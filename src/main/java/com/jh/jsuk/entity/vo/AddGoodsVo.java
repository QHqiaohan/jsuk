package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;

import java.io.Serializable;
import java.util.List;

public class AddGoodsVo extends ShopGoods implements Serializable {

    private List<ShopGoodsSize> shopGoodsSizeList;

    public List<ShopGoodsSize> getShopGoodsSizeList() {
        return shopGoodsSizeList;
    }

    public void setShopGoodsSizeList(List<ShopGoodsSize> shopGoodsSizeList) {
        this.shopGoodsSizeList = shopGoodsSizeList;
    }

}

/**
 * shopGoodsSizeList[
 * {
 * 规格名称sizeName: ,
 * 库存stock:  ,
 * 销售价格salesPrice: ,
 * 重量weight: ,
 * 体积volume: ,
 * 运费 0=包邮freight: ,
 * 满多少包邮fullFreight: ,
 * 是否满减,1=是,0=否type: ,
 * 图片img: ,
 * 商品品牌brand: ,
 * 赠送积分sendJf: ,
 * 抵扣积分deductibleJf: ,killStock
 * 商品类别goodsTypeId: ,
 * 秒杀价killPrice : ,
 * 秒杀库存killStock: ,
 * 商品详情detailInfo : ,
 * }
 * ]
 */
