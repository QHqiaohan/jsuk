package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopMoneyDao;
import com.jh.jsuk.entity.ShopMoney;
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
            .eq(ShopMoney.SHOP_ID, shopId));
        BigDecimal bigDecimal = new BigDecimal("0.00");
        if (CollectionUtils.isEmpty(list)) {
            return bigDecimal;
        } else {
            // 初始化余额
            for (ShopMoney shopMoney : list) {
                // 金额
                String money = shopMoney.getMoney();
                // 消费类型,计算
                Integer type = shopMoney.getType();
                if (type == 0) {
                    // 消费
                    bigDecimal = bigDecimal.subtract(new BigDecimal(money));
                } else if (type == 1) {
                    // 收入
                    bigDecimal = bigDecimal.add(new BigDecimal(money));
                }
            }
            return bigDecimal;
        }
    }

    @Override
    public void consume(Integer shopId, BigDecimal amount) throws Exception {
        ShopMoney entity = new ShopMoney();
        entity.setShopId(shopId);
        entity.setPublishTime(new Date());
        entity.setType(0);
        entity.setMoney(amount.toString());
        insert(entity);
    }

    @Override
    public void makeIncome(Integer shopId, BigDecimal amount) throws Exception {
        ShopMoney entity = new ShopMoney();
        entity.setShopId(shopId);
        entity.setMoney(amount.toString());
        entity.setPublishTime(new Date());
        entity.setType(1);
        insert(entity);
    }

}
