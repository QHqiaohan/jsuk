package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.*;
import com.jh.jsuk.utils.Distance;

import java.util.List;

public class UserOrderDetailVo extends UserOrder {

    private ShopPhoneVo shop;
    private DistributionUser distributionUser;
    private Coupon coupon;
    private UserAddress addressInfo;
    private List<UserOrderGoodsDetailVo> shopGoodsList;
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

    public UserAddress getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(UserAddress addressInfo) {
        this.addressInfo = addressInfo;
    }

    public List<UserOrderGoodsDetailVo> getShopGoodsList() {
        return shopGoodsList;
    }

    public void setShopGoodsList(List<UserOrderGoodsDetailVo> shopGoodsList) {
        this.shopGoodsList = shopGoodsList;
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
