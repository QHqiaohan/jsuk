package com.jh.jsuk.service;

import com.jh.jsuk.entity.DistributionUser;
import com.baomidou.mybatisplus.service.IService;

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
}
