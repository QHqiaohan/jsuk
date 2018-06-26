package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.EvaluateVo;
import com.jh.jsuk.entity.vo.EvaluateVoT;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.DictionaryService;
import com.jh.jsuk.service.UserEvaluateService;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.SensitiveWordUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2017-11-14
 */
@Api(tags = {"评价相关API:"})
@RestController
@RequestMapping(value = "/evaluate", method = {RequestMethod.POST, RequestMethod.GET})
public class UserEvaluateController {
    @Autowired
    private UserEvaluateService evaluateService;
    @Autowired
    private UserOrderService orderService;
    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation("添加评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", required = true, value = "订单id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "shopStarNum", required = true, value = "给店铺评价的星数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "distributionStarNum", required = true, value = "给骑手评价的星数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "content", required = true, value = "评价内容", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "img", required = false, value = "评价图片", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "status", required = true, value = "是否满意 0不满意  1满意", paramType = "query", dataType = "int"),
    })
    @PostMapping("/add")
    @Transactional
    public Result add(@ModelAttribute UserEvaluate evaluate) throws Exception {
        //获取敏感词
        Dictionary dictionary = dictionaryService.selectOne(new EntityWrapper<Dictionary>().eq("code", "sensitive_words"));
        String sensitiveWord = dictionary.getValue();
        String[] sensitiveWords = sensitiveWord.split("、");
        Set<String> sensitiveWordSet = new HashSet<>();
        for (String str : sensitiveWords) {
            sensitiveWordSet.add(str);
        }
        SensitiveWordUtil.init(sensitiveWordSet);
        String content = SensitiveWordUtil.replaceSensitiveWord(evaluate.getContent(), '*');
        evaluate.setContent(content);
        evaluate.setCreateTime(new Date());
        evaluate.insert();
        UserOrder order = orderService.selectById(evaluate.getOrderId());
        if(order == null)
            throw new MessageException("订单不存在！");
        order.setIsEvaluate(1);
        order.updateById();
//        Integer shopId = order.getShopId();
        Integer managerId = order.getManagerId();
        Shop shop = new Shop();
        shop.setId(managerId);
        shop.setStarNum(evaluateService.calulateStar(UserEvaluate.SHOP_STAR_NUM, new EntityWrapper()
                .eq(UserOrder.MANAGER_ID, managerId)));
        shop.updateById();
        Integer distributionUserId = order.getDistributionUserId();
        if (distributionUserId == null) {
            return new Result().success();
        }
        DistributionUser distributionUser = new DistributionUser();
        distributionUser.setId(distributionUserId);
//        distributionUser.setStarNum(evaluateService.calulateStar(UserEvaluate.DISTRIBUTION_STAR_NUM, new EntityWrapper()
//                .eq(UserEvaluate.DISTRIBUTION_USER_ID, distributionUserId)));
        distributionUser.updateById();
        return new Result().success();
    }

    @ApiOperation("店铺评价显示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "shopId", value = "店铺id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "status", value = "0不满意  1满意",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "0显示全部  1满意和不满意  2有图",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @PostMapping("/list")
    public Result list(Page page, @RequestParam Integer shopId, @RequestParam(required = false) Integer status, @RequestParam Integer type) {
        List<EvaluateVo> evaluateList = null;
        if (type == 0) {

            evaluateList = evaluateService
                    .selectListByShopId(page, new EntityWrapper()
                            .eq("t2.shop_id", shopId).eq("t1.is_del", 0));

        } else if (type == 1) {
            evaluateList = evaluateService
                    .selectListByShopId(page, new EntityWrapper()
                            .eq("t2.shop_id", shopId)
                            .eq("t1.`status`", status).eq("t1.is_del", 0));
        } else {
            evaluateList = evaluateService
                    .selectListByShopId(page, new EntityWrapper()
                            .eq("t2.shop_id", shopId)
                            .isNotNull("t1.img")
                            .or()
                            .eq("t1.img", "").eq("t1.is_del", 0));
        }

        return new Result().success(evaluateList);
    }

    @ApiOperation("根据订单号显示评价")
    @RequestMapping("show")
    public Result show(@ApiParam(value = "订单id", required = true) @RequestParam Integer orderId) {
        UserEvaluate evaluate = evaluateService.selectOne(new EntityWrapper<UserEvaluate>()
                .eq(UserEvaluate.ORDER_ID, orderId).eq("is_del", 0));
        return new Result().success(evaluate);
    }

    @ApiOperation("后台列表显示评价")
    @RequestMapping("/ui/selectListAllForAdmin")
    public Result uiSelectListAllForAdmin(Page page, Integer shopId) {
        EntityWrapper ew = new EntityWrapper();
        if (shopId != null) {
            ew.eq("t2.shop_id", shopId);
        }
        ew.eq("t1.is_del", 0);
        Page<EvaluateVoT> evaluateList = evaluateService.selectListAllForAdmin(page, ew);
        return new Result().success(evaluateList);
    }

    @ApiOperation("删除评价")
    @RequestMapping("/ui/deleteEvaluate")
    public Result uiDeleteEvaluate(Integer id) {
        UserEvaluate el = new UserEvaluate();
        el.setId(id);
        el.setIsDel(1);
        el.updateById();
        return new Result().success();
    }

    @ApiOperation("显示用户的历史评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer")
    })
    @RequestMapping("/list/user")
    public Result userEvaluate(Integer userId,Page page){
        Result<Page> result = new Result<>();
        Wrapper wrapper = new EntityWrapper();
        Page pg = evaluateService.listUser(userId,page,wrapper);
        return result.success(pg);
    }

}