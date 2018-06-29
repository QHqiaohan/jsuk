package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.jwt.AccessToken;
import com.jh.jsuk.entity.jwt.JwtParam;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.IpUtil;
import com.jh.jsuk.utils.JwtHelper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理用户 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商户端/平台用户相关")
@RestController
@RequestMapping(value = "/managerUser", method = {RequestMethod.POST, RequestMethod.GET})
public class ManagerUserController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMoneyService shopMoneyService;
    @Autowired
    private ShopTodayStatisticsService shopTodayStatisticsService;
    @Autowired
    private ShopMonthStatisticsService shopMonthStatisticsService;
    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation(value = "商家端-收款码")
    @RequestMapping(value = "getQrCode", method = {RequestMethod.POST, RequestMethod.GET})
    public void getQrCode(Integer userId, HttpServletResponse response) throws IOException {
        Map map = Maps.newHashMap();
        map.put("key", userId);
        BufferedImage generate = QrCodeUtil.generate(JSONUtil.toJsonStr(map), 300, 300);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(generate, "png", out);
        byte[] img = out.toByteArray();
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        os.write(img);
        os.flush();
        os.close();
    }

    @ApiOperation("商家端-商家注册-返回验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/resultCode", method = {RequestMethod.POST, RequestMethod.GET})
    public Result resultCode(String phone, HttpSession session) {
        // 获取手机验证码
        String verificationCode = (String) session.getAttribute(phone + "register1");
        return new Result().success(verificationCode);
    }

    @ApiOperation("商家端-商家注册,保存商家信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "headImg", value = "店铺头像,不传使用默认头像", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "shopName", value = "店铺名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "address", value = "地址", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "modularId", value = "模块ID", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "legalPersonName", value = "法人姓名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cardNum", value = "身份证", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public Result register(String phone, String password, String headImg, String shopName, String address, Integer modularId,
                           String legalPersonName, String cardNum) {
        Shop shop = new Shop();
        if (headImg != null) {
            shop.setHeadImg(headImg);
        } else {
            // 默认头像
            Dictionary defaultImg = dictionaryService.selectOne(new EntityWrapper<Dictionary>()
                    .eq(Dictionary.CODE, "shop_default_img"));
            shop.setHeadImg(defaultImg.getValue());
        }
        shop.setShopName(shopName);
        shop.setModularId(modularId);
        shop.setAddress(address);
        shop.insert();

        ManagerUser managerUser = new ManagerUser();
        managerUser.setUserType(2);
        managerUser.setPhone(phone);
        managerUser.setPassword(password);
        managerUser.setAddress(address);
        managerUser.setUserName(phone);
        managerUser.setShopId(shop.getId());
        managerUser.insert();

        ShopUser shopUser = new ShopUser();
        shopUser.setIdCardNo(cardNum);
        shopUser.setLegalPersonName(legalPersonName);
        shopUser.setShopId(shop.getId());
        shopUser.setManagerUserId(managerUser.getId());
        shopUser.insert();

        return new Result().success();
    }

    @ApiOperation("商家端-商家登陆")
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public Result login(@RequestParam @ApiParam(value = "手机号", required = true) String phone,
                        @RequestParam @ApiParam(value = "密码", required = true) String password,
                        HttpServletRequest request) throws Exception {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.PHONE, phone));
        if (managerUser != null) {
            if (managerUser.getCanUse() == 0) {
                return new Result().erro("账号已被禁用,请联系客服!");
            } else {
                ShopUser shopUser = shopUserService.selectOne(new EntityWrapper<ShopUser>()
                        .eq(ShopUser.MANAGER_USER_ID, managerUser.getId()));
                if (shopUser.getIsCheck() == 0) {
                    return new Result().success("店铺审核中");
                } else if (shopUser.getIsCheck() == 1) {
                    if (StrUtil.equals(password, managerUser.getPassword())) {
                        Date loginTime = new Date();
                        JwtParam jwtParam = new JwtParam();
                        jwtParam.setUserId(managerUser.getId());
                        jwtParam.setLoginTime(loginTime);
                        jwtParam.setLoginType(1);
                        managerUser.setLastLoginTime(loginTime);
                        managerUser.setLoginIp(IpUtil.getIpAddr(request));
                        managerUserService.updateById(managerUser);
                        String subject = JwtHelper.generalSubject(jwtParam);
                        String jwt = jwtHelper.createJWT(Constant.JWT_ID, subject);
                        AccessToken accessToken = new AccessToken();
                        accessToken.setAccess_id(managerUser.getId());
                        accessToken.setAccess_token(jwt);
                        return new Result().success("登录成功", accessToken);
                    } else {
                        return new Result().erro("密码错误");
                    }
                }
            }
        }
        return new Result().erro("该用户不存在");
    }

    @ApiOperation("商家端-根据手机验证码修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, paramType = "query", dataType = "string"),
    })
    @RequestMapping(value = "/editPassword", method = {RequestMethod.POST, RequestMethod.GET})
    public Result editPassword(String phone, String code, String password, HttpSession session) {
        String verificationCode = (String) session.getAttribute(phone + "find1");
        if (verificationCode != null) {
            if (code.equals(verificationCode)) {
                ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                        .eq(ManagerUser.PHONE, phone));
                managerUser.setPassword(password);
                managerUser.updateById();
            } else {
                return new Result().erro("验证码失效");
            }
        } else {
            return new Result().erro("验证码过期");
        }
        return new Result().success();
    }

    @ApiOperation("商家端-首页信息")
    @RequestMapping(value = "/shopInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public Result shopInfo(Integer userId) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
        Integer shopId = managerUser.getShopId();
        // 封装结果map
        Map<String, Object> map = new HashMap<>();
        /**
         * 店铺信息
         */
        Shop shop = shopService.selectOne(new EntityWrapper<Shop>()
                .eq(Shop.ID, shopId));
        map.put("shop", shop);
        /**
         * 店铺余额
         */
        List<ShopMoney> shopMoneyList = shopMoneyService.selectList(new EntityWrapper<ShopMoney>()
                .eq(ShopMoney.SHOP_ID, shopId));
        if (CollectionUtils.isEmpty(shopMoneyList)) {
            map.put("money", 0);
        } else {
            // 初始化余额
            int sum = 0;
            for (ShopMoney shopMoney : shopMoneyList) {
                // 金额
                String money = shopMoney.getMoney();
                Integer m = Integer.parseInt(money);
                // 消费类型,计算
                Integer type = shopMoney.getType();
                if (type == 0) {
                    // 消费
                    sum -= m;
                } else if (type == 1) {
                    // 收入
                    sum += m;
                }
            }
            map.put("money", sum);
        }
        /**
         * 访客/订单/交易额
         */
        ShopTodayStatistics todayStatistics = shopTodayStatisticsService.selectOne(new EntityWrapper<ShopTodayStatistics>()
                .eq(ShopTodayStatistics.SHOP_ID, shopId));
        if (todayStatistics == null) {
            // 日访客
            map.put("todayVisitor", 0);
            // 日交易额
            map.put("todayMoney", 0);
        } else {
            map.put("todayVisitor", todayStatistics.getTodayVisitor());
            map.put("todayMoney", todayStatistics.getTodayMoney());
        }
        // 月订单
        ShopMonthStatistics monthStatistics = shopMonthStatisticsService.selectOne(new EntityWrapper<ShopMonthStatistics>()
                .eq(ShopMonthStatistics.SHOP_ID, shop));
        if (monthStatistics == null) {
            map.put("monthOrder", 0);
        } else {
            map.put("monthOrder", monthStatistics.getMonthOrder());
        }
        return new Result().success(map);
    }


}

