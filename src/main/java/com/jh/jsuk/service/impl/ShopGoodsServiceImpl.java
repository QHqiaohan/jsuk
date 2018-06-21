package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopGoodsDao;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.ShopGoodsSizeVo;
import com.jh.jsuk.service.ShopGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopGoodsServiceImpl extends ServiceImpl<ShopGoodsDao, ShopGoods> implements ShopGoodsService {

    @Override
    public List<ShopGoodsSizeVo> findShopGoodsByModularId(Integer modularId) {
        return baseMapper.findShopGoodsByModularId(modularId);
    }

    @Override
    public Page shopGoodsListByModularId(Page page, Wrapper wrapper, Integer modularId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.shopGoodsListByModularId(page, wrapper, modularId));
        return page;
    }
}
