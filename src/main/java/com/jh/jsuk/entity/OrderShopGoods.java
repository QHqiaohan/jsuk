package com.jh.jsuk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户提交购物车计数价格
 * Author:xyl
 * Date:2018/8/27 16:27
 * Description:
 */
@Setter
@Getter
@ToString
public class OrderShopGoods {
    private Integer goodId;//商品id
    private  Integer goodSum;//商品数量
    private String goodImg;//商品小图；
    private Integer goodSizeId;//商品规格id，
    private String goodSizeName;//商品规格名称；
    private String salesPrice;//商品价格
    private String goodName;//s商品名称

}
