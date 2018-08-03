package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopRushBuyActivityDao;
import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.entity.vo.rushbuy.RushBuyActivityVO;
import com.jh.jsuk.entity.vo.rushbuy.ShopRushBuyActivityVO;
import com.jh.jsuk.service.ShopRushBuyActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 秒杀活动 服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@Service
public class ShopRushBuyActivityServiceImpl extends ServiceImpl<ShopRushBuyActivityDao, ShopRushBuyActivity> implements ShopRushBuyActivityService {

    @Override
    public Page page(Page page, Integer shopId) {
        List<ShopRushBuyActivityVO> list = baseMapper.page(page,shopId);
        return page.setRecords(list);
    }

    @Override
    public RushBuyActivityVO selectVo(Integer id) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq(ShopRushBuyActivity.ID,id);
        return baseMapper.selectVo(wrapper);
    }
}
