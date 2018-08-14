package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopGoodsDao;
import com.jh.jsuk.dao.ShopRushBuyActivityDao;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.vo.*;
import com.jh.jsuk.envm.ShopGoodsStatus;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ShopRushBuyActivityDao shopRushBuyActivityDao;

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
    public Page shopGoodsListByAttributeId(Page page, Wrapper wrapper, Integer attributeId, Integer shopId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.shopGoodsListByAttributeId(page, wrapper, attributeId, shopId));
        return page;
    }

    @Override
    public Page getShopGoodsBy(Page page, Wrapper wrapper, Integer type, Integer shopId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsBy(page, wrapper, type, shopId));
        return page;
    }

    @Override
    public Page getShopGoodsOrderBySalesPrice(Page page, Wrapper wrapper, Integer type, Integer shopId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsOrderBySalesPrice(page, wrapper, type, shopId));
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

    @Autowired
    ShopService shopService;

    @Override
    public GoodsSizeVo getShopGoodsById(Integer id) {
        GoodsSizeVo vo = baseMapper.getShopGoodsById(id);
        if (vo != null) {
            Shop shop = shopService.selectById(vo.getShopId());
            if (shop != null)
                vo.setShopName(shop.getShopName());
        }
        if (vo != null) {
            vo.addRushBuyInfo(shopRushBuyActivityDao.findVoByGoodsId(vo.getId()));
        }
        return vo;
    }

    @Override
    public Page getShopGoodsByLikeName(Page page, Wrapper wrapper, Integer type, String name, Integer attributeId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopGoodsByLikeName(page, wrapper, type, name, attributeId));
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

    @Override
    public Page getIsRecommend(Page page, Wrapper wrapper) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getIsRecommend(page, wrapper));
        return page;
    }

    /**
     * 返回库存
     *
     * @param goodsId
     */
    @Override
    public void returnStock(Integer goodsId, Integer num) {

        ShopGoods shopGoods = this.selectById(goodsId);
        //shopGoods.setStock(num + shopGoods.getStock());
        this.updateById(shopGoods);
    }

    @Override
    public Page findShopGoodsAndGoodsSizeByShopId(Page page, Wrapper wrapper, Integer shopId,String goodsName) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.findShopGoodsAndGoodsSizeByShopId(page, wrapper, shopId,goodsName));
        return page;
    }

    @Override
    public Page list(Page page, ShopGoodsStatus status, String categoryId, String keyWord, String brandId, Integer shopId, Integer userType) {
        if (keyWord != null)
            keyWord = "%" + keyWord.trim() + "%";
        List<GoodsSizeVo> list = baseMapper.shopGoodsList(page, status != null ? status.getKey() : null, categoryId, keyWord, brandId, shopId);
        for (GoodsSizeVo vo : list) {
            vo.setUserType(userType);
        }
        return page.setRecords(list);
    }

    @Override
    public Page listRecycle(Page page, String categoryId, String keyWord, String brandId, Integer shopId) {
        if (keyWord != null)
            keyWord = "%" + keyWord.trim() + "%";
        List<ShopGoodsVo2> list = baseMapper.shopGoodsRecycleList(page, categoryId, keyWord, brandId, shopId);
        return page.setRecords(list);
    }

    @Override
    public List<GoodsSizeVo> getShopGoodsByKeywords(String keywords) {
        return baseMapper.getShopGoodsByKeywords(keywords);
    }

    @Override
    public List<GoodsVo2> guessYourLike() {
        return baseMapper.guessYourLike();
    }


    @Override
    public Page getShopList(Page page, Wrapper wrapper, Integer type, Integer attributeId, String name, Integer shopModularId, Integer categoryId,
                            Integer brandId, String address, Integer goodsType, String lowPrice, String highPrice, Integer shopId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopList(page, wrapper, address, attributeId, shopModularId, categoryId, brandId, name, goodsType,
            lowPrice, highPrice, type, shopId));
        return page;
    }

    @Override
    public List<GoodsSalesPriceVo2> selectLevel2Goods(Integer categoryId) {
        return baseMapper.selectLevel2Goods(categoryId);
    }

}
