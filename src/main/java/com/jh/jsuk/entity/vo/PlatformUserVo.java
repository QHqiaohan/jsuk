package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.envm.UserAuthenticationStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 后台管理系统-用户管理-用户列表
 */
@Setter
@Getter
@ToString
public class PlatformUserVo implements Serializable {
    /**
     * 用户
     */
    private User user;
    /**
     * 消费金额
     */
    private BigDecimal consumeCount;
    /**
     * 订单数量
     */
    private Integer orderCount;
    /**
     * 可用积分
     */
    private Integer integralCount;

    private UserAuthenticationStatus authStatus;

    /**
     * 地址
     */
    private List<UserAddress> userAddressList;
    /**
     * 订单列表
     */
    private List<UserOrder> userOrderList;

}
