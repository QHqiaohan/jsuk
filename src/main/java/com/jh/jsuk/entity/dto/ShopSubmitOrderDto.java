package com.jh.jsuk.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ShopSubmitOrderDto implements Serializable {

    @ApiModelProperty(value = "商品规格id", name = "goodsSizeId",required = true)
    @NotNull
    private Integer goodsSizeId;
    
    @ApiModelProperty(value = "店铺id", name = "shopId",required = true)
    @NotNull
    private Integer shopId;

    @ApiModelProperty(value = "数量", name = "num",required = true)
    @NotNull
    @Min(1)
    private Integer num;

    @ApiModelProperty(value = "优惠券id", name = "userCouponId")
    private Integer userCouponId;

    @ApiModelProperty(value = "购物车id", name = "cartId",notes = "完成后清空购物车")
    private Integer cartId;

    @ApiModelProperty(value = "优惠规则id", name = "integralRuleId")
    private Integer integralRuleId;

    @ApiModelProperty(value = "满减规则id", name = "fullReduceId")
    private Integer fullReduceId;

}
