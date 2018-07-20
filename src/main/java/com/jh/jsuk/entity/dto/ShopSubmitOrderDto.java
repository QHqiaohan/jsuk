package com.jh.jsuk.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @ApiModelProperty(value = "具体商品", name = "goods",required = true)
    private ArrayList<ShopSubmitOrderGoodsDto> goods;

}
