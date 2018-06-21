package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserInvitationPay;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.service.UserInvitationPayService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 邀请用户 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-23
 */
@Api(tags = {"邀请好友:"})
@RestController
@RequestMapping("/userInvitationPay")
public class UserInvitationPayController {

    @Autowired
    private UserInvitationPayService userInvitationPayService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "成功邀请人数")
    @PostMapping("/getCount")
    public Result getCount(@ApiParam(value = "邀请人ID") @RequestParam Integer userId) {
        int count = userInvitationPayService.selectCount(new EntityWrapper<UserInvitationPay>()
                .eq(UserInvitationPay.INVITATION_ID, userId));
        return new Result().success(count);
    }

    @ApiOperation(value = "红包奖励明细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })

    @PostMapping("/getRewardInfo")
    public Result getRewardInfo(@ApiParam(value = "邀请人ID") @RequestParam Integer userId, Page page) {
        Page<UserInvitationPay> pageInfo = userInvitationPayService.selectPage(page, new EntityWrapper<UserInvitationPay>()
                .eq(UserInvitationPay.INVITATION_ID, userId));
        // 封装被邀请注册的用户信息
        List<User> userList = new ArrayList<>();
        for (UserInvitationPay userInvitationPay : pageInfo.getRecords()) {
            // 被邀请注册的用户ID
            Integer uId = userInvitationPay.getUserId();
            UserInfoVo userInfoVo = userService.selectInfoById(uId);
            userList.add(userInfoVo);
        }
        return new Result().success(userList);
    }


}

