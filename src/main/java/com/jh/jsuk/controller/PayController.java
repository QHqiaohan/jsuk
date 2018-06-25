package com.jh.jsuk.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
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
import com.google.common.collect.Maps;
import com.jh.jsuk.entity.MemberConfig;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.service.MemberConfigService;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import com.jh.jsuk.utils.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * luopa 在 2018/6/21 创建.
 */
@Api(tags = {"用户支付相关操作API:"})
@RestController
@RequestMapping("/pay")
@PropertySource(value = "classpath:payinfo.properties")
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);
    @Autowired
    UserOrderService userOrderService;
    @Autowired
    MemberConfigService memberConfigService;
    @Autowired
    UserRemainderService userRemainderService;
    @Autowired
    UserService userService;
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
    @ApiOperation("下单 在本地系统生成订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId", value = "用户ID",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "goodsId", value = "商品ID",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "carId", value = "购物车ID",
                    required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orderType", value = "订单类型 0:普通订单 1:秒杀订单 2:会员购买 3:充值",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "memberConfigId", value = "充值配置ID",
                    required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "rechargeType", value = "充值类型11:安卓会员购买 12:安卓充值 21:IOS会员购买 22:IOS充值  31:公众号会员购买  32:公众号充值",
                    required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "paymentAmount", value = "到店支付金额",
                    required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "placeAnOrder", method = RequestMethod.POST)
    public Result placeAnOrder(Integer userId, String goodsId, String carId, String orderType, Integer memberConfigId, Integer rechargeType, String paymentAmount) {
        if (ObjectUtil.isNull(userId) || StrUtil.isBlank(goodsId)) {
            return new Result().erro("参数错误!");
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        if (StrUtil.equals(orderType, "2")) {
            //查询充值配置
            MemberConfig memberConfig = memberConfigService.selectOne(new MyEntityWrapper<MemberConfig>().eq(MemberConfig.ID, memberConfigId));
            UserRemainder userRemainder = new UserRemainder();
            userRemainder.setUserId(userId);
            userRemainder.setOrderNum("JSUKVIP" + RandomUtil.randomNumbers(18));
            userRemainder.setRemainder(new BigDecimal(memberConfig.getMemberPrice()));
            userRemainder.setMemberId(memberConfigId);
            userRemainder.setIsOk(0);
            userRemainder.setType(2);
            //新增购买充值记录
            userRemainder.insert();

            //充值类型11:安卓会员购买 12:安卓充值 21:IOS会员购买 22:IOS充值  31:公众号会员购买  32:公众号充值
            resultMap.put("orderType", orderType);
            resultMap.put("orderId", userRemainder.getOrderNum());
            return new Result().success(resultMap);
        } else if (StrUtil.equals(orderType, "0") || StrUtil.equals(orderType, "1")) {
            resultMap.put("orderType", orderType);
            resultMap.put("orderId", "JSUKDD" + RandomUtil.randomNumbers(18));
            return new Result().success(resultMap);
        } else if (StrUtil.equals(orderType, "3")) {
            UserRemainder userRemainder = new UserRemainder();
            userRemainder.setUserId(userId);
            userRemainder.setOrderNum("JSUKCZ" + RandomUtil.randomNumbers(18));
            userRemainder.setRemainder(new BigDecimal(paymentAmount));
            userRemainder.setIsOk(0);
            //充值
            userRemainder.setType(1);
            //新增用户充值记录
            userRemainder.insert();
            resultMap.put("orderType", orderType);
            resultMap.put("orderId", userRemainder.getOrderNum());
            return new Result().success(resultMap);
        } else {
            return new Result().erro("参数错误!");
        }
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
    @ApiOperation("预下单 在支付系统生成订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "orderId", value = "订单号",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "payWay", value = "支付方式 0:支付宝 1:微信",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "userId", value = "用户ID",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orderType", value = "订单类型 0:普通订单 1:秒杀订单 2:会员充值",
                    required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isDeduction", value = "是否使用折扣 1:是 0:否",
                    required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "pre_order", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public ServerResponse appPay(String orderId, String payWay, Integer userId, String orderType, String isDeduction) {
        Callable<ServerResponse> callable = () -> {
            if (StrUtil.isBlank(orderType) || StrUtil.isBlank(isDeduction) || StrUtil.isBlank(payWay)) {
                return ServerResponse.createByErrorMessage("参数错误!");
            }
            UserOrder orderRecord = null;
            UserRemainder userRemainder = null;
            PayOrder payOrder = null;
            //当为普通/秒杀订单时OrderId必须
            if (StrUtil.equals(orderType, "1") || StrUtil.equals(orderType, "0")) {
                if (StrUtil.isBlank(orderId)) {
                    return ServerResponse.createByErrorMessage("参数错误!");
                }
                orderRecord = userOrderService.selectOne(new MyEntityWrapper<UserOrder>().eq(UserOrder.ORDER_NUM, orderId)
                        .eq(UserOrder.STATUS, "0")
                );
                if (orderRecord == null) {
                    return ServerResponse.createByErrorMessage("订单不存在");
                }
                if (orderRecord.getStatus() > 2) {
                    return ServerResponse.createByErrorMessage("订单已支付,请勿重复支付");
                }
                payOrder = new PayOrder("巨商U客订单" + orderRecord.getOrderNum(), "巨商U客订单", orderRecord.getOrderPrice(), orderId);
            }

            //当为购买订单时memberConfigId必须
            if (StrUtil.equals(orderType, "2")) {
                userRemainder = userRemainderService.selectOne(new MyEntityWrapper<UserRemainder>().eq(UserRemainder.ORDER_NUM, orderId)
                        .eq(UserRemainder.IS_OK, "0")
                );
                if (userRemainder == null) {
                    return ServerResponse.createByErrorMessage("订单不存在");
                }

                if (userRemainder.getIsOk() > 0) {
                    return ServerResponse.createByErrorMessage("订单已支付,请勿重复支付");
                }
                payOrder = new PayOrder("巨商U客订单" + userRemainder.getOrderNum(), "巨商U客充值会员订单", userRemainder.getRemainder(), orderId);
            }

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
     * 支付宝回调
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping(value = "alipayBack.json", method = {RequestMethod.POST, RequestMethod.GET})
    public String alipayBack(HttpServletRequest request) throws IOException {
        //获取支付方返回的对应参数
        Map<String, Object> params = aliservice.getParameter2Map(request.getParameterMap(), request.getInputStream());
        params.forEach((k, v) -> System.out.println(k + ":" + v));
        //校验
        if (aliservice.verify(params)) {
            //这里处理业务逻辑
            businessProcess(params, "");


        }
        logger.error(UriVariables.getMapToParameters(params));
        logger.error("验签失败");
        return aliservice.getPayOutMessage("fail", "失败").toMessage();
    }

    private void businessProcess(Map<String, Object> params, String payWay) {
        //商户订单号
        String outTradeNo = params.get("out_trade_no") == null ? null : params.get("out_trade_no").toString();
        //这里处理业务逻辑
        logger.info("订单号 " + outTradeNo);

        UserOrder userOrder = userOrderService.selectOne(new MyEntityWrapper<UserOrder>().eq(UserOrder.ORDER_NUM, outTradeNo).eq(UserOrder.STATUS, "0"));
        UserRemainder userRemainder = userRemainderService.selectOne(new MyEntityWrapper<UserRemainder>().eq(UserRemainder.ORDER_NUM, outTradeNo).eq(UserRemainder.IS_OK, "0"));
        if (userOrder == null && userRemainder == null) {
            logger.error("订单不存在");
        } else {
            if (userOrder != null) {
                //TODO 普通订单
            }
            if (userRemainder != null) {
                //TODO 充值订单
                userRemainder.setIsOk(1);
                //更新充值订单状态
                userRemainder.updateById();
                MemberConfig memberConfig = memberConfigService.selectOne(new MyEntityWrapper<MemberConfig>().eq(MemberConfig.ID, userRemainder.getMemberId()));
                User user = userService.selectById(userRemainder.getUserId());
                user.setLevel(memberConfig.getId());
            }
        }
    }

    /**
     * 微信回调
     *
     * @param request
     * @return
     * @throws IOException
     */
    @ApiIgnore
    @RequestMapping(value = "wxpayBack.json", method = {RequestMethod.POST, RequestMethod.GET})
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
