package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.GoodsEvaluate;

import java.util.List;
import java.util.Map;

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

    Page listPage(Integer goodsId, String type, Page page) throws Exception;

    Map<String,Object> counts(Integer goodsId) throws Exception;

    Page listEvaluate(Page page, String categoryId, String kw, String brandId, Integer shopId, String nickName);

    Page listUser(Integer lUserId, Page page, Wrapper wrapper);
}
