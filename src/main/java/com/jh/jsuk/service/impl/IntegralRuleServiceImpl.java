package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.IntegralRule;
import com.jh.jsuk.dao.IntegralRuleDao;
import com.jh.jsuk.service.IntegralRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分抵扣规则 服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-07-19
 */
@Service
public class IntegralRuleServiceImpl extends ServiceImpl<IntegralRuleDao, IntegralRule> implements IntegralRuleService {

    @Override
    public boolean catGetCoupon(String shopId) {
        if (shopId == null)
            return false;
        EntityWrapper<IntegralRule> wrapper = new EntityWrapper<>();
//        wrapper.eq(IntegralRule.SHOP_ID, shopId);
        return selectCount(wrapper) > 0;
    }
}
