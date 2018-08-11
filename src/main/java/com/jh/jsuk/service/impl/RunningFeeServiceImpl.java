package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.RunningFeeDao;
import com.jh.jsuk.entity.RunningFee;
import com.jh.jsuk.service.RunningFeeService;
import org.springframework.stereotype.Service;

/**
 * Author:xyl
 * Date:2018/8/9 11:47
 * Description:
 */
@Service
public class RunningFeeServiceImpl extends ServiceImpl<RunningFeeDao, RunningFee> implements RunningFeeService {


    @Override
    public double caleRunningFee(long dintance) {
        RunningFee runningFee = selectById(1);
        if (runningFee == null) {
            runningFee = new RunningFee();
        }
        runningFee.defaultVal();
        dintance = dintance / 1000;
        int startFee = runningFee.getStartFee();
        int startDistance = runningFee.getStartDistance();
        if (dintance < startDistance) {
            return startFee;
        }
        double price = startFee + (double) (dintance - startDistance) / (double) runningFee.getAddFee();
        return (double) Math.round(price * 100) / 100;
    }
}
