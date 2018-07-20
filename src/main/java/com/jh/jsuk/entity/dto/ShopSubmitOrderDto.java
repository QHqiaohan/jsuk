package com.jh.jsuk.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ShopSubmitOrderDto implements Serializable {

    @ApiModelProperty(value = "店铺id", name = "shopId",required = true)
    @NotNull
    private Integer shopId;

    @ApiModelProperty(value = "优惠券id", name = "userCouponId")
    private Integer userCouponId;

    @ApiModelProperty(value = "积分抵扣规则id", name = "integralRuleId")
    private Integer integralRuleId;

    @ApiModelProperty(value = "满减规则id", name = "fullReduceId")
    private Integer fullReduceId;

    @NotNull
    @Valid
    private ArrayList<ShopSubmitOrderGoodsDto> goods;

}
