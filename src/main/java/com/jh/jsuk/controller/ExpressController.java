package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.envm.DistributionExpressStatus;
import com.jh.jsuk.envm.ExpressStatus;
import com.jh.jsuk.envm.UserRemainderStatus;
import com.jh.jsuk.envm.UserRemainderType;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

//import com.jh.jsuk.mq.RobbingOrderProducer;

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
    ExpressService expressService;
    @Autowired
    Session session;
    @Autowired
    private ExpressTypeService expressTypeService;
    @Autowired
    BannerService bannerService;
    @Autowired
    DistributionUserService distributionUserService;
    @Autowired
    DistributionDetailService distributionDetailService;

    @GetMapping("/page")
    public R page(Page page, String status, String kw, String[] date) throws Exception {
        List<String> dates = null;
        if (date != null && StrUtil.isNotBlank(date[0]) && StrUtil.isNotBlank(date[1])) {
            dates = Arrays.asList(date);
        }
        ExpressStatus expressStatus = null;
        if (status != null && !"all".equals(status)) {
            expressStatus = EnumUitl.toEnum(ExpressStatus.class, status, "getShortKey");
        }
        return R.succ(expressService.listPage(page, expressStatus, dates, kw));
    }

    @GetMapping("/count")
    public R count() {
        Map<String, Object> map = new HashMap<>();
        int all = 0;
        for (ExpressStatus status : ExpressStatus.values()) {
            int cnt = expressService.statusCount(status, session.getShopId(), session.getUserType(), session.getUserId());
            all += cnt;
            map.put(status.getShortKey(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    @GetMapping("/detail")
    public R detail(Integer expressId) {
        return R.succ(expressService.detail(expressId));
    }

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
    @RequestMapping(value = "/addExpress", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addExpress(@ModelAttribute Express express) {
        Map<String, Object> map = new HashMap<>();
        if (express.getUserId() == null) {
            return new Result().erro("用户信息过期");
        }
        express.setStatus(1);
        express.setCityId(session.getCityId());
        express.setOrderNo(OrderNumUtil.getOrderIdByUUId());
        // runningFeeService.caleRunningFee(express);
        express.insert();
        map.put("expressId", express.getId());
        map.put("orderNo", express.getOrderNo());
        map.put("price", express.getPrice());
//      distributionUserService.notifyRobbing();
        return new Result().success(map);

    }

    @Autowired
    RunningFeeService runningFeeService;

    @ApiOperation("用户端-跑腿订单价格")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "startLng", value = "起始 lng",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "startLat", value = "起始 lat",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "endLng", value = "结束 lng",
            paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "endLat", value = "结束 lat",
            paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/expressPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public Result expressPrice(String startLng, String startLat, String endLng, String endLat) {
        if (StrUtil.isBlank(startLng) ||
            StrUtil.isBlank(startLat) ||
            StrUtil.isBlank(endLng) ||
            StrUtil.isBlank(endLat)) {
            return R.err("数据为空");
        }
        return R.succ(runningFeeService.caleRunningFee(startLng + "," + startLat, endLng + "," + endLat));
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

    @Autowired
    NewsService newsService;

    @ApiOperation("用户端-催一催")
    @RequestMapping(value = "/urge", method = {RequestMethod.POST, RequestMethod.GET})
    public Result urge(@ApiParam("快递id") Integer expressId) {
        newsService.urgeDistribution(expressId, session.getNickName());
        return new Result().success();
    }

    @ApiOperation("用户端-取消跑腿订单")
    @RequestMapping(value = "/cancelRunOrder", method = {RequestMethod.POST, RequestMethod.GET})
    public Result cancelRunOrder(Integer userId, @ApiParam(value = "订单ID", required = true) Integer orderId) {
        Express express = new Express();
        express.setStatus(0);
        boolean res = express.update(new EntityWrapper()
            .eq(Express.ID, orderId)
            .eq(Express.USER_ID, userId));
        if (res) {
            Express express1 = express.selectById(orderId);
            if (express1 != null) {
                express1.getPrice();
                UserRemainder ee1 = new UserRemainder();
                ee1.setUserId(userId);
                ee1.setCreateTime(new Date());
                ee1.setRemainder(new BigDecimal(express1.getPrice()));
                ee1.setOrderNum(express.getOrderNo());
                ee1.setType(UserRemainderType.REFUND);
                ee1.setStatus(UserRemainderStatus.PASSED);
                ee1.insert();
            }

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
        express.setStatus(5);
        boolean res = express.update(new EntityWrapper()
            .eq(Express.ID, orderId)
            .eq(Express.USER_ID, userId));
        if (res) {
            return new Result().success();
        } else {
            return new Result().erro("确认收货失败");
        }
    }
    @Autowired
    private CitysService citysService;
    @ApiOperation("骑手端-获取城市")
    @GetMapping("/dvrCity")
    public Result dvrCity(String cityName){
        Integer integer = citysService.getcitynumBycityname(cityName);
        if(integer==null){
            return  new Result().erro("获取城市失败");
        }
        session.setCityId(integer);
        return  new Result().success("获取城市成功");
    }
    @ApiOperation("骑手端-可接单数量")
    @GetMapping("/getOrderName")
    public Result getOrderName(){
        Integer orderName = expressService.getOrderName(session.getCityId());
        return new Result().success(orderName);
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
                              @ApiParam("订单类型 1=快递,2=跑腿") @RequestParam(required = false) Integer type,
                              @RequestParam(required = false) String lng, @RequestParam(required = false) String lat) throws Exception {
        Wrapper<Express> ew = new MyEntityWrapper<>();
//        type = null;
        Page expressList = expressService.getDeliverList(page, ew, status, type, userId, lng, lat, session.getCityId());
        return new Result().success(expressList);
    }

//    @Autowired
//    RobbingOrderProducer producer;

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
        wrapper1.eq(Express.DISTRIBUTION_USER_ID, userId)
            .ne(Express.IS_DEL, 1)
            .in(Express.STATUS, DistributionExpressStatus.WAIT_TAKE.getKey());
        map.put(DistributionExpressStatus.WAIT_TAKE.getsKey(),
            expressService.selectCount(wrapper1));

        Wrapper<Express> wrapper2 = new EntityWrapper<>();
        wrapper2.eq(Express.DISTRIBUTION_USER_ID, userId)
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
        return new Result().success(expressService.deliverRobbingOrder(userId, expressId));
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

    @Autowired
    DistributionApplyService distributionApplyService;


    @ApiOperation("骑手端-送达")
    @PostMapping("/dvr/delivered")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "expressId", value = "配送单id",
            paramType = "query", dataType = "integer")
    })
    @Transactional
    public Result deliverDelivered(Integer expressId) {
        Express express = expressService.selectById(expressId);
        if (express == null)
            throw new RuntimeException("配送单不存在");
        if (express.isCompleted())
            throw new RuntimeException("配送单已完成");
        String price = express.getPrice();
//        BigDecimal amount = new BigDecimal(price);
//        DistributionDetail detail = new DistributionDetail();
//        detail.setMoney(amount);
//        detail.setDetail("完成配送");
//        detail.setPublishTime(new Date());
//        detail.setUserId(userId);
//        detail.insert();
        distributionDetailService.complete(express);
        distributionApplyService.complete(express);
        express.setId(expressId);
        express.setEndTime(new Date());
        express.setStatus(5);
        express.updateById();
        return new Result().success();
    }

    /**
     * 用户端 快递下单
     *
     * @return
     */
    @ApiOperation("用户端 快递下单")
    @PostMapping("/kuaidi")
    public Result kuaidi(String SenderName, String SenderMobile, String SenderProvince, String SenderCity, String SenderDistrict,
                         String SenderAddress, String ReceiverName, String ReceiverMobile, String ReceiverProvince, String ReceiverCity,
                         String ReceiverDistrict, String GoodsType) {
        String AppKey = "juxbwcom";
        String APPSECRET = "0e4e4cd5bb1e4e179dd060be3074241e";
        Map map = new HashMap();
        map.put("SenderName", SenderName);//寄件人名称
        map.put("SenderMobile", SenderMobile);//寄件人手机号
        map.put("SenderProvince", SenderProvince);//寄件人省份
        map.put("SenderCity", SenderCity);//寄件人城市
        map.put("SenderDistrict", SenderDistrict);//寄件人区域
        map.put("SenderAddress", SenderAddress);//寄件人地址
        map.put("ReceiverName", ReceiverName);//收件人名称
        map.put("ReceiverMobile", ReceiverMobile);//收件人手机号
        map.put("ReceiverProvince", ReceiverProvince);//收件人省份
        map.put("ReceiverCity", ReceiverCity);//收件人城市
        map.put("ReceiverDistrict", ReceiverDistrict);//收件人区域
        map.put("GoodsType", GoodsType);//邮件的物品类型：默认：物品


        return new Result().success("ok");
    }
}

