package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.GoodsEvaluate;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsEvaluateVo extends GoodsEvaluate {

    private User userInfo;

    private ShopGoods goodsInfo;

}
