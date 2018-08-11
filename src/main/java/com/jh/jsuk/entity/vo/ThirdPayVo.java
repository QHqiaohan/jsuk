package com.jh.jsuk.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/11 11:03
 * Description:
 */
@Data
public class ThirdPayVo implements Serializable {

    @ApiModelProperty(name = "1.用户订单支付 2.到店支付 3.会员充值 4.寄快递 5.跑腿", value = "type")
    private Integer type;

    @ApiModelProperty(name = "其他参数：到店支付-param=商家id 会员充值-param=会员等级Id 其他类型都传订单id,多个订单逗号隔开", value = "param")
    private String param;

    @ApiModelProperty(name = "价格 只有到店支付需要传价格")
    private String price;

    @ApiModelProperty(name = "支付类型：2支付宝-3微信公众号-4微信APP-5银行卡")
    private Integer payType;

    @ApiModelProperty(name = "用户id", value = "userId")
    private Integer userId;

}
