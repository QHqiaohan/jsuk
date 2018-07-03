package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.GoodsEvaluate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsEvaluateVo extends GoodsEvaluate {

    private UserVo userInfo;

    private ShopGoodsVo goodsInfo;

}
