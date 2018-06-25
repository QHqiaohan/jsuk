package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户余额表 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-26
 */
@Api(tags = {"用户余额:"})
@RestController
@RequestMapping("/userRemainder")
public class UserRemainderController {

    @Autowired
    private UserRemainderService userRemainderService;

    @ApiOperation(value = "查询余额账单明细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @PostMapping("/getBill")
    public Result getBill(@RequestParam Integer userId, Page page) {
        // 根据用户ID查询用户余额账单
        Page remainderPage = userRemainderService.selectPage(page, new EntityWrapper<UserRemainder>()
                .eq(UserRemainder.USER_ID, userId)
                .orderBy(UserRemainder.CREATE_TIME, false));
        return new Result().success(remainderPage);
    }

    @ApiOperation(value = "获取总余额")
    @PostMapping("/getCount")
    public Result getCount(@ApiParam(hidden = true) @RequestParam Integer userId) {
        List<UserRemainder> userRemainderList = userRemainderService.selectList(new EntityWrapper<UserRemainder>()
                .eq(UserRemainder.USER_ID, userId));
        // 初始化记录总余额
        BigDecimal decimalSum = new BigDecimal("0.00");
        // 如果余额表有该用户信息
        if (userRemainderList.size() != 0) {
            for (UserRemainder ur : userRemainderList) {
                // 如果是消费
                if (ur.getType() == -1) {
                    decimalSum = decimalSum.subtract(ur.getRemainder());
                } else {
                    // 充值
                    decimalSum = decimalSum.add(ur.getRemainder());
                }
            }
            return new Result().success(decimalSum);
        } else {
            return new Result().success(0);
        }
    }

}

