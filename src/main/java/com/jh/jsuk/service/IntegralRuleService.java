package com.jh.jsuk.service;

import com.jh.jsuk.entity.IntegralRule;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 积分抵扣规则 服务类
 * </p>
 *
 * @author tj
 * @since 2018-07-19
 */
public interface IntegralRuleService extends IService<IntegralRule> {

    boolean catGetCoupon(String shopId);

}
