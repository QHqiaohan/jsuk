package com.jh.jsuk.controller;


import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.JwtHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

}

