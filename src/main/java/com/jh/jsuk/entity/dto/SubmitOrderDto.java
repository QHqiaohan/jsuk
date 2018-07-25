package com.jh.jsuk.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderDto implements Serializable {

    @ApiModelProperty(value = "地址id", name = "addressId", required = true)
    @NotNull
    private Integer addressId;

    @ApiModelProperty(value = "支付方式", name = "payType", required = true)
    @NotNull
    private Integer payType;

    @ApiModelProperty(value = "订单类型", name = "orderType", required = true)
    @NotNull
    private Integer orderType;

    @ApiModelProperty(value = "配送方式", name = "distributionType", required = true)
    @NotNull
    private Integer distributionType;

    @ApiModelProperty(value = "是否使用积分 0不使用 1使用", name = "isUseIntegral", required = true)
    @NotNull
    private Integer isUseIntegral;

    @ApiModelProperty(value = "配送时间", name = "distributionTime")
    private Date distributionTime;

    @Valid
    @NotNull
    @ApiModelProperty(value = "订单具体详情", name = "shops", required = true)
    private ArrayList<ShopSubmitOrderDto> shops;


}
