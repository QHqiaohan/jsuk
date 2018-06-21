package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopVisit;

/**
 * <p>
 * 商家端-店铺访问记录明细 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopVisitService extends IService<ShopVisit> {

    Page getVisitList(Page page, Wrapper wrapper, Integer shopId, String today);
}
