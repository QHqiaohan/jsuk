package com.jh.jsuk.entity.vo;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.envm.OrderServiceStatus;
import com.jh.jsuk.envm.OrderServiceType;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.service.UserOrderServiceService;
import com.jh.jsuk.utils.Distance;
import com.jh.jsuk.utils.EnumUitl;

import java.util.List;

public class UserOrderDetailVo extends UserOrder {

    private ShopPhoneVo shop;
    private DistributionUser distributionUser;
    private Coupon coupon;
    private UserAddress address;
    private List<UserOrderGoodsDetailVo> goodsList;
    private Double distance;
    private String statusText;

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

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void updateStatus(UserOrderServiceService service) throws Exception {
        OrderStatus orderStatus = EnumUitl.toEnum(OrderStatus.class, getStatus());
        statusText = orderStatus.getUserText();
        if (!OrderStatus.SERVICE.equals(orderStatus)) {
            return;
        }
        EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.eq(UserOrderService.ORDER_ID, getId());
        UserOrderService userOrderService = service.selectOne(wrapper);
        if (userOrderService == null)
            return;
        OrderServiceStatus serviceStatus = EnumUitl.toEnum(OrderServiceStatus.class, userOrderService.getStatus());
        OrderServiceType serviceType = EnumUitl.toEnum(OrderServiceType.class, userOrderService.getType());
        statusText = serviceType.getValue() + serviceStatus.getValue();
    }
}
