package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.UserCoupon;
import com.jh.jsuk.entity.vo.CoupQueryParam;
import com.jh.jsuk.entity.vo.CouponVo;
import com.jh.jsuk.service.CouponService;
import com.jh.jsuk.service.UserCouponService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2017-11-09
 */
@Api(tags = {"优惠券:"})
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;

    @ApiOperation("查询用户优惠卷列表")
    @PostMapping("/listByUserId")
    public Result listByUserId(Integer userId) {
        // 用户优惠券信息
        List<CouponVo> couponList = couponService.findByUserId(userId);
        for (CouponVo coupon : couponList) {
            //未开始
            if (new Date().before(coupon.getStartTime())) {
                coupon.getUserCoupon().setStatus(2);
                //已结束
            } else if (coupon.getEndTime().before(new Date())) {
                coupon.getUserCoupon().setStatus(3);
                //正常
            } else {
                coupon.getUserCoupon().setStatus(1);
            }
            coupon.getUserCoupon().updateById();
        }
        Collections.sort(couponList, new Comparator<CouponVo>() {
            @Override
            public int compare(CouponVo o1, CouponVo o2) {
                return o1.getUserCoupon().getStatus() - o2.getUserCoupon().getStatus();
            }
        });
        List<CouponVo> normalCoupon = new ArrayList<>();
        for (CouponVo coupon : couponList) {
            if (coupon.getUserCoupon().getStatus() == 1) {
                coupon.setStatus(1);
                normalCoupon.add(coupon);
            }
        }
        return new Result().success(normalCoupon);
    }

    /*@ApiOperation("获取用户优惠券列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @PostMapping("/getList")
    public Result getList(Page page, @RequestParam Integer userId) {
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        // 封装收藏的商品数据
        List<Coupon> list = new ArrayList<>();
        // 获取优惠券ID
        Page pageInfo = userCouponService.selectPage(page, new EntityWrapper<UserCoupon>()
                .eq(UserCoupon.USER_ID, userId));
        List<UserCoupon> records = pageInfo.getRecords();
        for (UserCoupon userCoupon : records) {
            Integer couponId = userCoupon.getCouponId();
            // 根据优惠券ID查询用户优惠券
            List<Coupon> couponList = couponService.selectList(new EntityWrapper<Coupon>()
                    .eq(Coupon.ID, couponId)
                    .eq(Coupon.IS_DEL, 1)
                    .orderBy(Coupon.END_TIME, false));
            for (Coupon coupon : couponList) {
                list.add(coupon);
            }
        }
        map.put("total", page.getSize());
        map.put("current", pageInfo.getCurrent());
        map.put("size", pageInfo.getSize());
        map.put("pages", pageInfo.getPages());
        map.put("records", list);
        return new Result().success(map);
    }*/

    @ApiOperation("显示店铺可用优惠卷列表")
    @PostMapping("/listByShopId")
    public Result listByShop(CoupQueryParam param
                             /*Integer userId, @ApiParam(value = "店铺id",required = true)
                                 @RequestParam Integer shopId, @ApiParam(value = "订单价格",required = true*/
            /*) @RequestParam BigDecimal money*/) {
        param.setStatus(1);//查询可用
        List<CouponVo> couponList = couponService.selectVoList2(param/*new EntityWrapper()
                .eq(Coupon.SHOP_ID, shopId)
                .or()
                .isNull(Coupon.SHOP_ID)
                .eq(UserCoupon.USER_ID, userId)
                .ge(Coupon.FULL_PRICE, money)
                .eq(UserCoupon.STATUS,1)
                .orderBy(Coupon.DISCOUNT,false)*/);
        return new Result().success(couponList);
    }

    @ApiOperation("领取优惠卷")
    @PostMapping("/get")
    public Result get(@ApiParam(value = "优惠券id", required = true) @RequestParam Integer couponId, Integer userId) {
        int count = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
                .eq(UserCoupon.COUPON_ID, couponId)
                .eq(UserCoupon.USER_ID, userId));
        if (count > 0) {
            return new Result().erro("您已领取过该优惠卷");
        }
        synchronized (this) {
            int count2 = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
                    .eq(UserCoupon.COUPON_ID, couponId)
                    .eq(UserCoupon.USER_ID, userId));
            if (count2 > 0) {
                return new Result().erro("您已领取过该优惠卷");
            }
            /*int count = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
                    .eq(UserCoupon.COUPON_ID, couponId));*/
            Coupon coupon = couponService.selectById(couponId);
            if (coupon.getNum() > 0) {
                coupon.setNum(coupon.getNum() - 1);
                couponService.update(coupon, new EntityWrapper<Coupon>().eq("id", couponId));
                UserCoupon userCoupon = new UserCoupon();
                userCoupon.setCouponId(couponId);
                userCoupon.setUserId(userId);
                userCouponService.insert(userCoupon);
                return new Result().success("领取成功");
            } else {
                return new Result().erro("优惠卷数量不足");
            }
            /*if(coupon.getNum() > count){
                UserCoupon userCoupon = new UserCoupon();
                userCoupon.setCouponId(couponId);
                userCoupon.setUserId(userId);
                userCouponService.insert(userCoupon);
                return new Result().success("领取成功");
            }else {
                return new Result().erro("优惠卷数量不足");
            }*/
        }
    }

    @ApiOperation("显示优惠券领取列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer")
    })
    @PostMapping("/list")
    public Result list(Page page) {
        Page couponPage = couponService.selectPage(page, new EntityWrapper<Coupon>()
                .eq(Coupon.IS_DEL, 1)
                .gt(Coupon.NUM, 0)
                .orderBy(Coupon.PUBLISH_TIME, false));
        return new Result().success(couponPage);
    }

    @GetMapping("/ui/list")
    public Result uiList(Page page) {
        Page couponPage = couponService.selectPage(page, new EntityWrapper<Coupon>()
                .eq(Coupon.IS_DEL, 1)
                .orderBy(Coupon.PUBLISH_TIME, false));
        return new Result().success(couponPage);
    }

    @PostMapping("/ui/add")
    public Result add(Coupon coupon) {
        coupon.insert();
        return new Result().success();
    }

    @GetMapping("/ui/del")
    public Result del(Coupon coupon) {
        coupon.setIsDel(0);
        coupon.updateById();
        return new Result().success();
    }
}