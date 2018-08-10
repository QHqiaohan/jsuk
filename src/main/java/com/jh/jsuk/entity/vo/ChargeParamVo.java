package com.jh.jsuk.entity.vo;

import com.jh.jsuk.envm.PayType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author:xyl
 * Date:2018/8/10 10:18
 * Description: ping++ 用于创建charge对象
 */
@Data
public class ChargeParamVo implements Serializable {

    private BigDecimal amount;//订单总金额

    private String subject; //商品标题

    private String body;//商品描述信息

    private Integer payType;//支付类型

    private String orderNo;//订单编号

    private String clientIP;//发起支付请求客户端的 IP 地址

    private String openId;//微信公众号支付时用户openId

}
