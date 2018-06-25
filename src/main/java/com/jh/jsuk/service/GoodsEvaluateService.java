package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.GoodsEvaluate;

import java.util.List;

/**
 * <p>
 * 商品评价 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface GoodsEvaluateService extends IService<GoodsEvaluate> {

    List<GoodsEvaluate> get(Integer goodsId, Integer count) throws Exception;

    Integer count(Integer goodsId) throws Exception;

    Page listPage(Integer goodsId, Page page) throws Exception;
}
