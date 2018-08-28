package com.jh.jsuk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 购物车提交计算价格商家列表；
 * Author:xyl
 * Date:2018/8/27 16:42
 * Description:
 */
@Setter
@Getter
@ToString
public class OrderShops {
    private Integer shopId;//店铺id
    private String shopName;//店铺名称
    private BigDecimal fullReduce;//满减了多少
    private BigDecimal freight;//运费
    private Integer ShopGoodSum;//店铺商品数量
    private BigDecimal ShopPrice;//店铺价格
    private ArrayList<OrderShopGoods> shopGoods;
    private BigDecimal full;//满了多少；
}
