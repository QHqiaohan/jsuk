package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShopGoodsSizeVo extends ShopGoodsSize {

    private ShopGoods goodsInfo;

}
