package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserIntegral;
import com.jh.jsuk.service.UserIntegralService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户积分表 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-26
 */
@Api(tags = {"用户积分:"})
@RestController
@RequestMapping("/userIntegral")
public class UserIntegralController {

    @Autowired
    private UserIntegralService userIntegralService;

    @ApiOperation(value = "查询用户积分明细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @PostMapping("/getInfo")
    public Result getInfo(@ApiParam(hidden = true) @RequestParam Integer userId, Page page) {
        // 根据用户ID查询用户余额账单
        Page pageInfo = userIntegralService.selectPage(page, new EntityWrapper<UserIntegral>()
                .eq(UserIntegral.USER_ID, userId)
                .orderBy(UserIntegral.CRA_TIME, false));
        if (pageInfo.getRecords().size() == 0) {
            return new Result().success("暂无积分明细");
        } else {
            return new Result().success(pageInfo);
        }
    }

    @ApiOperation(value = "获取总积分")
    @PostMapping("/getCount")
    public Result getCount(@ApiParam(hidden = true) @RequestParam Integer userId) {
        List<UserIntegral> userIntegrals = userIntegralService.selectList(new EntityWrapper<UserIntegral>()
                .eq(UserIntegral.USER_ID, userId));
        // 如果余额表没有该用户信息
        if (userIntegrals.size() == 0) {
            return new Result().success(0);
        } else {
            // 初始记录总积分数
            int sum = 0;
            for (UserIntegral integral : userIntegrals) {
                // 如果是购物获赠积分
                if (integral.getIntegralType() == 1) {
                    sum += integral.getIntegralNumber();
                    // 抵扣积分
                } else if (integral.getIntegralType() == -1) {
                    sum -= integral.getIntegralNumber();
                }
            }
            return new Result().success(sum);
        }
    }
}

