package com.jh.jsuk.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopSubmitOrderGoodsDto implements Serializable {

    @ApiModelProperty(value = "商品规格id", name = "goodsSizeId",required = true)
    @NotNull
    private Integer goodsSizeId;

    @ApiModelProperty(value = "商品id", name = "goodsId",required = true)
    @NotNull
    private Integer goodsId;

    @ApiModelProperty(value = "商品价格", name = "goodsPrice",required = true)
    @NotNull
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "数量", name = "num",required = true)
    @NotNull
    @Min(1)
    private Integer num;

    @ApiModelProperty(value = "购物车id", name = "cartId",notes = "完成后清空购物车")
    private Integer cartId;

}
