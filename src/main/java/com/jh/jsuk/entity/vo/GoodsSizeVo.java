package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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

    private List<RushBuyVo> rushBuyInfo;

    private GoodsCategory categoryInfo;

    public void addRushBuyInfo(RushBuyVo vo) {
        if (rushBuyInfo == null)
            rushBuyInfo = new ArrayList<>();
        rushBuyInfo.add(vo);
    }

//    private List<ShopRushBuySizeVo> rushBuyInfo;
//
//    public void addRushBuyInfo(ShopRushBuySizeVo shopRushBuySizeVo){
//        if (rushBuyInfo == null) {
//            rushBuyInfo = new ArrayList<>();
//        }
//        rushBuyInfo.add(shopRushBuySizeVo);
//    }
//    public List<ShopGoodsSize> getShopGoodsSize() {
//        return shopGoodsSize;
//   }
//
//    public void setShopGoodsSize(List<ShopGoodsSize> shopGoodsSize) {
//       this.shopGoodsSize = shopGoodsSize;
//   }
}
