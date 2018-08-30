package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopDao;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.entity.vo.ShopTelPhoneVo;
import com.jh.jsuk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 店铺 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopDao, Shop> implements ShopService {

    @Autowired
    UserRemainderService userRemainderService;

    @Autowired
    ShopMoneyService shopMoneyService;

    @Override
    public boolean isExist(Integer shopId) {
        return selectCount(new EntityWrapper<Shop>().eq(Shop.ID, shopId)) > 0;
    }

    /**
     * 涉及到写的操作避免幻读
     *
     * @param shopId
     * @param userId
     * @param bigDecimal
     * @throws Exception
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void doDeal(Integer shopId, Integer userId, BigDecimal bigDecimal) throws Exception {
        userRemainderService.consume(userId, bigDecimal);
        shopMoneyService.gain(shopId, bigDecimal);
    }

    @Override
    public ShopTelPhoneVo getShopById(Integer shopId) {
        return baseMapper.getShopById(shopId);
    }

    @Override
    public List<Shop> findCollectByUserId(Integer userId) {
        return baseMapper.findCollectByUserId(userId);
    }

    @Override
    public List<Shop> findShopsByUserArea(Integer cityId) {
        return baseMapper.findShopsByUserArea(cityId);
    }

    @Autowired
    UserAddressService userAddressService;

    @Autowired
    ManagerUserService managerUserService;

    @Override
    public UserAddress syncAddressInfo(Integer shopId) {
        Shop shop = selectById(shopId);
        if (shop == null)
            return null;
        if (shop.getAddressId() != null) {
            return userAddressService.selectById(shop.getAddressId());
        }
        UserAddress a = new UserAddress();
        a.setAddress(shop.getAddress());
        Integer cityId = shop.getCityId();
        a.setCity(cityId == null ? null : String.valueOf(cityId));
        a.setArea(cityId == null ? null : String.valueOf(cityId));
        Double latitude = shop.getLatitude();
        a.setLatitude(latitude == null ? null : String.valueOf(latitude));
        Double longitude = shop.getLongitude();
        a.setLongitude(longitude == null ? null : String.valueOf(longitude));
        ManagerUser managerUser = managerUserService.shopManager(shopId);
        if (managerUser != null) {
            a.setPhone(managerUser.getPhone());
            a.setName(managerUser.confirmName());
        }
        a.setIsDel(0);
        a.setCreateTime(new Date());
        a.insert();
        return a;
    }

}
