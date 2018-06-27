package com.jh.jsuk.entity.comparator;

import com.jh.jsuk.entity.vo.ShopVo;
import com.jh.jsuk.entity.vo.UserOrderVo;

import java.util.Comparator;

public class DistanceComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        double v = 0;
        if (o1 instanceof UserOrderVo && o2 instanceof UserOrderVo) {
            UserOrderVo orderVo1 = (UserOrderVo) o1;
            UserOrderVo orderVo2 = (UserOrderVo) o2;
            v = orderVo1.getDistance() - orderVo2.getDistance();
        } else if (o1 instanceof ShopVo && o2 instanceof ShopVo) {
            ShopVo shopVo1 = (ShopVo) o1;
            ShopVo shopVo2 = (ShopVo) o2;
            v = shopVo1.getDistance() - shopVo2.getDistance();
        }
        if (v > 0) {
            return 1;
        } else if (v < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
