package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Coupon;
import com.jh.jsuk.entity.UserCoupon;
import com.jh.jsuk.entity.vo.CouponVo;
import com.jh.jsuk.entity.vo.UserCouponVo;
import com.jh.jsuk.service.CouponService;
import com.jh.jsuk.service.UserCouponService;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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
@RequestMapping(value = "/coupon", method = {RequestMethod.POST, RequestMethod.GET})
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;

    public R page(Page page) {
        return null;
    }

    @ApiOperation("商家修改满减列表")
    @RequestMapping(value = "/postCoupon", method = {RequestMethod.POST, RequestMethod.GET})
    public Result  postCoupon(String coupon,Integer shopId){
        try{
            couponService.deleteCouponByShopId(shopId);

            //coupon 格式：  13-2,15-3
            String[] cu =coupon.split(",");
            for(int i=0;i<cu.length;i++){
                String aa = cu[i];//获取单条数据
                String[] bb = aa.split("-");
                String man = bb[0];
                String jia = bb[1];
               double m = Double.parseDouble(man);//满多少
               double ji =Double.parseDouble(jia);//减多少
                couponService.postCoupon(shopId,m,ji);
            }

            return new Result().success("成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result().erro("网络异常");
        }
    }
    /**
     * 商家获取满减列表
     * @return
     */
    @ApiOperation("商家获取满减列表")
    @RequestMapping(value = "/getListByShopId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getListByShopId(Integer shopId){
        List<Coupon> list = couponService.getListByShopId(shopId);
        return new Result().success(list);
    }

    //用户-我的-优惠券-查询用户优惠卷列表
    @ApiOperation("用户-查询用户优惠卷列表")
    @PostMapping("/listByUserId")
    public Result listByUserId(Integer userId) {
        // 用户优惠券信息
        List<UserCouponVo> couponList = userCouponService.findByUserId(userId);
        return new Result().success(couponList);
    }

    @ApiOperation("用户-查询用户店铺优惠卷列表")
    @PostMapping("/listByShopUserId")
    public Result listByUserShopId(@RequestParam Integer shopId, Integer userId) {
        // 用户优惠券信息
        List<UserCouponVo> couponList = userCouponService.listByUserShopId(shopId, userId);
        return new Result().success(couponList);
    }

    //用户-我的-优惠券-获取优惠券数量
    @ApiOperation("用户-我的-优惠券-获取优惠券数量")
    @RequestMapping("/getCouponNum")
    public Result getCouponNum(Integer userId) {
        // 用户优惠券信息
        List<CouponVo> couponList = couponService.findByUserId(userId);
        if (couponList == null || couponList.size() == 0) {
            return new Result().success(0);
        }
        List<CouponVo> resList = new ArrayList<>();
        for (CouponVo coupon : couponList) {
            //0已使用 1未使用  2未开始  3已结束
//            if(coupon.getUserCoupon().getStatus()==1 || coupon.getUserCoupon().getStatus()==2){
//                resList.add(coupon);
//            }
        }
        return new Result().success(resList.size());
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

    @Setter
    @Getter
    private static class CouponShop implements Serializable {

        private Integer shopId;

        private List<Coupon> coupon;

    }

    @ApiOperation("用户-显示店铺可用优惠券列表")
    @PostMapping("/listByShopId")
    public R listByShop(@RequestParam String shopId, Integer userId) {
        if (StrUtil.isBlank(shopId)) {
            return R.err("shop 为空");
        }
        String[] sps = shopId.split(",");
        List<String> allSps = new ArrayList<>();
        for (String sp : sps) {
            if (StrUtil.isNotBlank(sp)) {
                allSps.add(sp);
            }
        }
        EntityWrapper<Coupon> wrapper = new EntityWrapper<>();
        wrapper.in(Coupon.SHOP_ID, allSps);
        wrapper.orderBy(Coupon.SHOP_ID);
        wrapper.orderBy(Coupon.FULL_PRICE, false);
        List<Coupon> list = couponService.selectList(wrapper);
        List<CouponShop> ss = new ArrayList<>();
        for (Coupon coupon : list) {
            Integer sid = coupon.getShopId();
            if (sid == null) {
                continue;
            }
            CouponShop couponShop = null;
            for (CouponShop cs : ss) {
                if (sid.equals(cs.getShopId())) {
                    couponShop = cs;
                    break;
                }
            }
            if (couponShop == null) {
                couponShop = new CouponShop();
                couponShop.setShopId(sid);
                ss.add(couponShop);
            }
            List<Coupon> couponList = couponShop.getCoupon();
            if (couponList == null) {
                couponList = new ArrayList<>();
                couponShop.setCoupon(couponList);
            }
            couponList.add(coupon);
        }
        return R.succ(ss);
    }


    //用户-领券-显示优惠券领取页面(前端暂时没有领券页面)
//    @ApiOperation("用户-领券-展示店铺的优惠券列表")
//    @RequestMapping(value="showShopCouponList",method={RequestMethod.GET,RequestMethod.POST})
//    public Result showShopCouponList(@ApiParam(value="店铺id",required = true) Integer shopId){
//        List<Coupon> shopCouponList=couponService.selectList(new EntityWrapper<Coupon>()
//                                                                 .eq(Coupon.SHOP_ID,shopId)
//                                                                 .eq(Coupon.IS_DEL,0)
//                                                                 .gt(Coupon.NUM,0)
//                                                                 .orderBy(Coupon.PUBLISH_TIME,false)
//        );
//        if(shopCouponList==null || shopCouponList.size()==0){
//            return new Result().erro("没有优惠券");
//        }
//        return new Result().success(shopCouponList);
//    }


    //用户-领券
    @ApiOperation("领取优惠卷")
    @RequestMapping(value = "/getCoupon", method = {RequestMethod.GET, RequestMethod.POST})
    @Transactional
    public synchronized Result getCoupon(@ApiParam(value = "优惠券id", required = true) Integer couponId,
//                                         @ApiParam(value="店铺id",required = true)   Integer shopId,
                                         @ApiParam(value = "用户id", required = true) Integer userId) {
        Coupon coupon = couponService.selectById(couponId);
        if (coupon == null)
            return new Result().erro("优惠券数量不足");
        if (new Date().after(coupon.getEndTime()) || coupon.getIsDel() == 1) {
            //优惠券已过期或删除
            return new Result().erro("优惠券已过期");
        }
        if (coupon.getNum() < 0) {
            return new Result().erro("优惠券数量不足");
        }

        UserCoupon userCoupon = userCouponService.selectOne(new EntityWrapper<UserCoupon>()
            .eq(UserCoupon.ID, coupon.getId())
            .eq(UserCoupon.USER_ID, userId)
        );
        if (userCoupon != null) {
            return new Result().erro("您已领取过该优惠卷");
        }
        coupon.setNum(coupon.getNum() - 1);
        coupon.updateById();

        UserCoupon newUserCoupon = new UserCoupon();
        newUserCoupon.setUserId(userId);
        newUserCoupon.setCouponId(couponId);
        newUserCoupon.setIsUsed(0);
        newUserCoupon.setPublishTime(new Date());
        newUserCoupon.insert();

        return new Result().success("领券成功");
    }


//    @ApiOperation("领取优惠卷")
//    @PostMapping("/get")
//    public Result get(@ApiParam(value = "优惠券id", required = true) @RequestParam Integer couponId, Integer userId) {
//        int count = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
//                .eq(UserCoupon.COUPON_ID, couponId)
//                .eq(UserCoupon.USER_ID, userId));
//        if (count > 0) {
//            return new Result().erro("您已领取过该优惠卷");
//        }
//        synchronized (this) {
//            int count2 = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
//                    .eq(UserCoupon.COUPON_ID, couponId)
//                    .eq(UserCoupon.USER_ID, userId));
//            if (count2 > 0) {
//                return new Result().erro("您已领取过该优惠卷");
//            }
//            /*int count = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
//                    .eq(UserCoupon.COUPON_ID, couponId));*/
//            Coupon coupon = couponService.selectById(couponId);
//            if (coupon.getNum() > 0) {
//                coupon.setNum(coupon.getNum() - 1);
//                couponService.update(coupon, new EntityWrapper<Coupon>().eq("id", couponId));
//                UserCoupon userCoupon = new UserCoupon();
//                userCoupon.setCouponId(couponId);
//                userCoupon.setUserId(userId);
//                userCouponService.insert(userCoupon);
//                return new Result().success("领取成功");
//            } else {
//                return new Result().erro("优惠卷数量不足");
//            }
//            /*if(coupon.getNum() > count){
//                UserCoupon userCoupon = new UserCoupon();
//                userCoupon.setCouponId(couponId);
//                userCoupon.setUserId(userId);
//                userCouponService.insert(userCoupon);
//                return new Result().success("领取成功");
//            }else {
//                return new Result().erro("优惠卷数量不足");
//            }*/
//        }
//    }

    //用户-领券-前端缺少领券页面
    @ApiOperation("用户-显示优惠券领取列表")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "current", value = "当前页码",
            required = false, paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数",
            required = false, paramType = "query", dataType = "integer")
    })
    @PostMapping("/list")
    public Result list(Page page) {
        Page couponPage = couponService.selectPage(page, new EntityWrapper<Coupon>()
            .ne(Coupon.IS_DEL, 1)
            .gt(Coupon.NUM, 0)
            .orderBy(Coupon.PUBLISH_TIME, false));
        return new Result().success(couponPage);
    }


//    @RequestMapping("/ui/list")
//    public Result uiList(Page page) {
//        Page couponPage = couponService.selectPage(page, new EntityWrapper<Coupon>()
//                .eq(Coupon.IS_DEL, 1)
//                .orderBy(Coupon.PUBLISH_TIME, false));
//        return new Result().success(couponPage);
//    }

//    @PostMapping("/ui/add")
//    public Result add(Coupon coupon) {
//        coupon.insert();
//        return new Result().success();
//    }

//    @RequestMapping("/ui/del")
//    public Result del(Coupon coupon) {
//        coupon.setIsDel(0);
//        coupon.updateById();
//        return new Result().success();
//    }


}