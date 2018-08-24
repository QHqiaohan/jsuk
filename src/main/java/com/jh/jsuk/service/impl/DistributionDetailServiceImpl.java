package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.DistributionDetail;
import com.jh.jsuk.dao.DistributionDetailDao;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.service.DistributionDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Service
public class DistributionDetailServiceImpl extends ServiceImpl<DistributionDetailDao, DistributionDetail> implements DistributionDetailService {

    @Override
    public void tixian(Integer userId, BigDecimal price) {
        DistributionDetail detail = new DistributionDetail();
        detail.setUserId(userId);
        detail.setDetail("提现");
        detail.setMoney(price);
        detail.setPublishTime(new Date());
        insert(detail);
    }

    @Override
    public void complete(Express express) {
        DistributionDetail detail = new DistributionDetail();
        detail.setUserId(express.getUserId());
        detail.setDetail("完成配送");
        String price = express.getPrice();
        detail.setMoney(new BigDecimal(price == null ? "0" : price));
        detail.setPublishTime(new Date());
        insert(detail);
    }
}
