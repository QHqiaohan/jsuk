package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopAttributeGoodsDao;
import com.jh.jsuk.entity.ShopAttributeGoods;
import com.jh.jsuk.entity.vo.ShopAttributeVo;
import com.jh.jsuk.service.ShopAttributeGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 店铺分类属性关联商品表 服务实现类
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@Service
public class ShopAttributeGoodsServiceImpl extends ServiceImpl<ShopAttributeGoodsDao, ShopAttributeGoods> implements ShopAttributeGoodsService {

    @Override
    public List<ShopAttributeVo> getShopAttributeByShopId(Integer shopId) {
        return baseMapper.getShopAttributeByShopId(shopId);
    }
}
