package com.jh.jsuk.controller;


import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.Dictionary;
import com.jh.jsuk.entity.jwt.AccessToken;
import com.jh.jsuk.entity.jwt.JwtParam;
import com.jh.jsuk.entity.vo.PlatformUserVo;
import com.jh.jsuk.entity.vo.UserInfo;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.*;
import com.jh.jsuk.utils.wx.MD5Util;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 普通用户 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Slf4j
@Api(tags = {"用户登录相关操作API:"})
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {
    @Autowired
    UserRemainderService userRemainderService;
    @Autowired
    UserCouponService userCouponService;
    @Autowired
    CollectService collectService;
    @Autowired
    CollectGoodsService collectGoodsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    UserAddressService userAddressService;
    @Autowired
    UserOrderService userOrderService;

    @Autowired
    UserInviteLogService userInviteLogService;

    @PostMapping("/update")
    public R update(User user) {
        user.setPassword(null);
        user.setQqToken(null);
        user.setWeiboToken(null);
        user.setWxToken(null);
        user.updateById();
        return R.succ();
    }

    @GetMapping("/page")
    public R listPage(Page page, String kw, String nickName, String[] date) {
        List<String> dates = null;
        if (date != null && StrUtil.isNotBlank(date[0]) && StrUtil.isNotBlank(date[1])) {
            dates = Arrays.asList(date);
        }
        return R.succ(userService.listPage(page, kw, nickName, dates));
    }

    @GetMapping("/order")
    public R userORder(Page page, Integer id) {
        return R.succ(userOrderService.userOrder(page, id));
    }

    @GetMapping("/info")
    public R userInfo(Integer id) {
        R r = new R();
        Map<String, Object> map = new HashMap<>();
        User user = userService.selectById(id);
        if (user == null) {
            return r;
        }
        user.setPassword(null);
        user.setQqToken(null);
        user.setWeiboToken(null);
        user.setWxToken(null);
        map.put("base", user);
        Wrapper<UserAddress> wrapper = new EntityWrapper<>();
        wrapper.ne(UserAddress.IS_DEL, 1)
            .eq(UserAddress.USER_ID, id);
        Map<String, Object> statInfo = new HashMap<>();

        statInfo.put("consumption", userRemainderService.getConsumption(id));
        statInfo.put("orderCount", userOrderService.orderCount(id));
        statInfo.put("integral", userIntegralService.getIntegral(id));

        map.put("stat", statInfo);
        map.put("recvAddress", userAddressService.selectList(wrapper));
        r.success(map);
        return r;
    }

    @Autowired
    DistributionUserService distributionUserService;

    @Autowired
    ShopUserService shopUserService;

    @ApiOperation(value = "获取多类型用户信息", notes = "")
    @PostMapping("/userInfo")
    public Result bindAccount(@ApiParam("usrId 用户id") @RequestParam Integer usrId,
                              @ApiParam("userType 用户类型  SHOP 商家, DISTRIBUTION 骑手, USER 用户, ADMIN 平台") UserType userType) throws Exception {
        if (userType == null || usrId == null) {
            return R.err("参数错误");
        }
        UserInfo info = new UserInfo();
        switch (userType) {
            case DISTRIBUTION:
                DistributionUser distributionUser = distributionUserService.selectById(usrId);
                if (distributionUser == null)
                    break;
                info.setHeadImage(distributionUser.getHeadImg());
                info.setNickName(distributionUser.getName());
                info.setHeadImage(distributionUser.getPhone());
                break;
            case USER:
                User user = userService.selectById(usrId);
                if (user == null)
                    break;
                info.setHeadImage(user.getHeadImg());
                info.setNickName(user.getNickName());
                info.setHeadImage(user.getNickName());
                break;
            case SHOP:
            case ADMIN:
                ManagerUser managerUser = managerUserService.selectById(usrId);
                if (managerUser == null) {
                    break;
                }
                info.setHeadImage(managerUser.getHeadImg());
                info.setNickName(managerUser.getNickName());
                info.setPhone(managerUser.getPhone());
                break;
        }
        return R.succ(info);
    }

    @ApiOperation(value = "绑定账户", notes = "")
    @PostMapping("/bindAccount")
    public Result bindAccount(HttpServletRequest request, HttpSession session, @ApiParam("第三方唯一标识符") @RequestParam String token,
                              @ApiParam("tokenType  1==微信  0==QQ 2==新浪微博") @RequestParam Integer tokenType,
                              @ApiParam("headImg  头像地址") @RequestParam String headImg,
                              @ApiParam("nickName  昵称") @RequestParam String nickName,
                              @ApiParam("手机号") @RequestParam String phone,
                              @ApiParam("验证码") @RequestParam String code) throws Exception {
        //tokenType  1==微信 0==QQ 2==新浪微博
        String attribute = String.valueOf(session.getAttribute(phone + "bind" + "0"));
        if (StrUtil.isBlank(attribute)) {
            return new Result().erro("验证码已过期");
        }
        if (!StrUtil.equals(attribute, code)) {
            return new Result().erro("验证码错误");
        }
        User user = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
        Date loginTime = new Date();
        Result result = new Result();
        if (user != null) {
            if (user.getCanUse() == 0) {
                result.setCode((long) 2002);
                result.setMsg("非常抱歉，系统维护中，十万火急请打电话");
            } else {
                JwtParam jwtParam = new JwtParam();
                jwtParam.setUserId(user.getId());
                jwtParam.setLoginTime(loginTime);
                jwtParam.setLoginType(3);
                user.setLastLoginTime(loginTime);
                user.setLoginIp(IpUtil.getIpAddr(request));
                if (tokenType == 1) {
                    user.setWxToken(token);
                }
                if (tokenType == 0) {
                    user.setQqToken(token);
                }
                if (tokenType == 2) {
                    user.setWeiboToken(token);
                }
                if (StrUtil.isNotBlank(nickName)) {
                    user.setNickName(nickName);
                }
                if (StrUtil.isNotBlank(headImg)) {
                    user.setHeadImg(headImg);
                }
                userService.updateById(user);
                String subject = JwtHelper.generalSubject(jwtParam);
                String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
                AccessToken accessToken = new AccessToken();
                accessToken.setAccess_id(user.getId());
                accessToken.setAccess_token(jwt);
                Map<String, Object> p = new HashMap<>();
                p.put("userId", user.getId());
                result.setData(p);
                result.success("登录成功", accessToken);
                return result;
            }
        } else {
            User us = new User();
            if (tokenType == 1) {
                us.setWxToken(token);
            } else if (tokenType == 0) {
                us.setQqToken(token);
            } else if (tokenType == 2) {
                us.setWeiboToken(token);
            }
            us.setPhone(phone);
            us.setPassword(SecureUtil.md5(StrUtil.subSuf(phone, phone.length() - 6)));
            us.setLastLoginTime(loginTime);
            us.setLoginIp(IpUtil.getIpAddr(request));
            us.setNickName(nickName);
            us.setHeadImg(headImg);
            us.insert();
            JwtParam jwtParam = new JwtParam();
            jwtParam.setUserId(us.getId());
            jwtParam.setLoginTime(loginTime);
            jwtParam.setLoginType(UserType.USER.getKey());
            String subject = JwtHelper.generalSubject(jwtParam);
            String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
            AccessToken accessToken = new AccessToken();
            accessToken.setAccess_id(us.getId());
            accessToken.setAccess_token(jwt);
            Map<String, Object> p = new HashMap<>();
            p.put("userId", us.getId());
            result.setData(p);
            result.success("登录成功", accessToken);
            return result;
        }
        return new Result().erro("系统异常");
    }

    @ApiOperation("使用第三方唯一标识符登陆")
    @PostMapping("/loginByToken")
    public Result loginByToken(@ApiParam("第三方唯一标识符") @RequestParam String token,
                               @ApiParam("tokenType  1==微信  0==QQ 2==新浪微博") Integer tokenType,
                               HttpServletRequest request) throws Exception {
        User user = null;
        if (tokenType == 0) {
            user = userService.selectOne(new EntityWrapper<User>()
                .eq(User.QQ_TOKEN, token));
        } else if (tokenType == 1) {
            user = userService.selectOne(new EntityWrapper<User>()
                .eq(User.WX_TOKEN, token));
        } else if (tokenType == 2) {
            user = userService.selectOne(new EntityWrapper<User>()
                .eq(User.WEIBO_TOKEN, token));
        }
        if (user != null) {
            //是否可用,0=不可用,1=可用
            if (user.getCanUse() == 0) {
                return new Result().erro("账号已被禁用,请联系管理员");
            }
            Date loginTime = new Date();
            JwtParam jwtParam = new JwtParam();
            jwtParam.setUserId(user.getId());
            jwtParam.setLoginTime(loginTime);
            jwtParam.setLoginType(3);
            user.setLastLoginTime(loginTime);
            user.setLoginIp(IpUtil.getIpAddr(request));
            userService.updateById(user);
            String subject = JwtHelper.generalSubject(jwtParam);
            String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
            AccessToken accessToken = new AccessToken();
            accessToken.setAccess_id(user.getId());
            accessToken.setAccess_token(jwt);
            return new Result().success("登录成功", accessToken);
        } else {
            //tokenType  1==微信  0==QQ 2==新浪微博
            if (StrUtil.equals(String.valueOf(tokenType), "0")) {
                return new Result(Constant.BIND_QQ_NOAUTH, "您还没有绑定第三方登陆");
            }
            if (StrUtil.equals(String.valueOf(tokenType), "1")) {
                return new Result(Constant.BIND_WX_NOAUTH, "您还没有绑定第三方登陆");
            }
            if (StrUtil.equals(String.valueOf(tokenType), "2")) {
                return new Result(Constant.BIND_WX_NOAUTH, "您还没有绑定第三方登陆");
            }
            return new Result().erro("网络繁忙");
        }
    }

    @ApiOperation("客户登陆--手机号/邮箱登录")
    @PostMapping("/login")
    public Result login(@RequestParam @ApiParam(value = "手机号", required = true) String phone,
                        @RequestParam @ApiParam(value = "密码", required = true) String password,
                        HttpServletRequest request) throws Exception {
        Result result = new Result();
        User user = userService.selectOne(new EntityWrapper<User>()
            .eq(User.PHONE, phone));
        if (user != null) {
            if (user.getCanUse() == 0) {
                result.setCode((long) -1);
                result.setMsg("非常抱歉，系统维护中，十万火急请打电话");
            } else if (StrUtil.equals(password, user.getPassword())) {
                Date loginTime = new Date();
                user.setLastLoginTime(loginTime);
                user.setLoginIp(IpUtil.getIpAddr(request));
                userService.updateById(user);

                JwtParam jwtParam = new JwtParam();
                jwtParam.setUserId(user.getId());
                jwtParam.setLoginTime(loginTime);
                jwtParam.setLoginType(3);
                String subject = JwtHelper.generalSubject(jwtParam);
                String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
                AccessToken accessToken = new AccessToken();
                accessToken.setAccess_id(user.getId());
                accessToken.setAccess_token(jwt);
                Map<String, Object> p = new HashMap<>();
                p.put("userId", user.getId());
                result.setData(p);
                result.success("登录成功", accessToken);
            } else {
                result.erro("密码错误");
            }
        } else {
            result.erro("该用户不存在");
        }
        return result;
    }

    @ApiOperation("客户登陆--手机验证码登录")
    @PostMapping("/loginByCode")
    public Result login(@RequestParam @ApiParam(value = "手机号", required = true) String phone,
                        @RequestParam @ApiParam(value = "验证码", required = true) String code,
                        HttpServletRequest request,
                        HttpSession session) throws Exception {
        Result result = new Result();
        String verificationCode = (String) session.getAttribute(phone + "login" + "0");
        if (verificationCode != null) {
            if (code.equals(verificationCode)) {
                User user = userService.selectOne(new EntityWrapper<User>()
                    .eq(User.PHONE, phone));
                if (user != null) {
                    if (user.getCanUse() == 0) {
                        result.setCode((long) 2002);
                        result.setMsg("非常抱歉，系统维护中，十万火急请打电话");
                    } else {
                        Date loginTime = new Date();
                        JwtParam jwtParam = new JwtParam();
                        jwtParam.setUserId(user.getId());
                        jwtParam.setLoginTime(loginTime);
                        jwtParam.setLoginType(3);
                        user.setLastLoginTime(loginTime);
                        user.setLoginIp(IpUtil.getIpAddr(request));
                        userService.updateById(user);
                        String subject = JwtHelper.generalSubject(jwtParam);
                        String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
                        AccessToken accessToken = new AccessToken();
                        accessToken.setAccess_id(user.getId());
                        accessToken.setAccess_token(jwt);
                        Map<String, Object> p = new HashMap<>();
                        p.put("userId", user.getId());
                        result.setData(p);
                        result.success("登录成功", accessToken);
                    }
                } else {
                    result.erro("该用户不存在");
                }
            } else {
                return new Result().erro("验证码失效");
            }
        } else {
            return new Result().erro("验证码过期");
        }
        return result;
    }

    @ApiOperation("用户注册")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "phone", value = "手机号",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "password", value = "密码",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "code", value = "验证码",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "inviteUserId", value = "邀请人 id",
            required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "token", value = "第三方登陆唯一标识符",
            required = false, paramType = "query", dataType = "string")
    })
    @PostMapping("/register")
    public Result register(@ModelAttribute User user, @RequestParam String code, @RequestParam(required = false) Integer inviteUserId, HttpSession session) throws Exception {
        try {
            // 效验手机验证码
            String verificationCode = (String) session.getAttribute(user.getPhone() + "register0");
            if (verificationCode == null) {
                throw new MessageException("验证码错误");
            }
            if (!code.equals(verificationCode)) {
                throw new MessageException("验证码过期");
            }
            // 验证码正确
            user.setPassword(user.getPassword());
            user.setCanUse(1);
            //获取默认头像
            Dictionary dictionaryImg = dictionaryService.selectOne(new EntityWrapper<Dictionary>().
                eq("code", "user_default_img"));
            user.setHeadImg(dictionaryImg.getValue());
            //获取默认昵称
            Dictionary dictionaryName = dictionaryService.selectOne(new EntityWrapper<Dictionary>().
                eq("code", "user_default_name"));
            user.setNickName(dictionaryName.getValue());
            user.insert();
            if (inviteUserId != null) {
                userInviteLogService.addInvite(user.getId(), inviteUserId);
            }
            return new Result().success();
        } catch (MybatisPlusException e) {
            throw new RuntimeException("服务器繁忙");
        }
    }

    @ApiOperation("客户邀请注册")
    @ApiImplicitParams(value = {
        @ApiImplicitParam(name = "phone", value = "手机号",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "password", value = "密码",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "code", value = "验证码",
            required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "token", value = "第三方登陆唯一标识符",
            required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping("/invitationRegister")
    public Result uiInvitationRegister(@ModelAttribute User user, @RequestParam String code, @RequestParam Integer inviteById, HttpSession session) {
        try {
            String verificationCode = (String) session.getAttribute(user.getPhone() + "register0");
            if (verificationCode != null) {
                if (code.equals(verificationCode)) {
                    user.setPassword(user.getPassword());
                    user.setCanUse(1);
                    //TODO 邀请人user.setInvitationId(inviteById);
                    //获取默认头像
                    Dictionary dictionaryImg = dictionaryService.selectOne(new EntityWrapper<Dictionary>().eq("code", "user_default_img"));
                    user.setHeadImg(dictionaryImg.getValue());
                    //获取默认昵称
                    Dictionary dictionaryName = dictionaryService.selectOne(new EntityWrapper<Dictionary>().eq("code", "user_default_name"));
                    user.setNickName(dictionaryName.getValue());
                    user.insert();
                } else {
                    return new Result().erro("验证码失效");
                }
            } else {
                return new Result().erro("验证码过期");
            }
        } catch (MybatisPlusException e) {
            throw new RuntimeException("该手机号已注册");
        }
        return new Result().success();
    }

    @ApiOperation("用户信息修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "headImg", value = "头像", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "nickName", value = "昵称", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "gender", value = "性别", paramType = "query", dataType = "int")
    })
    @PostMapping("/edit")
    public Result edit(@ModelAttribute User user, Integer userId) throws Exception {
//        if (user.getId() == null) {
//            throw new MessageException("用户id不存在");
//        }
        if (userId == null) {
            throw new MessageException("用户未登录");
        }
        user.setId(userId);
        if (StrUtil.isNotBlank(user.getPhone())) {
            if (Validator.isMobile(user.getPhone())) {
                User oneuser = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, user.getPhone()));
                if (oneuser != null) {
                    return new Result().erro("手机号已被绑定");
                }
            } else {
                return new Result().erro("请输入正确的手机号");
            }
        }
        user.updateById();
        return new Result().success();
    }

    @ApiOperation("根据手机验证码修改密码")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "password", value = "新密码", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping("/editPasswordByCode")
    public Result editPassword(@RequestParam String phone, @RequestParam String code, @RequestParam String password, HttpSession session) {
        String verificationCode = (String) session.getAttribute(phone + "find0");
        if (verificationCode != null) {
            if (code.equals(verificationCode)) {
                User user = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
                user.setPassword(password);
                user.updateById();
            } else {
                return new Result().erro("验证码失效");
            }
        } else {
            return new Result().erro("验证码过期");
        }
        return new Result().success();
    }

    @ApiOperation("根据旧密码修改密码")
    @PostMapping("/editPasswordByOld")
    public Result editPasswordByOld(@ApiParam(value = "用户ID", required = true) Integer userId,
                                    @ApiParam(value = "旧密码", required = true) String oldPassword,
                                    @ApiParam(value = "新密码", required = true) String newPassword) {
        User user = userService.selectOne(new EntityWrapper<User>().
            eq(User.ID, userId));
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                user.updateById();
                return new Result().success("修改成功");
            } else {
                return new Result().erro("旧密码错误");
            }
        } else {
            return new Result().erro("没有找到该用户");
        }
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/getInfo")
    public Result getInfo(@ApiParam("用户ID") Integer userId) {
        User user = userService.selectById(userId);
        return new Result().success(user);
    }

    @ApiOperation(value = "获取积分/余额/优惠券/收藏/用户信息", notes = " `level`='会员等级(0:普通会员 1:铜牌会员 2:银牌会员 3:金牌会员)")
    @PostMapping("/getCount")
    public Result getCount(@ApiParam("用户ID") Integer userId) {
        if (userId == null) {
            return new Result().erro("未登录");
        }
        // 封装数据map
        Map<String, Object> map = new HashMap<>();
        /**
         * 获取用户总积分
         */
        List<UserIntegral> userIntegrals = userIntegralService.selectList(new EntityWrapper<UserIntegral>()
            .eq(UserIntegral.USER_ID, userId));
        // 如果余额表没有该用户信息
        if (userIntegrals.size() == 0) {
            map.put("sum_jiFen", 0);
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
            map.put("sum_jiFen", sum);
        }
        /**
         * 获取用户总余额
         */

        List<UserRemainder> userRemainderList = userRemainderService.selectList(new EntityWrapper<UserRemainder>().eq(UserRemainder.USER_ID, userId));
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
            map.put("sum_yuE", decimalSum);
        } else {
            map.put("sum_yuE", 0);
        }
        /**
         * 获取用户优惠券总数
         */
        int yhCount = userCouponService.selectCount(new EntityWrapper<UserCoupon>()
            .eq(UserCoupon.USER_ID, userId)
            .ne(UserCoupon.IS_USED, 1));
        map.put("sum_youHuiJuan", yhCount);
        /**
         * 获取收藏总数
         */
        int scCount = collectGoodsService.selectCount(new EntityWrapper<CollectGoods>()
            .eq(CollectGoods.USER_ID, userId));
        map.put("sum_shouCang", scCount);
        /**
         * 获取用户信息
         */
        User user = userService.selectById(userId);
        map.put("user", user);
        return new Result().success(map);
    }

    @RequestMapping("/ui/getUserList")
    public Result getUserList(Page page, @RequestParam(required = false) String phone) {
        Page userPage = userService.selectPage(page, new MyEntityWrapper<User>().like(User.PHONE, phone).orderDesc(Collections.singleton(User
            .CREATE_TIME)));
        return new Result().success(userPage);
    }


    /**
     * 修改密码
     */
    @PostMapping(value = "/ui/EditPassword")
    public Result editPassword(HttpSession session, String oldPassword, String newPassword) throws Exception {
        Integer userId = Integer.parseInt(session.getAttribute("adminId").toString());
        String opw = SecureUtil.md5(oldPassword);
        ManagerUser user = managerUserService.selectOne((new MyEntityWrapper<ManagerUser>().eq(ManagerUser.ID, userId)
            .eq(ManagerUser.PASSWORD, opw)));
        if (user != null) {
            user.setPassword(SecureUtil.md5(newPassword));
            user.updateById();
            return new Result().success("修改成功");
        } else {
            return new Result().erro("修改失败，密码错误");
        }
    }

    /**
     * 获取管理员列表
     */
    @RequestMapping(value = "/ui/getAdminList", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getAdminList(HttpSession session, Page page) {
        Page userPage = managerUserService.selectPage(page, new MyEntityWrapper<ManagerUser>().orderDesc(Collections.singleton(ManagerUser
            .CREATE_TIME)));
        return new Result().success(userPage);
    }

    /**
     * 管理账号是否可用
     */
    @PostMapping(value = "/ui/editAdmin")
    public Result editAdmin(@ModelAttribute ManagerUser user, Integer userId) {
        if (userId != null) {
            user.setId(userId);
        }
        user.updateById();
        return new Result().success();
    }

    /**
     * 增加管理账号
     */
    @PostMapping("/ui/addAdminUser")
    public Result addAdminUser(@ModelAttribute ManagerUser user) {
        ManagerUser su = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.USER_NAME, user.getUserName()));
        if (su == null) {
            user.setPassword(MD5Util.getMD5(user.getPassword()));
            user.setCanUse(1);
            user.insert();
        } else {
            return new Result().erro("该用户名已注册");
        }
        return new Result().success();
    }

    @PostMapping("/ui/edit")
    public Result uiEdit(HttpSession session, @ModelAttribute User user, Integer userId) {
        if (userId != null) {
            user.setId(userId);
        }
        user.updateById();
        return new Result().success();
    }


