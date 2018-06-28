package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.ExpressType;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.entity.dto.RobbingExpressDTO;
import com.jh.jsuk.envm.DistributionExpressStatus;
import com.jh.jsuk.mq.RobbingOrderProducer;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.service.ExpressTypeService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 快递跑腿 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"快递跑腿相关API:"})
@RestController
@RequestMapping("/express")
public class ExpressController {

    @Autowired
    private ExpressService expressService;
    @Autowired
    private ExpressTypeService expressTypeService;
    @Autowired
    BannerService bannerService;

    @ApiOperation(value = "用户端-快递跑腿banner")
    @RequestMapping(value = "/expressRunBanner", method = {RequestMethod.POST, RequestMethod.GET})
    public Result expressRunBanner() {
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                .eq(Banner.BANNER_LOCATION, 11)
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.SORT, false));
        return new Result().success(bannerList);
    }

    @ApiOperation("用户端-新增快递/跑腿信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "用户ID", value = "userId",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "联系电话", value = "phone",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "联系人姓名", value = "name",
                    paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "寄件人地址", value = "senderAddress",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "收件人地址", value = "getAddress",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "物品类型", value = "goodsType",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "预估重量", value = "weight",
                    paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "订单类型 1=快递,2=跑腿", value = "type",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/addExpress", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addExpress(Express express) {
        if (express.getUserId() == null) {
            return new Result().erro("用户信息过期");
        } else {
            boolean res = express.insert();
            if (res) {
                return new Result().success("您的快递订单已提交");
            } else {
                return new Result().erro("服务器繁忙,请稍后再试");
            }
        }
    }

    @ApiOperation("用户端-跑腿订单列表-不传表示查所有")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/getExpressList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getExpressList(Page page, @ModelAttribute Express express,
                                 @ApiParam("状态 1=待接单,2=待送货,3=待评价,0=待付款,4=已完成,5=已取消") @RequestParam(required = false) Integer status,
                                 @ApiParam("订单类型 1=快递,2=跑腿") @RequestParam(required = false) Integer type, Integer userId) {
        MyEntityWrapper<UserAddress> ew = new MyEntityWrapper<>();
        Page expressList = expressService.getExpressListBy(page, ew, status, type, userId);
        return new Result().success(expressList);
    }

    @ApiOperation("用户端-物品类型列表")
    @RequestMapping(value = "/getExpressType", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getExpressType() {
        List<ExpressType> expressTypeList = expressTypeService.selectList(new EntityWrapper<ExpressType>()
                .orderBy(ExpressType.PUBLISH_TIME, false));
        return new Result().success(expressTypeList);
    }

    @ApiOperation("用户端-取消跑腿订单")
    @RequestMapping(value = "/cancelRunOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result cancelRunOrder(Integer userId, @ApiParam(value = "订单ID", required = true) Integer orderId) {
        Express express = new Express();
        express.setStatus(5);
        boolean res = express.update(new EntityWrapper()
                .eq(Express.ID, orderId)
                .eq(Express.USER_ID, userId));
        if (res) {
            return new Result().success();
        } else {
            return new Result().erro("取消失败");
        }
    }

    @ApiOperation("用户端-删除跑腿订单")
    @RequestMapping(value = "/delRunOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delRunOrder(Integer userId, @ApiParam(value = "订单ID", required = true) Integer orderId) {
        Express express = new Express();
        express.setIsDel(1);
        boolean res = express.update(new EntityWrapper()
                .eq(Express.ID, orderId)
                .eq(Express.USER_ID, userId));
        if (res) {
            return new Result().success();
        } else {
            return new Result().erro("删除失败");
        }
    }

    @ApiOperation("用户端-确认收货")
    @RequestMapping(value = "/enterOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result enterOrder(Integer userId, @ApiParam(value = "订单ID", required = true) Integer orderId) {
        Express express = new Express();
        express.setStatus(3);
        boolean res = express.update(new EntityWrapper()
                .eq(Express.ID, orderId)
                .eq(Express.USER_ID, userId));
        if (res) {
            return new Result().success();
        } else {
            return new Result().erro("确认收货失败");
        }
    }

    @ApiOperation("骑手端-配送单列表-不传表示所有")
    @GetMapping("/dvr/list")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    public Result deliverList(Integer userId, Page page,
                              @ApiParam("状态 待抢单：wrb 待取货：wtk 待送达：dvn 已完成：cpt") @RequestParam(required = false) String status,
                              @ApiParam("订单类型 1=快递,2=跑腿") @RequestParam(required = false) Integer type) throws Exception {
        Wrapper<Express> ew = new MyEntityWrapper<>();
        Page expressList = expressService.getDeliverList(page, ew, status, type, userId);
        return new Result().success(expressList);
    }

    @Autowired
    RobbingOrderProducer producer;

    @ApiOperation("骑手端-配送数量")
    @PostMapping("/dvr/count")
    public Result deliverCount(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        Wrapper<Express> wrapper = new EntityWrapper<>();
        wrapper.ne(Express.IS_DEL, 1)
                .in(Express.STATUS, DistributionExpressStatus.WAIT_ROBBING.getKey());
        map.put(DistributionExpressStatus.WAIT_ROBBING.getsKey(),
                expressService.selectCount(wrapper));

        Wrapper<Express> wrapper1 = new EntityWrapper<>();
        wrapper1.eq(Express.DISTRIBUTION_USER_ID,userId)
                .ne(Express.IS_DEL, 1)
                .in(Express.STATUS, DistributionExpressStatus.WAIT_TAKE.getKey());
        map.put(DistributionExpressStatus.WAIT_TAKE.getsKey(),
                expressService.selectCount(wrapper1));

        Wrapper<Express> wrapper2 = new EntityWrapper<>();
        wrapper2.eq(Express.DISTRIBUTION_USER_ID,userId)
                .ne(Express.IS_DEL, 1)
                .in(Express.STATUS, DistributionExpressStatus.DELIVERING.getKey());
        map.put(DistributionExpressStatus.DELIVERING.getsKey(),
                expressService.selectCount(wrapper2));
        return new Result().success(map);
    }

    @ApiOperation("骑手端-抢单")
    @PostMapping("/dvr/robbing")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "expressId", value = "配送单id",
                    paramType = "query", dataType = "integer")
    })
    public Result deliverRobbingOrder(Integer userId, Integer expressId) {
        producer.send(new RobbingExpressDTO(userId, expressId));
        return new Result().success();
    }

    @ApiOperation("骑手端-取货")
    @PostMapping("/dvr/takeGoods")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "expressId", value = "配送单id",
                    paramType = "query", dataType = "integer")
    })
    public Result deliverTakeGoods(Integer expressId) {
        Express express = new Express();
        express.setId(expressId);
        express.setStatus(4);
        express.updateById();
        return new Result().success();
    }

    @ApiOperation("骑手端-送达")
    @PostMapping("/dvr/delivered")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "expressId", value = "配送单id",
                    paramType = "query", dataType = "integer")
    })
    public Result deliverDelivered(Integer expressId) {
        Express express = new Express();
        express.setId(expressId);
        express.setStatus(5);
        express.updateById();
        return new Result().success();
    }

}

