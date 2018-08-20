package com.jh.jsuk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.RunningFeeDao;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.RunningFee;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.service.RunningFeeService;
import com.jh.jsuk.service.UserAddressService;
import com.jh.jsuk.utils.GetDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:xyl
 * Date:2018/8/9 11:47
 * Description:
 */
@Service
public class RunningFeeServiceImpl extends ServiceImpl<RunningFeeDao, RunningFee> implements RunningFeeService {

    @Autowired
    UserAddressService userAddressService;

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

    @Override
    public double caleRunningFee(String start, String end) {
        return caleRunningFee(GetDistance.getDistance(start, end));
    }

    @Override
    public void caleRunningFee(Express express) {
        UserAddress sendAddress = userAddressService.selectById(express.getSenderAddress());
        UserAddress getAddress = userAddressService.selectById(express.getGetAddress());
        if (sendAddress == null || getAddress == null)
            return;
        String startLng = sendAddress.getLongitude();
        String startLat = sendAddress.getLatitude();
        String endLng = getAddress.getLongitude();
        String endLat = getAddress.getLatitude();
        if (StrUtil.isBlank(startLng) ||
            StrUtil.isBlank(startLat) ||
            StrUtil.isBlank(endLng) ||
            StrUtil.isBlank(endLat))
            return;
        express.setPrice(String.valueOf(caleRunningFee(startLng + "," + startLat, endLng + "," + endLat)));
    }
}
