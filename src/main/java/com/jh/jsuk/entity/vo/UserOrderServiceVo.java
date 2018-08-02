package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserOrderServiceVo extends UserOrderService {

    private UserOrder orderInfo;

    private ShopGoodsSizeVo sizeInfo;

}
