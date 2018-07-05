package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ParentUserEx;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.IpUtil;
import com.jh.jsuk.utils.JwtHelper;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = {"后台相关"})
@RestController
@RequestMapping("/adm")
public class WMainController {

    @Autowired
    Session session;

    @Autowired
    UserService userService;

    @Autowired
    DistributionUserService distributionUserService;

    @Autowired
    ManagerUserService managerUserService;

    @Autowired
    JwtHelper jwtHelper;

    @ApiOperation("后台-登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", required = true, value = "密码", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", required = true, value = "类型(商家 shp 骑手 dsb 用户 usr 管理员 amd 运维 rot)", paramType = "query", dataType = "string"),
    })
    @PostMapping("/login")
    public R list(String phone, String password, String type, HttpServletRequest requeset) throws Exception {
        Map<String, Object> map = new HashMap<>();
        UserType userType = EnumUitl.toEnum(UserType.class, type, "getShortKey");
        ParentUserEx user = null;
        if (UserType.USER.equals(userType)) {
            User us = userService.selectOne(new EntityWrapper<User>().eq("phone", phone));
            if (us != null)
                user = us.toParentUser();
        } else if (UserType.SHOP.equals(userType)) {
            ManagerUser us = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq("phone", phone));
            if (us != null)
                user = us.toParentUser();
        } else if (UserType.DISTRIBUTION.equals(userType)) {
            DistributionUser us = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq("phone", phone));
            if (us != null)
                user = us.toParentUser();
        } else if (userType.hasManageUserType()) {
            ManagerUser us = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq("phone", phone));
            if (us != null)
                user = us.toParentUser();
        }
        if (user == null) {
            return R.err(-11, "用户名或密码错误");
        }
        if (!StrUtil.equals(password, user.getPassword())) {
            return R.err(-11, "用户名或密码错误");
        }
        if (!user.canUse()) {
            return R.err(-11, "该账号已被禁用");
        }
        session.setUserType(userType);
        session.fromParentUserEx(user);
        session.setLogin(true);
        Date login = new Date();
        long date = Math.round((double) new Date().getTime() / 1000) * 1000;
        login.setTime(date);
        userService.updateLoginStatus(user.getUserId(), userType, IpUtil.getIpAddr(requeset), login);
        session.setLastLogin(login);
        map.put("token", jwtHelper.createAccessToken(user.getUserId(), userType.getKey()));
        return R.succ(map);
    }

    @ApiOperation("后台-退出")
    @PostMapping("/quit")
    public R loginOut(HttpSession session) {
        session.invalidate();
        return R.succ();
    }

    @ApiOperation("后台-session")
    @GetMapping("session")
    public R session() {
        Map<String, Object> map = new HashMap<>();
        map.put("useId", session.getUserId());
        map.put("type", session.getUserType());
        map.put("phone", session.getPhone());
        map.put("nickName", session.getNickName());
        map.put("lastLogin", session.getLastLogin());
        map.put("shopName", session.getShopName());
        return R.succ(map);
    }


}
