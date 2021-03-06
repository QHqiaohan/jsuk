package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopAttributeGoods;
import com.jh.jsuk.entity.vo.ShopAttributeVo;

import java.util.List;

/**
 * <p>
 * 店铺分类属性关联商品表 服务类
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
public interface ShopAttributeGoodsService extends IService<ShopAttributeGoods> {

    List<ShopAttributeVo> getShopAttributeByShopId(Integer shopId);
}
