package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/13 12:45
 */
@Setter
@Getter
@ToString
public class GoodsSizeVo extends ShopGoods {

    private String shopName;

    private List<ShopGoodsSize> shopGoodsSize;

    private RushBuyVo rushBuyInfo;

    private GoodsCategory categoryInfo;

    private Integer userType;

    private String phone;

    public void addRushBuyInfo(RushBuyVo vo) {
        if (vo == null)
            return;
        vo.checkStatus();
        rushBuyInfo = vo;
    }

}
