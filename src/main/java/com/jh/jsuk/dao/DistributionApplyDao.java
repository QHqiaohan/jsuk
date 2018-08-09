package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.vo.UserApplyVo;
import com.jh.jsuk.entity.vo.DistributionApplyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 配送端提现申请
 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DistributionApplyDao extends BaseMapper<DistributionApply> {
    List<UserApplyVo> selectPageByUserInfo(RowBounds page, @Param("ew") Wrapper wrapper);


    List<DistributionApplyVo> searchDistributionUserTiXian(Page page, Wrapper wrapper);
}
