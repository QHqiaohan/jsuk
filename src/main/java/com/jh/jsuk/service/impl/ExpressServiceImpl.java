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
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.entity.vo.ExpressVo;
import com.jh.jsuk.entity.vo.ExpressVo2;
import com.jh.jsuk.envm.*;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.utils.DistanceUtil;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.OrderNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    private UserRemainderService userRemainderService;

    @Override
    public Page getExpressListBy(Page page, Wrapper wrapper, Integer status, Integer type, Integer userId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getExpressListBy(page, wrapper, status, type, userId));
        return page;
    }

    @Override
    public Page getDeliverList(Page page, Wrapper ew, String status, Integer type, Integer userId, String lng, String lat, Integer cityId) throws Exception {
        Integer[] sts = null;
        DistributionExpressStatus envm = null;
        if (status != null) {
            envm = EnumUitl.toEnum(DistributionExpressStatus.class, status, "getsKey");
            if (DistributionExpressStatus.WAIT_ROBBING.equals(envm)) {
                userId = null;
            }
            sts = envm.getKey();
        }
        List<ExpressVo> deliverList = baseMapper.getDeliverList(page, ew, sts, type, userId,cityId);
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
        wrapper.ne(Express.IS_DEL, 1);
        List<ExpressVo2> list = baseMapper.listPage(page, wrapper);
        return page.setRecords(list);
    }

    @Override
    public int statusCount(ExpressStatus status, Integer shopId, UserType userType, Integer userId) {
        Wrapper<Express> wrapper = new EntityWrapper<>();
        wrapper.eq(Express.STATUS, status.getKey())
            .ne(Express.IS_DEL, 1);
        return selectCount(wrapper);
    }

    @Override
    public Page listOrderByDistributionId(Page page, Integer distributionId) {
        return page.setRecords(baseMapper.listOrderByDistributionId(page, distributionId));
    }

    @Override
    public ExpressVo2 detail(Integer expressId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq(Express.ID, expressId);
        wrapper.last("limit 1");
        return baseMapper.detail(wrapper);
    }

    @Override
    public boolean deliverRobbingOrder(Integer userId, Integer expressId) {
        return baseMapper.deliverRobbingOrder(userId, expressId, ExpressStatus.PAYED.getKey(), ExpressStatus.WAIT_DELIVER.getKey()) > 0;
    }

    @Autowired
    DistributionUserService distributionUserService;

    @Override
    public void balancePay(String expressId, Integer userId) throws Exception {
        Express ee = new Express();
        Express express = ee.selectById(expressId);
        //获取订单价格
        BigDecimal price = new BigDecimal(express.getPrice());
        //用户余额不足
        if (userRemainderService.hasRemain(userId, price)) {
            //userRemainderService.consume(userId, price);
            //修改订单信息
            express.setStatus(2);
            express.updateById();


            UserRemainder ee1 = new UserRemainder();
            ee1.setUserId(userId);
            ee1.setCreateTime(new Date());
            ee1.setRemainder(price);
            ee1.setOrderNum(express.getOrderNo());
            ee1.setType(UserRemainderType.CONSUME);
            ee1.setStatus(UserRemainderStatus.PASSED);
            ee1.insert();


        } else {
            throw new MessageException("余额不足");
        }
        distributionUserService.notifyRobbing();
    }

    @Autowired
    ShopService shopService;


    @Override
    public void createCityDistributionPayed(UserOrder order, Integer userId) {
        Express e = new Express();
        e.setUserId(userId);
        BigDecimal f = order.getFreight();
        e.setPrice(f == null ? null : String.valueOf(f));
        e.setStatus(ExpressStatus.PAYED.getKey());
        e.setPublishTime(new Date());
        e.setOrderNo(OrderNumUtil.getOrderIdByUUId());
        e.setNotes(StrUtil.format("帮我送:{}", order.getGoodsName()));
        e.setRequirementTime(order.getDistributionTime());
        e.setGetAddress(order.getAddressId());
        UserAddress address = shopService.syncAddressInfo(order.getShopId());
        if (address != null)
            e.setSenderAddress(address.getId());
        e.insert();
        distributionUserService.notifyRobbing();
    }

    @Override
    public Integer getOrderName(Integer cityId) {
        List<Express> orderName = baseMapper.getOrderName(cityId);
        if(orderName==null){
            return 0;
        }
        return orderName.size();
    }
}
