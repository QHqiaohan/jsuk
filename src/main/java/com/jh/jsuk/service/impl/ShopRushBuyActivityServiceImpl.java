package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ShopRushBuyActivityDao;
import com.jh.jsuk.entity.ShopRushBuyActivity;
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
    public Page page(Page page) {
        List<ShopRushBuyActivityVO> list = baseMapper.page(page);
        return page.setRecords(list);
    }
}
