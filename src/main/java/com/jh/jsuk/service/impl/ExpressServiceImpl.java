package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ExpressDao;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.vo.ExpressVo;
import com.jh.jsuk.envm.DistributionExpressStatus;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.utils.DistanceUtil;
import com.jh.jsuk.utils.EnumUitl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 快递跑腿 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ExpressServiceImpl extends ServiceImpl<ExpressDao, Express> implements ExpressService {

    @Override
    public Page getExpressListBy(Page page, Wrapper wrapper, Integer status, Integer type, Integer userId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getExpressListBy(page, wrapper, status, type, userId));
        return page;
    }

    @Override
    public Page getDeliverList(Page page, Wrapper ew, String status, Integer type, Integer userId, String lng, String lat) throws Exception {
        Integer[] sts = null;
        DistributionExpressStatus envm = null;
        if (status != null) {
            envm = EnumUitl.toEnum(DistributionExpressStatus.class, status, "getsKey");
            if (DistributionExpressStatus.WAIT_ROBBING.equals(envm)) {
                userId = null;
            }
            sts = envm.getKey();
        }
        List<ExpressVo> deliverList = baseMapper.getDeliverList(page, ew, sts, type, userId);
        if (DistributionExpressStatus.WAIT_ROBBING.equals(envm))
            DistanceUtil.calcDistance(deliverList, lng, lat);
        return page.setRecords(deliverList);
    }
}
