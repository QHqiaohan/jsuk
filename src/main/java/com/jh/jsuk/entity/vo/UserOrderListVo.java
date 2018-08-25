package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.UserOrder;
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

}