//    @ApiOperation("平台管理系统-用户管理-根据用户id、账号搜索")
////    @RequestMapping(value="/searchUserListBy",method={RequestMethod.POST,RequestMethod.GET})
////    public Result searchUserListBy(@RequestParam String keywords,Integer current,Integer size){
////        current=current==null?1:current;
////        size=size==null?10:size;
////        Page<User> userPage=new Page<>(current,size);
////        List<User> userList=userService.selectUserListBy(keywords);
////
////        return new Result().success(userPage.setRecords(userList));
////    }


    @ApiOperation("平台管理系统-用户管理-用户列表")
    @RequestMapping(value = "/getUserPage", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getUserPage(Page page, String searchKeywords) {
        Page<User> userPage = userService.selectPage(page,
            new EntityWrapper<User>()
                .like(searchKeywords != null, "nick_name", searchKeywords)
                .eq(User.CAN_USE, 1)
        );
        List<User> userList = userPage.getRecords();

        // Page plateformUserVoPage=new Page(page.getCurrent(),page.getSize());
        List<PlatformUserVo> plateformUserVoList = new ArrayList<>();

        for (User user : userList) {
            PlatformUserVo platformUserVo = new PlatformUserVo();
            platformUserVo.setUser(user);
            Integer userId = user.getId();
            List<UserOrder> userOrderList = userOrderService.selectList(new EntityWrapper<UserOrder>()
                .eq(UserOrder.USER_ID, userId)
            );
            //该用户的消费金额
            BigDecimal consumeCount = new BigDecimal(0.00);
            if (userOrderList != null && userOrderList.size() > 0) {
                //该用户的订单数量
                platformUserVo.setOrderCount(userOrderList.size());
                for (UserOrder userOrder : userOrderList) {
                    BigDecimal orderRealPrice = userOrder.getOrderRealPrice();
                    if (orderRealPrice == null) {
                        orderRealPrice = new BigDecimal("0.00");
                    }
                    consumeCount = consumeCount.add(orderRealPrice);
                }
            } else {
                platformUserVo.setOrderCount(0);
            }
            platformUserVo.setConsumeCount(consumeCount);
            //该用户可用积分
            UserIntegral userIntegral = userIntegralService.selectOne(new EntityWrapper<UserIntegral>()
                .eq(UserIntegral.USER_ID, userId)
            );
            if (userIntegral != null) {
                platformUserVo.setIntegralCount(userIntegral.getIntegralNumber());
            } else {
                platformUserVo.setIntegralCount(0);
            }
            plateformUserVoList.add(platformUserVo);
        }
        return new Result().success(page.setRecords(plateformUserVoList));
    }

    @ApiOperation("后台管理系统-用户列表-用户详情")
    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getUserInfo(@RequestParam Integer detailUserId, Page page) {
        System.out.println("userId:" + detailUserId);
        PlatformUserVo platformUserVo = new PlatformUserVo();
        User user = userService.selectOne(new EntityWrapper<User>()
            .eq(User.ID, detailUserId)
            .eq(User.CAN_USE, 1)
        );
        if (user == null) {
            return new Result().erro("该用户不可用");
        }
        platformUserVo.setUser(user);
        //查询用户地址列表
        List<UserAddress> userAddressList = userAddressService.selectList(new EntityWrapper<UserAddress>()
            .eq(UserAddress.USER_ID, detailUserId)
            .eq(UserAddress.IS_DEL, 0)
        );
        platformUserVo.setUserAddressList(userAddressList);
        //查询订单列表
        Page<UserOrder> userOrderPage = userOrderService.selectPage(page,
            new EntityWrapper<UserOrder>()
                .eq(UserOrder.USER_ID, detailUserId)
                .ne(UserOrder.IS_SHOP_DEL, 1)
                .ne(UserOrder.IS_USER_DEL, 1)
        );
        if (userOrderPage != null && userOrderPage.getRecords().size() > 0) {
            platformUserVo.setUserOrderList(userOrderPage.getRecords());
        }
        return new Result().success(platformUserVo);
    }


    @ApiOperation("后台管理系统-用户管理-编辑用户信息")
    @RequestMapping(value = "/editUser", method = {RequestMethod.POST})
    public Result editUser(@ModelAttribute User user) {
        if (user == null) {
            return new Result().erro("参数错误");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            return new Result().erro("请设置密码");
        }
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        user.updateById();
        return new Result().success("用户信息编辑成功");
    }

    //@ApiOperation("后台管理系统-用户管理-删除用户")
    @RequestMapping(value = "/deleteUserById", method = {RequestMethod.GET, RequestMethod.POST})
    public Result deleteUserById(@RequestParam Integer delUserId) {
        User user = userService.selectOne(new EntityWrapper<User>().eq(User.ID, delUserId));
        if (user == null) {
            return new Result().erro("系统错误");
        }
        user.setCanUse(0);
        user.updateById();
        return new Result().success("用户删除成功");
    }


    @ApiOperation("后台管理系统-用户管理-用户详情-查看订单详情")
    @RequestMapping(value = "/getUserOrderInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getUserOrderInfo(@RequestParam Integer userId, @RequestParam Integer userOrderId) {
        UserOrder userOrder = userOrderService.selectOne(new EntityWrapper<UserOrder>()
            .eq(UserOrder.ID, userOrderId)
            .eq(UserOrder.USER_ID, userId)
        );
        return new Result().success(userOrder);
    }


    @ApiOperation("平台-获取用户")
    @RequestMapping(value = "/getUserInfoById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getUserInfoById(@RequestParam Integer userBianjiId) {
        User user = userService.selectOne(new EntityWrapper<User>().eq(User.ID, userBianjiId));
        if (user == null) {
            return new Result().erro("系统错误");
        }
        return new Result().success(user);
    }


}