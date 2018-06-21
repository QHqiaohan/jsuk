package com.jh.jsuk.controller;


import com.jh.jsuk.entity.UserCoupon;
import com.jh.jsuk.service.UserCouponService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 用户优惠券 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018年5月23日
 */
@Api(tags = {"优惠券:"})
@RestController
@RequestMapping("/userCoupon")
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    @ApiOperation("优惠券总数")
    @PostMapping("/getCount")
    public Result getCount(@RequestParam @ApiParam(value = "用户ID", required = true) Integer userId) {
        UserCoupon userCoupon = userCouponService.selectById(userId);
        return new Result().success(userCoupon);
    }

    @ApiOperation("查询优惠券")
    @PostMapping("/getUserCoupon")
    public Result getUserCoupon(@RequestParam @ApiParam(value = "用户ID", required = true) Integer userId) {
        UserCoupon userCoupon = userCouponService.selectById(userId);
        return new Result().success(userCoupon);
    }

}
