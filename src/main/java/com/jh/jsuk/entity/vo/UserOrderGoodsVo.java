package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.UserOrderGoods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserOrderGoodsVo extends UserOrderGoods {

    private ShopGoods shopGoods;

}
