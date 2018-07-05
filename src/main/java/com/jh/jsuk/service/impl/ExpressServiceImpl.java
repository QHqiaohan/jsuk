package com.jh.jsuk.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ExpressDao;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.vo.ExpressVo;
import com.jh.jsuk.entity.vo.ExpressVo2;
import com.jh.jsuk.envm.DistributionExpressStatus;
import com.jh.jsuk.envm.ExpressStatus;
import com.jh.jsuk.envm.UserType;
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

    @Override
    public Page listPage(Page page, ExpressStatus status, List<String> date, String kw) {
        String start = null, stop = null;
        if (date != null && !date.isEmpty()) {
            start = date.get(0);
            stop = date.get(1);
        }
        if (kw != null) {
            kw = "%" + kw.trim() + "%";
        }
        EntityWrapper wrapper = new EntityWrapper();
        if (StrUtil.isNotBlank(kw)) {
            wrapper.eq(Express.ID, kw);
        }
        if (StrUtil.isNotBlank(start) && StrUtil.isNotBlank(stop)) {
            wrapper.gt(Express.PUBLISH_TIME, DateTime.of(start, "yyyy-MM-dd"));
            wrapper.lt(Express.PUBLISH_TIME, DateTime.of(stop, "yyyy-MM-dd"));
        }
        if (status != null) {
            wrapper.eq(UserOrder.STATUS, status.getKey());
        }
        wrapper.ne(Express.IS_DEL,1);
        List<ExpressVo2> list = baseMapper.listPage(page, wrapper);
        return page.setRecords(list);
    }

    @Override
    public int statusCount(ExpressStatus status, Integer shopId, UserType userType, Integer userId) {
        Wrapper<Express> wrapper = new EntityWrapper<>();
        wrapper.eq(Express.STATUS,status.getKey())
                .ne(Express.IS_DEL,1);
        return selectCount(wrapper);
    }

    @Override
    public ExpressVo2 detail(Integer expressId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq(Express.ID,expressId);
        wrapper.last("limit 1");
        return baseMapper.detail(wrapper);
    }
}
