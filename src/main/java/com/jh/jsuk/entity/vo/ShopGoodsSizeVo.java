package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Data;

@Data
public class ShopGoodsSizeVo extends ShopGoodsSize {

    private ShopGoods goodsInfo;

}
