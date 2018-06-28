package com.jh.jsuk.utils;

import cn.hutool.core.util.StrUtil;
import com.jh.jsuk.entity.vo.ExpressVo;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 高德测距工具
 * 1.这个需要客户申请个高德API上面的key,也可以自己申请(免费的，很快)
 */
@Slf4j
public class DistanceUtil {

    /**
     * 计算距离
     *
     * @param data
     * @param lng
     * @param lat
     */
    public static void calcDistance(List<ExpressVo> data, String lng, String lat) {
        if (StrUtil.isBlank(lng) || StrUtil.isBlank(lat)) {
            return;
        }
        String locStr = lng + "," + lat;
        List<ExpressVo> list = Collections.synchronizedList(data);
        if (list == null || list.isEmpty()) {
            return;
        }
        int count = 0;
        for (ExpressVo vo : list) {
            if (vo == null) {
                continue;
            }
            if (vo.canCalcGetDistance()) {
                count++;
            }
            if (vo.canCalcSenderDistance()) {
                count++;
            }
        }
        if (count == 0) {
            return;
        }
        CountDownLatch latch = new CountDownLatch(count);
        for (ExpressVo vo : list) {
            if (vo == null) {
                continue;
            }
            if (vo.canCalcSenderDistance()) {
                new Thread(() -> {
                    vo.setSenderDistance((double) GetDistance.getDistance(locStr, vo.getSenderAddressInfo().toCalcLocation()));
                    latch.countDown();
                }).start();
            }
            if (vo.canCalcGetDistance()) {
                new Thread(() -> {
                    vo.setGetDistance((double) GetDistance.getDistance(locStr, vo.getGetAddressInfo().toCalcLocation()));
                    latch.countDown();
                }).start();
            }
        }
        try {
            latch.await();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}   
