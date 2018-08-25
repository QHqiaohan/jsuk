package com.jh.jsuk.entity.vo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserAddress;
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

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/27 15:09
 */

@Setter
@Getter
@ToString
public class UserOrderInfoVo extends UserOrder {

//    private Integer num;
//
//    private BigDecimal buyPrice;
//
//    private String sizeName;
//
//    private String goodsName;
//
//    //private String goodsHeadImg;
//    private String shopName;
//
//    private String salesPrice;
//
//    private String originalPrice;
//
//    private Integer shopId;
//
//    private Integer orderType;
//
//    private String refundReason;
//
//    private Integer goodsSizeId;
//
//    private BigDecimal discount;
//
//    private Integer id;
//
//    private Integer status;
//
//    private Date creatTime;
//
//    private Integer isClosed;
//
//    private Date sendTime;

    private UserAddress addressInfo;

    private List<UserOrderDetailVo> shopGoodsList;

    private OrderServiceType serviceType;

    private OrderServiceStatus serviceStatus;

    private String statusText;

    public void updateStatus(UserOrderServiceService service) throws Exception {
        OrderStatus orderStatus = EnumUitl.toEnum(OrderStatus.class, getStatus());
        statusText = orderStatus.getValue();
        if (!OrderStatus.SERVICE.equals(orderStatus)) {
            EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
            wrapper.eq(UserOrderService.ORDER_ID, getId());
            UserOrderService userOrderService = service.selectOne(wrapper);
            if (userOrderService == null)
                return;
            serviceStatus = EnumUitl.toEnum(OrderServiceStatus.class, userOrderService.getStatus());
            serviceType = EnumUitl.toEnum(OrderServiceType.class, userOrderService.getType());
            statusText = serviceType.getValue() + serviceStatus.getValue();
        }
    }

}
