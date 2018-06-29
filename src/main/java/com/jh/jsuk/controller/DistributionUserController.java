package com.jh.jsuk.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.UserBank;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.jwt.AccessToken;
import com.jh.jsuk.entity.jwt.JwtParam;
import com.jh.jsuk.entity.vo.DistributionUserVo;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.UserBankService;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * <p>
 * 骑手信息 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "骑手端相关")
@RestController
@RequestMapping(value = "/distributionUser")
public class DistributionUserController {
    @Autowired
    private DistributionUserService distributionUserService;
    @Autowired
    private UserOrderService orderService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    UserBankService userBankService;

    @ApiOperation("骑手-登陆")
    @PostMapping("/login")
    public Result login(@RequestParam @ApiParam(value = "手机号", required = true) String phone, @RequestParam @ApiParam(value = "密码", required = true) String password, HttpServletRequest request) throws Exception {
        Result result = new Result();
        DistributionUser user = distributionUserService.selectOne(new EntityWrapper<DistributionUser>()
                .eq(DistributionUser.PHONE, phone));
        if (user != null) {
            if (user.getCanUse() == 0) {
                result.setCode((long) 2002);
                result.setMsg("非常抱歉，系统维护中，十万火急请打电话!");
            } else if (MD5Util.getMD5(password).equals(user.getPassword())) {
                Date loginTime = new Date();
                JwtParam jwtParam = new JwtParam();
                jwtParam.setUserId(user.getId());
                jwtParam.setLoginTime(loginTime);
                jwtParam.setLoginType(2);
                user.setLastLoginTime(loginTime);
                user.setLoginIp(IpUtil.getIpAddr(request));
                distributionUserService.updateById(user);
                String subject = JwtHelper.generalSubject(jwtParam);
                String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
                AccessToken accessToken = new AccessToken();
                accessToken.setAccess_id(user.getId());
                accessToken.setAccess_token(jwt);
                result.success("登录成功", accessToken);
            } else {
                result.erro("密码错误");
            }
        } else {
            result.erro("该用户不存在");
        }
        return result;
    }

