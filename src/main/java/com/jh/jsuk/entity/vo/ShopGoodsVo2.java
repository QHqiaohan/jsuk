package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.entity.ShopGoods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopGoodsVo2 extends ShopGoods {

    private GoodsBrand brandInfo;

    private GoodsCategory categoryInfo;

}
