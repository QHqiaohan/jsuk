package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.UserOrderGoods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserOrderGoodsDetailVo extends UserOrderGoods {

    private ShopGoods goodsInfo;

    private ShopGoodsSize sizeInfo;

}
