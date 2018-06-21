package com.jh.jsuk.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.http.UriVariables;
import com.egzosn.pay.common.util.XML;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayService;
import com.egzosn.pay.wx.bean.WxTransactionType;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * luopa 在 2018/6/21 创建.
 */
@RestController
@RequestMapping("/pay")
@PropertySource(value = "classpath:payinfo.properties")
public class PayController {
    @Autowired
    UserOrderService userOrderService;
    //支付宝服务
    private PayService aliservice = null;
    //微信服务
    private PayService wxservice = null;
    @Resource
    private Environment env;

    /**
     * 加载支付配置信息
     */
    @PostConstruct
    public void init() {
        /*读取微信配置信息*/
        String wx_appid = env.getProperty("APP_ID");
        String wx_mchid = env.getProperty("MCH_ID");
        String wx_api_key = env.getProperty("API_KEY");
        String wx_sign_type = env.getProperty("SIGN_TYPE");
        String wx_notifyurl = env.getProperty("NOTIFYURL");
        String wx_inputcharset = env.getProperty("INPUTCHARSET");
        String wx_paytype = env.getProperty("WX_PAYTYPE");
        /*读取支付宝配置信息*/
        String ali_pid = env.getProperty("pid");
        String ali_appid = env.getProperty("appid");
        String ali_public_key = env.getProperty("public_key");
        String ali_alipay_public_key = env.getProperty("alipay_public_key");
        String ali_notifyUrl = env.getProperty("notifyUrl");
        String ali_signType = env.getProperty("signType");
        String ali_seller = env.getProperty("seller");
        String ali_inputCharset = env.getProperty("inputCharset");
        Boolean ali_test = Boolean.valueOf(env.getProperty("test"));
        String ali_ZFB_PAYTYPE = env.getProperty("ZFB_PAYTYPE");

        //初始化支付宝
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setPid(ali_pid);
        aliPayConfigStorage.setAppId(ali_appid);
        //支付宝公钥
        aliPayConfigStorage.setKeyPublic(ali_public_key);
        //私钥
        aliPayConfigStorage.setKeyPrivate(ali_alipay_public_key);
        //收款号
        aliPayConfigStorage.setSeller(ali_seller);
        aliPayConfigStorage.setNotifyUrl(ali_notifyUrl);
        aliPayConfigStorage.setSignType(ali_signType);
        aliPayConfigStorage.setInputCharset(ali_inputCharset);
        //是否为测试账号，沙箱环境
        aliPayConfigStorage.setTest(ali_test);
        aliPayConfigStorage.setPayType(ali_ZFB_PAYTYPE);
        aliservice = new AliPayService(aliPayConfigStorage);

        //初始化微信
        WxPayConfigStorage wxPayConfigStorage = new WxPayConfigStorage();
        wxPayConfigStorage.setMchId(wx_mchid);
        wxPayConfigStorage.setAppid(wx_appid);
        wxPayConfigStorage.setKeyPrivate(wx_api_key);
        wxPayConfigStorage.setNotifyUrl(wx_notifyurl);
        wxPayConfigStorage.setSignType(wx_sign_type);
        wxPayConfigStorage.setInputCharset(wx_inputcharset);
        wxPayConfigStorage.setPayType(wx_paytype);
        wxservice = new WxPayService(wxPayConfigStorage);
    }


    /**
     * 生成系统订单信息
     *
     * @param userId
     * @param goodsId
     * @param carId
     * @return
     */
    @RequestMapping("placeAnOrder")
    @ResponseBody
    public ServerResponse placeAnOrder(Integer userId, String goodsId, String carId) {
        if (ObjectUtil.isNull(userId) || StrUtil.isBlank(goodsId)) {
            return ServerResponse.createByErrorMessage("参数错误!");
        }
        return null;
    }

    /**
     * 预下单
     *
     * @param orderId     商户订单ID
     * @param payWay      支付方式
     * @param userId      用户Id
     * @param isDeduction 是否抵扣  1:是  0:否
     * @return
     */
    @RequestMapping("pre_order")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse appPay(String orderId, String payWay, String userId, String isDeduction) {
        Callable<ServerResponse> callable = () -> {
            if (StrUtil.isBlank(orderId) || StrUtil.isBlank(userId) || StrUtil.isBlank(isDeduction) || StrUtil.isBlank(payWay)) {
                return ServerResponse.createByErrorMessage("参数错误!");
            }
            UserOrder orderRecord = userOrderService.selectOne(new MyEntityWrapper<UserOrder>().eq(UserOrder.ORDER_NUM, orderId));
            if (orderRecord == null) {
                return ServerResponse.createByErrorMessage("订单不存在");
            }
            if (orderRecord.getStatus() == 20) {
                return ServerResponse.createByErrorMessage("订单已支付,请勿重复支付");
            }
            PayOrder payOrder = null;

            //支付方式(0:支付宝，1:微信，2:苹果内购)
            if (StrUtil.equals(payWay, "0")) {
                //TODO 支付宝支付
                payOrder.setTransactionType(AliTransactionType.APP);
                Map<String, Object> orderInfo = aliservice.orderInfo(payOrder);
                System.out.println("\n" + orderInfo);
                //Map转化为对应得参数字符串

                return ServerResponse.createBySuccess(UriVariables.getMapToParameters(aliservice.orderInfo(payOrder)));
            }
            if (StrUtil.equals(payWay, "1")) {
                //TODO 微信支付
                payOrder.setTransactionType(WxTransactionType.APP);
                Map<String, Object> orderInfo = wxservice.orderInfo(payOrder);
                //Map转化为对应得参数字符串

                return ServerResponse.createBySuccess(orderInfo);
            }
            if (StrUtil.equals(payWay, "2")) {
                //TODO 苹果内购
                //扣除余额
            }
            return ServerResponse.createByErrorMessage("参数错误!");
        };
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("系统繁忙,请稍后再试");
        }
    }

    /**
     * 支付回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "alipayBack.json")
    public String alipayBack(HttpServletRequest request) throws IOException {
        //获取支付方返回的对应参数
        Map<String, Object> params = aliservice.getParameter2Map(request.getParameterMap(), request.getInputStream());
        params.forEach((k, v) -> System.out.println(k + ":" + v));
        //校验
        if (aliservice.verify(params)) {
            //这里处理业务逻辑
        }
        System.out.println("****************************************************************");
        System.out.println(UriVariables.getMapToParameters(params));
        System.out.println("验签失败");
        System.out.println("*****************************************************************");
        return aliservice.getPayOutMessage("fail", "失败").toMessage();
    }

    /**
     * 微信回调
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "wxpayBack.json")
    public String wxpayBack(HttpServletRequest request) throws IOException {
        //获取支付方返回的对应参数
        Map<String, Object> params = wxservice.getParameter2Map(request.getParameterMap(), request.getInputStream());
        params.forEach((k, v) -> System.out.println(k + ":" + v));
        String requestXML = XML.getMap2Xml(params);
        System.out.println(requestXML);
        //校验
        if (wxservice.verify(params)) {
            //验签成功
        }
        System.out.println("****************************************************************");
        System.out.println(UriVariables.getMapToParameters(params));
        System.out.println("验签失败");
        System.out.println("*****************************************************************");
        return wxservice.getPayOutMessage("fail", "失败").toMessage();
    }

}
