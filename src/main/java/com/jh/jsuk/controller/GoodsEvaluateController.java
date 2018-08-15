package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.Dictionary;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.dto.GoodsEvaluateDto;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.SensitiveWordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品评价:")
@RestController
@RequestMapping(value = "/goodsEvaluate", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsEvaluateController {

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @ApiOperation("用户-获取指定数量的评价数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "count", value = "数量(默认1)", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    public Result get(Integer goodsId, @RequestParam(defaultValue = "1") Integer count) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("evaluates", goodsEvaluateService.get(goodsId, count));
        EntityWrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
            .ne(GoodsEvaluate.IS_DEL, 1);
        map.put("count", goodsEvaluateService.selectCount(wrapper));
        return new Result().success(map);
    }

    @ApiOperation("用户-获取评价数量")
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public Result count(Integer goodsId) throws Exception {
        return new Result().success(goodsEvaluateService.count(goodsId));
    }

    @ApiOperation("用户-各类型评价数量")
    @RequestMapping(value = "/counts", method = {RequestMethod.POST, RequestMethod.GET})
    public Result counts(Integer goodsId) throws Exception {
        return new Result().success(goodsEvaluateService.counts(goodsId));
    }

    @ApiOperation("用户-获取评价分页数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "type", value = "评价类型 全部:all(默认),好评:gd 中评:mdm  差评:ngt",
            paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public Result count(Integer goodsId, @RequestParam(defaultValue = "all") String type, Page page) throws Exception {
        return new Result().success(goodsEvaluateService.listPage(goodsId, type, page));
    }

    @Autowired
    UserService userService;

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    com.jh.jsuk.service.UserOrderService orderService;

    @Autowired
    ShopService shopService;

    @Autowired
    GoodsEvaluateService getGoodsEvaluateService;

    @Autowired
    UserEvaluateService userEvaluateService;

    @Autowired
    Session session;

    @ApiOperation("用户端-添加商品评价")
    @RequestMapping(value = "/addEvaluate", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addEvaluate(@RequestBody ArrayList<GoodsEvaluateDto> list) throws Exception {
        if (list == null || list.isEmpty())
            return R.err("没数据");
        for (GoodsEvaluateDto dto : list) {
            Integer goodsStar = dto.getGoodsStar();
            Integer sendStar = dto.getSendStar();
            Integer serviceStar = dto.getServiceStar();
            if (goodsStar != null && sendStar != null && serviceStar != null) {
                int fullStar = (goodsStar + sendStar + serviceStar) / 3;
                dto.setStarNumber(fullStar);
            } else {
                dto.setStarNumber(5);
            }
            dto.setUserId(session.lUserId());
            //获取敏感词
            Dictionary dictionary = dictionaryService.selectOne(new EntityWrapper<Dictionary>().eq("code", "sensitive_words"));
            String sensitiveWord = dictionary.getValue();
            String[] sensitiveWords = sensitiveWord.split("、");
            Set<String> sensitiveWordSet = new HashSet<>(Arrays.asList(sensitiveWords));
            SensitiveWordUtil.init(sensitiveWordSet);
            String content = SensitiveWordUtil.replaceSensitiveWord(dto.getContent(), '*');
            dto.setContent(content);
            dto.setCreateTime(new Date());
            UserOrder order = orderService.selectById(dto.getOrderId());
            if (order == null) {
                return new Result().erro("订单不存在！");
            }
            User user = userService.selectById(session.lUserId());
            if (user != null) {
                dto.setUserHeadImg(user.getHeadImg());
                dto.setUserName(user.getNickName());
            }
            goodsEvaluateService.insert(dto);
            order.setIsEvaluate(1);
            order.updateById();

            /**
             * 评价店铺
             */
//            Integer managerId = order.getShopId();
//            Shop shop = shopService.selectOne(new EntityWrapper<Shop>().eq("id", managerId));
//            shop.setStarNum(userEvaluateService.calulateStar(UserEvaluate.SHOP_STAR_NUM, new EntityWrapper()
//                .eq(UserOrder.SHOP_ID, managerId)));
//            shop.updateById();

        }
        return new Result().success("添加成功");
    }


}

