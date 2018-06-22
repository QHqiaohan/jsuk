package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopGoodsDao;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
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
    public List<GoodsSalesPriceVo> findShopGoodsByModularId(Integer modularId) {
        return baseMapper.findShopGoodsByModularId(modularId);
    }

    @Override
    public Page shopGoodsListByModularId(Page page, Wrapper wrapper, Integer modularId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.shopGoodsListByModularId(page, wrapper, modularId));
        return page;
    }

    @Override
    public Page shopGoodsListByAttributeId(Page page, Wrapper wrapper, Integer attributeId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.shopGoodsListByAttributeId(page, wrapper, attributeId));
        return page;
    }

    @Override
    public Page getShopGoodsBy(Page page, Wrapper wrapper, Integer type) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsBy(page, wrapper, type));
        return page;
    }

    @Override
    public Page getShopGoodsOrderBySalesPrice(Page page, Wrapper wrapper, Integer type) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsOrderBySalesPrice(page, wrapper, type));
        return page;
    }

    @Override
    public Page getShopGoodsByCategoryId(Page page, Wrapper wrapper, Integer categoryId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsByCategoryId(page, wrapper, categoryId));
        return page;
    }

    @Override
    public Page getShopGoodsByServiceOrPrice(Page page, Wrapper wrapper, Integer goodsType, String lowPrice, String highPrice, Integer categoryId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsByServiceOrPrice(page, wrapper, goodsType, lowPrice, highPrice, categoryId));
        return page;
    }

    @Override
    public GoodsSizeVo getShopGoodsById(Integer id) {
        return baseMapper.getShopGoodsById(id);
    }

    @Override
    public Page getShopGoodsByLikeName(Page page, Wrapper wrapper, Integer type, String name) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsByLikeName(page, wrapper, type, name));
        return page;
    }

    @Override
    public Page getShopGoodsOnCategoryBy(Page page, Wrapper wrapper, Integer type, Integer categoryId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsOnCategoryBy(page, wrapper, type, categoryId));
        return page;
    }

    @Override
    public Page getShopGoodsByBrandId(Page page, Wrapper wrapper, Integer brandId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsByBrandId(page, wrapper, brandId));
        return page;
    }
}
