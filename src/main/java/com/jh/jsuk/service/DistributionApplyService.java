package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.vo.UserApplyVo;

import java.math.BigDecimal;

/**
 * <p>
 * 配送端提现申请
 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DistributionApplyService extends IService<DistributionApply> {

    Page<UserApplyVo> selectPageByUserInfo(Page page, Wrapper wrapper);

    Page searchDistributionUserTiXian(Page page, Wrapper wrapper,Integer tixianId,Integer begin,Integer end,Integer status);

    void createCashApplying(Integer userId, BigDecimal price, String tiXianNo, Integer bankId);

    void confirm(String no);

    void decline(String no);

    BigDecimal getRemainder(Integer userId);

    void syncRemainder(Integer userId);
}
