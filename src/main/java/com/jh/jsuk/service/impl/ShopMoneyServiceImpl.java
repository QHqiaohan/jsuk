package com.jh.jsuk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopMoneyDao;
import com.jh.jsuk.entity.ShopMoney;
import com.jh.jsuk.envm.ShopMoneyType;
import com.jh.jsuk.envm.UserRemainderStatus;
import com.jh.jsuk.service.ShopMoneyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商家端-余额 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopMoneyServiceImpl extends ServiceImpl<ShopMoneyDao, ShopMoney> implements ShopMoneyService {

    @Override
    public BigDecimal getShopMoney(Integer shopId) {
        /**
         * 店铺余额
         */
        List<ShopMoney> list = selectList(new EntityWrapper<ShopMoney>()
            .eq(ShopMoney.STATUS, UserRemainderStatus.PASSED.getKey())
            .eq(ShopMoney.SHOP_ID, shopId));
        BigDecimal bigDecimal = new BigDecimal("0.00");
        if (CollectionUtils.isEmpty(list)) {
            return bigDecimal;
        } else {
            // 初始化余额
            for (ShopMoney shopMoney : list) {
                // 金额
                String shopMoneyMoney = shopMoney.getMoney();
                ShopMoneyType type = shopMoney.getType();
                if (StrUtil.isBlank(shopMoneyMoney) || type == null)
                    continue;
                BigDecimal money = new BigDecimal(shopMoneyMoney);
                switch (type) {
                    case GAIN:
                        bigDecimal = bigDecimal.add(money);
                        break;
                    case REFUND:
                    case CASH:
                        bigDecimal = bigDecimal.subtract(money);
                        break;
                }
            }
            return bigDecimal;
        }
    }

    @Override
    public BigDecimal getApplying(Integer shopId) {
        List<ShopMoney> list = selectList(new EntityWrapper<ShopMoney>()
            .eq(ShopMoney.STATUS, UserRemainderStatus.APPLYING.getKey())
            .eq(ShopMoney.SHOP_ID, shopId));
        BigDecimal bigDecimal = new BigDecimal("0.00");
        if (CollectionUtils.isEmpty(list)) {
            return bigDecimal;
        } else {
            for (ShopMoney shopMoney : list) {
                String shopMoneyMoney = shopMoney.getMoney();
                if (StrUtil.isBlank(shopMoneyMoney))
                    continue;
                BigDecimal money = new BigDecimal(shopMoneyMoney);
                bigDecimal = bigDecimal.add(money);
                break;
            }
            return bigDecimal;
        }
    }

    @Override
    public void createCashApplying(Integer shopId, BigDecimal amount, String tiXianNo, Integer bankId) throws Exception {
        ShopMoney entity = new ShopMoney();
        entity.setShopId(shopId);
        entity.setPublishTime(new Date());
        entity.setType(ShopMoneyType.CASH);
        entity.setStatus(UserRemainderStatus.APPLYING);
        entity.setMoney(amount.toString());
        entity.setPlatformNo(tiXianNo);
        insert(entity);
    }


    @Override
    public void confirm(String platformNo) {
        ShopMoney entity = new ShopMoney();
        EntityWrapper<ShopMoney> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopMoney.PLATFORM_NO, platformNo);
        entity.setStatus(UserRemainderStatus.PASSED);
        update(entity, wrapper);
    }

    @Override
    public void decline(String platformNo) {
        ShopMoney entity = new ShopMoney();
        EntityWrapper<ShopMoney> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopMoney.PLATFORM_NO, platformNo);
        entity.setStatus(UserRemainderStatus.REFUSED);
        update(entity, wrapper);
    }

    @Override
    public void gain(Integer shopId, BigDecimal amount) throws Exception {
        ShopMoney entity = new ShopMoney();
        entity.setShopId(shopId);
        entity.setMoney(amount.toString());
        entity.setPublishTime(new Date());
        entity.setStatus(UserRemainderStatus.PASSED);
        entity.setType(ShopMoneyType.GAIN);
        insert(entity);
    }

    @Override
    public boolean refund(Integer shopId, BigDecimal amount) throws Exception {
        ShopMoney entity = new ShopMoney();
        entity.setShopId(shopId);
        entity.setMoney(amount.toString());
        entity.setPublishTime(new Date());
        entity.setStatus(UserRemainderStatus.PASSED);
        entity.setType(ShopMoneyType.REFUND);
        insert(entity);
        return true;
    }

}
