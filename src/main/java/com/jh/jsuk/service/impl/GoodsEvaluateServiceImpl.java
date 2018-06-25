package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.GoodsEvaluateDao;
import com.jh.jsuk.entity.GoodsEvaluate;
import com.jh.jsuk.service.GoodsEvaluateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class GoodsEvaluateServiceImpl extends ServiceImpl<GoodsEvaluateDao, GoodsEvaluate> implements GoodsEvaluateService {

    @Override
    public List<GoodsEvaluate> get(Integer goodsId, Integer count) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0)
                .orderBy(GoodsEvaluate.CREATE_TIME,false);
        return baseMapper.list(wrapper, count);
    }

    @Override
    public Integer count(Integer goodsId) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0);
        return selectCount(wrapper);
    }

    @Override
    public Page listPage(Integer goodsId, Page page) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0)
                .orderBy(GoodsEvaluate.CREATE_TIME,false);
        return selectPage(page, wrapper);
    }
}
