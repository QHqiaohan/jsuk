package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.UserOrder;
import lombok.Data;

import java.util.List;

/**
 * Author:谢英亮
 * Date:2018/7/27 14:55
 * Description:
 */
@Data
public class UserOrderListVo {
    private Shop shop;
    private UserOrder userOrder;
    private List<ShopGoodsVo> shopGoodsVos;
}
