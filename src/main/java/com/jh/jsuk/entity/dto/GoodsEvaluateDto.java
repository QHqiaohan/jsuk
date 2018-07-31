package com.jh.jsuk.entity.dto;

import com.jh.jsuk.entity.GoodsEvaluate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GoodsEvaluateDto extends GoodsEvaluate {

    @ApiModelProperty(name = "商品质量星数", value = "goodsStar")
    private Integer goodsStar;

    @ApiModelProperty(name = "送货员星数", value = "sendStar")
    private Integer sendStar;

    @ApiModelProperty(name = "客服服务星数", value = "serviceStar")
    private Integer serviceStar;

}
