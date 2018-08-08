package com.jh.jsuk.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/8 15:55
 * Description:猜你喜欢
 */
@Data
public class GoodsVo2 implements Serializable {

    private Integer id;

    private String image;

    private String goodsName;

    private String price;

    private Integer saleAmount;

}
