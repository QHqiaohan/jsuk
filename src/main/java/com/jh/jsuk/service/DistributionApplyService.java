package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.vo.UserApplyVo;

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

}
