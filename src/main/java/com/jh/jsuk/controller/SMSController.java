package com.jh.jsuk.controller;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ParentUser;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopUserService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.MessageUtil;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Api(tags = {"短信相关操作API:"})
@RestController
@RequestMapping("/sms")
public class SMSController {
    @Autowired
    private UserService userService;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private DistributionUserService distributionUserService;

    @Autowired
    ManagerUserService managerUserService;

    @ApiOperation("注册短信发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "0用户端  1商家端  2骑手端", required = true, paramType = "query", dataType = "int"),
    })
    @RequestMapping(value = "/sendByRegister", method = {RequestMethod.POST, RequestMethod.GET})
    public Result sendByRegister(@RequestParam String phone, @RequestParam Integer type, HttpSession session) throws Exception {
        try {
            ParentUser user = null;
            if (type == 0) {
                // 用户端
                user = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
            } else if (type == 1) {
                // 商家端
                user = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.PHONE, phone));
            } else {
                // 骑手端
                user = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.PHONE, phone));
            }
            if (user != null) {
                return new Result().erro("该用户已注册");
            }
            // 6位随机验证码
            String code = RandomUtil.randomNumbers(6);
            // 发送短信
            SendSmsResponse sendSmsResponse = MessageUtil.sendSmsRegister(phone, code);
            // 获取返回状态码
            String code1 = sendSmsResponse.getCode();
            if ("OK".equalsIgnoreCase(code1)) {
                // 存入session
                session.setAttribute(phone + "register" + type, code);
                session.setMaxInactiveInterval(60 * 60 * 5 * 1000);
                return new Result().success(code, "发送成功");
            } else {
                System.out.println(sendSmsResponse.getMessage());
                return new Result().erro("发送失败");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result().erro("发送失败");
        }
    }

    @ApiOperation("找回密码短信发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "0用户端  1商家端  2骑手端", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping("/sendByFind")
    public Result sendByFind(@RequestParam String phone, @RequestParam Integer type, HttpSession session) {
        try {
            ParentUser user = null;
            if (type == 0) {
                user = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
            } else if (type == 1) {
                user = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.PHONE, phone));
            } else {
                user = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.PHONE, phone));
            }
            if (user == null) {
                return new Result().erro("该用户未注册");
            }
            String code = RandomUtil.randomNumbers(6);
            SendSmsResponse sendSmsResponse = MessageUtil.sendSmsByFindPwd(phone, code);
            String code1 = sendSmsResponse.getCode();
            System.out.println(code1);
            if ("OK".equalsIgnoreCase(code1)) {
                session.setAttribute(phone + "find" + type, code);
                session.setMaxInactiveInterval(60 * 5);
                return new Result().success("发送成功");
            } else {
                return new Result().erro("发送失败");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result().erro("发送失败");
        }
    }

    @ApiOperation("用户端短信登录短信发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping("/sendByUserLogin")
    public Result sendByUserLogin(@RequestParam String phone, HttpSession session) {
        try {
            ParentUser user = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
            if (user == null) {
                return new Result().erro("该用户未注册");
            }
            String code = RandomUtil.randomNumbers(6);
            SendSmsResponse sendSmsResponse = MessageUtil.sendSmsByLogin(phone, code);
            String code1 = sendSmsResponse.getCode();
            System.out.println(code1);
            if ("OK".equalsIgnoreCase(code1)) {
                session.setAttribute(phone + "login" + "0", code);
                session.setMaxInactiveInterval(60 * 5);
                return new Result().success("发送成功");
            } else {
                return new Result().erro("发送失败");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result().erro("发送失败");
        }
    }

    @ApiOperation("用户端绑定账号短信发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping("/sendByBindAccount")
    public Result sendByBindAccount(@RequestParam String phone, HttpSession session) {
        try {
            String code = RandomUtil.randomNumbers(6);
            System.out.println("code = " + code);
            SendSmsResponse sendSmsResponse = MessageUtil.sendSmsByLogin(phone, code);
            String code1 = sendSmsResponse.getCode();
            System.out.println(code1);
            if ("OK".equalsIgnoreCase(code1)) {
                session.setAttribute(phone + "bind" + "0", code);
                session.setMaxInactiveInterval(60 * 5);
                return new Result().success("发送成功");
            } else {
                return new Result().erro("发送失败");
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result().erro("发送失败");
        }
    }
}
