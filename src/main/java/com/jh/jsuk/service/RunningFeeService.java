package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.RunningFee;

/**
 * Author:xyl
 * Date:2018/8/9 11:46
 * Description:
 */
public interface RunningFeeService extends IService<RunningFee> {

    /**
     * 计算奋勇
     * @param dintance
     * @return
     */
    double caleRunningFee(long dintance);

}
