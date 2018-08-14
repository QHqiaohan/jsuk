package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.*;
import com.jh.jsuk.utils.Distance;

import java.util.List;

public class UserOrderDetailVo extends UserOrder {

    private ShopPhoneVo shop;
    private DistributionUser distributionUser;
    private Coupon coupon;
    private UserAddress address;
    private List<UserOrderGoodsDetailVo> goodsList;
    private Double distance;


    private ShopGoods shopGoods;

    public ShopGoods getShopGoods() {

        return shopGoods;
    }

    public void setShopGoods(ShopGoods shopGoods) {
        this.shopGoods = shopGoods;
    }

    public ShopPhoneVo getShop() {
        return shop;
    }

    public void setShop(ShopPhoneVo shop) {
        this.shop = shop;
    }

    public DistributionUser getDistributionUser() {
        return distributionUser;
    }

    public void setDistributionUser(DistributionUser distributionUser) {
        this.distributionUser = distributionUser;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public List<UserOrderGoodsDetailVo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<UserOrderGoodsDetailVo> goodsList) {
        this.goodsList = goodsList;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double lon, Double lat) {
        if(shop == null){
            distance = null;
            return;
        }
        this.distance = Distance.getDistance(lat, lon, shop.getLatitude(), shop.getLongitude());
    }
}