    @ApiOperation("骑手-注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", required = true, value = "密码", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "name", required = true, value = "姓名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idCardNo", required = true, value = "身份证号码", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cardFront", required = true, value = "身份证正面图", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cardBack", required = true, value = "身份证背面图", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", required = true, value = "验证码", paramType = "query", dataType = "string"),
    })
    @PostMapping("/register")
    public Result register(@ModelAttribute DistributionUser distributionUser, @RequestParam String code, HttpSession session) {
        try {
            String verificationCode = (String) session.getAttribute(distributionUser.getPhone() + "register2");
            if (verificationCode != null) {
                if (code.equals(verificationCode)) {
                    distributionUser.setPassword(distributionUser.getPassword());
                    distributionUser.setCanUse(0);
                    distributionUser.setStatus(0);
                    distributionUser.setPublishTime(new Date());
                    distributionUser.insert();
                    DateTime dateTime = DateUtil.offsetDay(new Date(), 5);
                    return new Result().success("提交审核成功", "预计" + DateUtil.format(dateTime, "yyyy-MM-dd HH:mm") + " 之前通过");
                } else {
                    return new Result().erro("验证码失效");
                }
            } else {
                return new Result().erro("验证码过期");
            }
        } catch (MybatisPlusException e) {
            throw new RuntimeException("该手机号已注册");
        }
    }

    @ApiOperation("骑手-信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "headImg", value = "头像", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "gender", value = "性别", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "age", value = "年龄", paramType = "query", dataType = "int"),
    })
    @PostMapping("/edit")
    public Result edit(@ModelAttribute DistributionUser distributionUser, Integer userId) {
        if (userId != null) {
            distributionUser.setId(userId);
        } else {
            if (distributionUser.getCanUse() == 1) {
                try {
                    MessageUtil.sendSmsByDistributionApply(distributionUser.getPhone());
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }
        distributionUser.updateById();
        return new Result().success();
    }

    @ApiOperation("骑手-绑定支付宝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankNumber", value = "支付宝账号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", dataType = "string"),
    })
    @PostMapping("/binding")
    public Result binding(UserBank bank,String phone,String code, Integer userId,HttpSession session) {
        Result result = new Result();
        String verificationCode = (String) session.getAttribute(phone + "register2");
        if(verificationCode == null)
            return result.erro("验证码已失效");
        Wrapper<UserBank> wrapper = new EntityWrapper<>();
        wrapper.eq(UserBank.USER_TYPE,1)
                .eq(UserBank.USER_ID,userId);
        UserBank userBank = userBankService.selectOne(wrapper);
        if(userBank != null)
            return result.erro("已绑定支付宝");
        bank.setUserType(1);
        bank.setBankName("支付宝");
        bank.setUserId(userId);
        bank.insert();
        return new Result().success();
    }

    @ApiOperation("骑手-修改绑定支付宝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankNumber", value = "支付宝账号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "query", dataType = "string"),
    })
    @PostMapping("/binding/edit")
    public Result bindingEdit(UserBank bank,String phone,String code, Integer userId,HttpSession session) {
        Result result = new Result();
        String verificationCode = (String) session.getAttribute(phone + "register2");
        if(verificationCode == null)
            return result.erro("验证码已失效");
        Wrapper<UserBank> wrapper = new EntityWrapper<>();
        wrapper.eq(UserBank.USER_TYPE,1)
                .eq(UserBank.BANK_NAME,"支付宝")
                .eq(UserBank.USER_ID,userId);
        userBankService.update(bank,wrapper);
        return new Result().success();
    }

    @ApiOperation("骑手获取支付宝绑定信息")
    @GetMapping("/binding/get")
    public Result binding(String userId) {
        Wrapper<UserBank> wrapper = new EntityWrapper<>();
        wrapper.eq(UserBank.USER_TYPE,1)
                .eq(UserBank.USER_ID,userId);
        return new Result().success(userBankService.selectList(wrapper));
    }

    @ApiIgnore
    @PostMapping("/ui/edit")
    public Result uiEdit(HttpSession session, @ModelAttribute DistributionUser distributionUser, Integer userId) {
        Integer adminRole = Integer.parseInt(session.getAttribute("adminRole").toString());
        if (adminRole == 2) {
            return new Result().erro("权限不足，请切换账号");
        }
        if (userId != null) {
            distributionUser.setId(userId);
        } else {
            if (distributionUser.getCanUse() == 1) {
                try {
                    MessageUtil.sendSmsByDistributionApply(distributionUser.getPhone());
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }
        distributionUser.updateById();
        return new Result().success();
    }

    @ApiOperation("骑手-更新骑手坐标")
    @PostMapping("/location")
    public Result location(@ApiParam(value = "经度", required = true) @RequestParam Double longitude, @ApiParam(value = "纬度", required = true) @RequestParam Double latitude, Integer userId) {
        DistributionUser distributionUser = new DistributionUser();
        distributionUser.setId(userId);
        distributionUser.setLongitude(longitude);
        distributionUser.setLatitude(latitude);
        distributionUser.updateById();
        return new Result().success();
    }

    @ApiOperation("骑手-查看骑手资料")
    @GetMapping("/getInfo")
    public Result getInfo(Integer userId) {
        DistributionUser distributionUser = distributionUserService.selectById(userId);
        return new Result().success(distributionUser);
    }

    @ApiOperation("骑手-修改骑手工作状态")
    @GetMapping("/editJobStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "jobStatus", value = "状态 0:休息 1:接单", required = true, paramType = "query", dataType = "int"),
    })
    public Result editJobStatus(Integer userId, Integer jobStatus) {
        DistributionUser distributionUser = distributionUserService.selectById(userId);
        if (distributionUser != null) {
            distributionUser.setIsOnline(jobStatus);
            distributionUserService.updateById(distributionUser);
            return new Result().success();
        }
        return new Result().erro("用户不存在");
    }

    @ApiOperation("骑手-根据手机验证码修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping("/editPassword")
    public Result editPassword(@RequestParam String phone, @RequestParam String code, @RequestParam String password, HttpSession session) {
        String verificationCode = (String) session.getAttribute(phone + "find2");
        if (verificationCode != null) {
            if (code.equals(verificationCode)) {
                DistributionUser distributionUser = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.PHONE, phone));
                distributionUser.setPassword(MD5Util.getMD5(password));
                distributionUser.updateById();
            } else {
                return new Result().erro("验证码失效");
            }
        } else {
            return new Result().erro("验证码过期");
        }
        return new Result().success();
    }

    @ApiOperation("骑手-修改手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping("/editPhone")
    public Result editPhone(Integer userId, @RequestParam String phone, @RequestParam String code, HttpSession session) {
        String verificationCode = (String) session.getAttribute(phone + "find2");
        if (verificationCode != null) {
            if (code.equals(verificationCode)) {
                DistributionUser distributionUser = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.ID, userId));
                if (distributionUser != null) {
                    distributionUser.setPhone(phone);
                    distributionUser.updateById();
                    return new Result().success("修改手机号成功");
                } else {
                    return new Result().erro("用户不存在");
                }
            } else {
                return new Result().erro("验证码失效");
            }
        } else {
            return new Result().erro("验证码过期");
        }
    }

    @ApiIgnore
    @GetMapping("/ui/getUserList")
    public Result getUserList(Page page, @RequestParam(required = false) String phone) {
        Page userPage = distributionUserService.selectPage(page, new MyEntityWrapper<DistributionUser>().like(DistributionUser.PHONE, phone).orderDesc(Collections.singleton(DistributionUser.PUBLISH_TIME)));
        return new Result().success(userPage);
    }

    /**
     * 拒绝
     *
     * @param id
     * @param desc
     * @return
     */
    @ApiIgnore
    @PostMapping("/ui/refuse")
    public Result uiRefuse(Integer id, String desc) {
        DistributionUser distributionUser = distributionUserService.selectById(id);
        distributionUser.setDesc(desc);
        distributionUser.setStatus(2);
        boolean b = distributionUserService.updateById(distributionUser);
        return b ? new Result().success("成功") : new Result().erro("失败");
    }

    /**
     * 同意
     *
     * @param id
     * @return
     */
    @ApiIgnore
    @PostMapping("/ui/adopt")
    public Result uiAdopte(Integer id) {
        DistributionUser distributionUser = distributionUserService.selectById(id);
        distributionUser.setStatus(1);
        boolean b = distributionUserService.updateById(distributionUser);
        return b ? new Result().success("成功") : new Result().erro("失败");
    }

    @ApiIgnore
    @GetMapping("/ui/getUserOrderNum")
    public Result getUserOrderNum(Integer id) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        int lastMonthMaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
        String LastMonthTwoDay = sdf.format(c.getTime()); //上月最后一天
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");
        String LastMonthOneDay = sdf2.format(c.getTime()); //上月第一天
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        String nowMonthOneDay = sdf2.format(c1.getTime()); //当前月第一天
        String nowMonthTwoDay = sdf.format(new Date()); //当前月现在时间
        //获取上月订单数量
        int lastMonthOrderNum = orderService.selectCount(new EntityWrapper<UserOrder>().eq("distribution_user_id", id).gt("status", 4).between("send_time", LastMonthOneDay, LastMonthTwoDay));
        //获取本月订单数量
        int nowMonthOrderNum = orderService.selectCount(new EntityWrapper<UserOrder>().eq("distribution_user_id", id).between("send_time", nowMonthOneDay, nowMonthTwoDay));
        DistributionUserVo duv = new DistributionUserVo();
        duv.setLastMonthNum(lastMonthOrderNum);
        duv.setNowMonthNum(nowMonthOrderNum);
        return new Result().success(duv);
    }

    @ApiIgnore
    @PostMapping("/editPasswordByOld")
    public Result editPasswordByOld(@RequestParam String phone, @RequestParam String password, @RequestParam String newPassword) {
        DistributionUser user = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.PHONE, phone));
        if (user != null) {
            if (user.getPassword().equals(MD5Util.getMD5(password))) {
                user.setPassword(MD5Util.getMD5(newPassword));
                user.updateById();
                return new Result().success();
            } else {
                return new Result().erro("旧密码错误");
            }
        } else {
            return new Result().erro("没有找到该用户");
        }
    }

    @ApiIgnore
    @PostMapping("/ui/addDUserFoAdmin")
    public Result uiAddDUserFoAdmin(HttpSession session, @ModelAttribute DistributionUser distributionUser) {
        Integer adminRole = Integer.parseInt(session.getAttribute("adminRole").toString());
        if (adminRole == 2) {
            return new Result().erro("权限不足，请切换账号");
        }
        DistributionUser distributionUser1 = distributionUser.selectById(distributionUser.getPhone());
        if (distributionUser1 == null) {
            distributionUser.setPassword(MD5Util.getMD5(distributionUser.getPassword()));
            distributionUser.setCanUse(1);
            distributionUser.insert();
            return new Result().success();
        } else {
            return new Result().erro("该手机号已注册");
        }

    }
}

