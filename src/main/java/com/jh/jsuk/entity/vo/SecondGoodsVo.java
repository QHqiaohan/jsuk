package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Data;

import java.util.List;

/**
 * Author:xyl
 * Date:2018/8/7 17:41
 * Description:后台 - 二手市场
 */
@Data
public class SecondGoodsVo {

    private Integer id;

    private String mainImg;

    private String name;

    private List<ShopGoodsSize> shopGoodsSize;

    private Integer status;

    private GoodsCategory categoryInfo;

}
