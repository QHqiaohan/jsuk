package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.entity.vo.rushbuy.RushBuyActivityVO;

/**
 * <p>
 * 秒杀活动 服务类
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
public interface ShopRushBuyActivityService extends IService<ShopRushBuyActivity> {

    Page page(Page page, Integer shopId);

    RushBuyActivityVO selectVo(Integer id);
}
