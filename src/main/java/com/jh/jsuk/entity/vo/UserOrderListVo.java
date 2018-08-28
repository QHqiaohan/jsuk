package com.jh.jsuk.entity.vo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.envm.OrderServiceStatus;
import com.jh.jsuk.envm.OrderServiceType;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.service.UserOrderServiceService;
import com.jh.jsuk.utils.EnumUitl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Author:谢英亮
 * Date:2018/7/27 14:55
 * Description:
 */
@Setter
@Getter
@ToString
public class UserOrderListVo implements Serializable {

    private Shop shop;

    private UserOrder userOrder;

    private List<ShopGoodsVo> shopGoodsVos;

    private OrderServiceType serviceType;

    private OrderServiceStatus serviceStatus;

    private String status;

    // 用户的状态显示
    public void updateStatus(UserOrderServiceService service) throws Exception {
        OrderStatus orderStatus = EnumUitl.toEnum(OrderStatus.class, userOrder.getStatus());
        status = orderStatus.getUserText();
        if (!OrderStatus.SERVICE.equals(orderStatus)) {
            return;
        }
        EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.eq(UserOrderService.ORDER_ID, userOrder.getId());
        UserOrderService userOrderService = service.selectOne(wrapper);
        if (userOrderService == null)
            return;
        serviceStatus = EnumUitl.toEnum(OrderServiceStatus.class, userOrderService.getStatus());
        serviceType = EnumUitl.toEnum(OrderServiceType.class, userOrderService.getType());
        status = serviceType.getValue() + serviceStatus.getValue();
    }

}
