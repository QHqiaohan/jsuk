package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopVisitDao;
import com.jh.jsuk.entity.ShopVisit;
import com.jh.jsuk.service.ShopVisitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商家端-店铺访问记录明细 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ShopVisitServiceImpl extends ServiceImpl<ShopVisitDao, ShopVisit> implements ShopVisitService {

    @Override
    public Page getVisitList(Page page, Wrapper wrapper, Integer shopId, String today) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getVisitList(page, wrapper, shopId, today));
        return page;
    }

    @Override
    public List<ShopVisit> getListShopVisit(Integer ShopId) {
        return baseMapper.getListShopVisit(ShopId);
    }

}
