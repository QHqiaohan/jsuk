package com.jh.jsuk.utils;

public class AliPayUtil {
    private static final String APP_ID = "2017120600401931";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkObOOhADO8fOxoG6YR00EU+LBajib/jAYOcb4saAdSRNyI5qN5gP0mNnW1tsnKNEvfefnHxeQp3gDfCV+vWM+o4OC4wR40TvgY7Y6TAOLGS42QDL9eo/Si3hgR73nxnxrDw16JZcvauivpBc9ugMr0m2Z8r1HRVhcRAZamDhd9xsD5EYFGhmdYjnwDGElXT906iNC+7b7uqLRB0Krxk4Ql+ejMar/uDZKEaDRGFbPRSdz8IEnEv8pVWGbrtZLbqsovWt40RpbNwDmk2zHofeFu18Ic+RkZ5tC7BqV+1o3+uMumPd6Kay+qvJPxkuuP1slQ+2O6To1Vh2ZPJLvXJpNwIDAQAB";
    private static final String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDKrZJLNlEU1+eEAOkW1d/ATcw6wfJTdxyMRYtAJJYQCrdoszH9HCjtZ9Mgr1HWhrHtQGjQKYEl2a9G4Oya2YsSRUxcq3iXUmok8kC5wLgz7Ynammd6JBzj8rVaFKuaWrhHxdMb8wzi7s6h7POCEloSnpT8IcU30JcbvBwowdmht/MmotF10wOvtEvleO42XCUNlhVyoSsL/wLrYJaeULTmfDbs4utRxehg5C99j5DDQNGoMtq/3RIlzdcOCVXvHzelO7/5U+zNtGcGRc58/IySK/mOTMtb6aY4Fk9+IUmaLlhqrrEK2y8fhMmq79ttNcWksHcKJxcpnUIOLRUuthVhAgMBAAECggEANrEx+hPVO2+rrOZl/+CE+arEwtJVZwqMbJnTouJ8kCWkKp+4jJnNvYq34WkOB3rfhqtL3WhlzmX4s4K8tmif67VBjvxClWsK859fsgB8dJ7UBWaHJ+GY3jY/k+hiCkwS0GNkvXUP9CkHH5EGgQIcxkt+3q7qPk6OiFBxtNnitY2nxeL93+0vsyjkY2PHLm8c6+Jd4hB+3ICsj0coSFMibCB2t0LfV6GNAIYcIOX/1WumnpzN49nApha12+nEjrojRUq0f1tFNHHY28qylqMH9MyrBnWHwqvSbGa2debt2NkdXruTyJM07T2RgZn2AUN9bm+TY0k8IxeDvVuGiroqVQKBgQD2Abcdo0Yf9a6Qt5j613gdwQcls8sYZzyDPh8L0wBzdXQU5NlNoATMxNvU1Z+PvlUT/O7WxMxyAaK+xAY0K+PbqlJ1kpg1QhYJ0B7an2WsYolWzre2PFfrLj+wWBHsS0NPxqgobvZCjOtj+8a1cKjcMHeQe43mfcgHQs5EVBn1dwKBgQDS6UU4/6e/QfuMA4gMAUXr5ZqmjQU4gvsGW6HtPEKYKSXaf0sFsaAnACw+eNUN7OJrBjlPk7b4paWaJ5hEQCy5fGU4BDdQxezjuThcdgW4ij5AGH9haBGe5Q3VWcaG9SfiwqvHaohjoKXV2k451/4WDeFME3XdGWOOP25zbNTh5wKBgQCd9bH/rNQdN2K0d78Z6fqtmEEfm1egPsGjP+Mtc3nJqTQ1KlDBwCr01L6W5ehDoyH76J/vdYQU/CnktfeJzljLtoymA9AJ+nouKN124XymeMrY23ko4YxObW7lq1cu05DQC63z8HPdfaCznXoVDNbQM6rCMiOGSWOEDghl5V6ZtwKBgQCepPh8t1DmfCtKVh2vagW+OybowIp8x4kqbyKN4BX3fnwKrlUwIzvkGi0tkJYxs01cU4IoaWPoBDFckwvlyp/Zpcg9Km6xQy+820WZxuzmspXAgKzQCAe5DXIqWS0LtrwZxXSQu4F77wrOoTbWK7HjxO0C3GB7vu0zwmLCT8qRYQKBgQDu/G+/SPcP/AOayMhqk7UCckP9Ix+rjJRPrSiiTIyhoKoYSJjqfnmXLxAmin7gbljyvF/jzQ/PKUQT4Bd8AERcnG3hna8bvoXXCFoUrxi//JIR0BFD/w4uhcHsKwqEdgxk4FqTrmBWUP/S+O8zUkrN30WAvgBghmtKnj34NgJCiA==";

    /*public static AlipayTradeAppPayResponse getPayResponse(OrderVo order, String notifyUrl) throws AlipayApiException {
        if (order == null) {
            throw new NullPointerException("找不到该订单");
        }
        if (StringUtils.isEmpty(notifyUrl)) {
            throw new NullPointerException("请填写回调地址");
        }
        //Shop shop = order.getShop();
       *//* Coupon coupon = order.getCoupon();//优惠券实体
        BigDecimal orderPrice = order.getOrderPrice();//订单价格
        BigDecimal distributionFee = order.getDistributionFee();//配送费价格
        BigDecimal realOrderPrice;
        if (coupon != null) {
            realOrderPrice = orderPrice.subtract(coupon.getDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (realOrderPrice.intValue() < 0) {
                realOrderPrice = new BigDecimal(0);
            }
            realOrderPrice = realOrderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            realOrderPrice = orderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        System.out.println("订单总金额为" + realOrderPrice);

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do"
                , APP_ID, APP_PRIVATE_KEY,
                "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("妈妈烩功夫");
        model.setSubject("妈妈烩功夫：" + order.getOrderNum());
        model.setOutTradeNo(order.getOrderNum());//订单号
        model.setTimeoutExpress("30m");
        //测试支付0.01
        // TODO apipay 测试支付基于一分钱
        //model.setTotalAmount("0.01");
        model.setTotalAmount(realOrderPrice.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);//回调地址
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);*//*
        //response.getBody()
        return null;
    }

    public static AlipayTradeRefundResponse getRefundResponse(OrderVo order) throws AlipayApiException {
        if (order == null) {
            throw new NullPointerException("找不到该订单");
        }
        //Shop shop = order.getShop();
        Coupon coupon = order.getCoupon();//优惠券实体
        BigDecimal orderPrice = order.getOrderPrice();//订单价格
        BigDecimal distributionFee = order.getDistributionFee();//配送费价格
        BigDecimal realOrderPrice;
        if (coupon != null) {
            realOrderPrice = orderPrice.subtract(coupon.getDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (realOrderPrice.intValue() < 0) {
                realOrderPrice = new BigDecimal(0);
            }
            realOrderPrice = realOrderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            realOrderPrice = orderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        //realOrderPrice = new BigDecimal("0.01");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, "json", "UTF-8",
                ALIPAY_PUBLIC_KEY, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + order.getOrderNum() + "\"," +
                "\"refund_amount\":\"" + realOrderPrice.toString() + "\"," +
                "\"refund_reason\":\"正常退款\"" +
                "}");
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        ;
        return response;
    }*/

}
