package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.CollectGoodsMapper;
import com.jh.jsuk.entity.CollectGoods;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.CollectGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户商品收藏 服务实现类
 * </p>
 *
 * @author tj123
 * @since 2018-06-25
 */
@Service
public class CollectGoodsServiceImpl extends ServiceImpl<CollectGoodsMapper, CollectGoods> implements CollectGoodsService {

    @Override
    public Page selectCollectList(Integer userId, Page page) {
        List<GoodsSalesPriceVo> list = baseMapper.selectCollectList(page,userId);
        return page.setRecords(list);
    }
}
