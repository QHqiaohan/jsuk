package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserAuthentication;
import com.jh.jsuk.service.UserAuthenticationService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 实名认证 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-26
 */
@Api(tags = {"实名认证:"})
@RestController
@RequestMapping("/userAuthentication")
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @ApiOperation("新增认证信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "realName", value = "真实姓名",
                    paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idCard", value = "身份证号",
                    paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idCardImg", value = "手持身份证",
                    paramType = "query", dataType = "OSS图片地址"),
            @ApiImplicitParam(name = "idCardImgZ", value = "身份证正面",
                    paramType = "query", dataType = "OSS图片地址"),
            @ApiImplicitParam(name = "idCardImgF", value = "身份证反面",
                    paramType = "query", dataType = "OSS图片地址"),
            @ApiImplicitParam(name = "userId", value = "用户ID",
                    paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "审核状态 1=通过 0=审核中 -1=未通过 -2=未认证",
                    paramType = "query", dataType = "Integer"),
    })
    @PostMapping("/add")
    public Result add(@ModelAttribute UserAuthentication userAuthentication) {
        boolean res = userAuthentication.insert();
        if (res) {
            return new Result().success("请等待审核结果");
        } else {
            return new Result().erro("服务器繁忙,请稍后再试");
        }
    }

    @ApiOperation("获取认证状态")
    @PostMapping("/getStatusByUserId")
    public Result getStatusByUserId(@ApiParam("用户ID") @RequestParam Integer userId) {
        UserAuthentication userAuthentication = userAuthenticationService.selectOne(new EntityWrapper<UserAuthentication>()
                .eq(UserAuthentication.USER_ID, userId));
        // 初始化 审核状态
        int status;
        // 如果表信息为空
        if (userAuthentication == null) {
            return new Result().erro("没有该用户认证信息");
        } else {
            // 获取审核状态
            status = userAuthentication.getStatus();
        }
        if (status == 1) {
            return new Result().success("审核通过", userAuthentication);
        } else if (status == 0) {
            return new Result().success("审核中", userAuthentication);
        } else if (status == -1) {
            return new Result().success("未通过", userAuthentication);
        } else if (status == -2) {
            return new Result().success("未认证", userAuthentication);
        }
        return new Result().erro("没有该用户认证信息");
    }

    @ApiOperation("查询认证信息")
    @PostMapping("/getInfoByUserId")
    public Result getInfoByUserId(@ApiParam("用户ID") @RequestParam Integer userId) {
        UserAuthentication userAuthentication = userAuthenticationService.selectOne(new EntityWrapper<UserAuthentication>()
                .eq(UserAuthentication.USER_ID, userId));
        // 审核状态
        if (userAuthentication == null) {
            return new Result().erro("没有该用户认证信息");
        } else {
            return new Result().success(userAuthentication);
        }
    }

}