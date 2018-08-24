package com.jh.jsuk.service;

import com.jh.jsuk.entity.DistributionDetail;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Express;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
public interface DistributionDetailService extends IService<DistributionDetail> {


    void tixian(Integer userId, BigDecimal price);

    void complete(Express express);
}
