package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.DistributionUser;

import java.math.BigDecimal;

/**
 * <p>
 * 骑手信息 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DistributionUserService extends IService<DistributionUser> {

    /**
     * 通知骑手接单
     */
    void notifyRobbing();


    void addAccount(BigDecimal amount,Integer userId);

    /**
     * 获取骑手端用户余额
     * @param distributionUserId
     * @return
     */
    BigDecimal getRemainder(Integer distributionUserId);
}
