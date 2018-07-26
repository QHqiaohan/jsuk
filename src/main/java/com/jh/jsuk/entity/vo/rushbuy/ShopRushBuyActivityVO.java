package com.jh.jsuk.entity.vo.rushbuy;

import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.entity.vo.RushBuyVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShopRushBuyActivityVO extends ShopRushBuyActivity {

    private RushBuyVo time;

    private SGoodsVo good;

    private SShopVo shop;

}
