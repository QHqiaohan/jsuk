package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.utils.Distance;

public class ShopVo extends Shop {
    private Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double lon, Double lat) {
        if (lon != null && lat != null) {
            this.distance = Distance.getDistance(lat, lon, this.getLatitude(), this.getLongitude());
        } else {
            this.distance = 0.0;
        }
    }
}
