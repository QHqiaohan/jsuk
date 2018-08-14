package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.vo.DistributionVo;
import com.jh.jsuk.entity.vo.PlatformDistributionUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 骑手信息 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DistributionUserDao extends BaseMapper<DistributionUser> {

    Integer updateAccount(@Param("amount") BigDecimal amount, @Param("userId") Integer userId);

    List<PlatformDistributionUserVo> getDistributionUserList(RowBounds rowBounds, @Param("ew") Wrapper wrapper);

    List<PlatformDistributionUserVo> searchDistributionUserBy(RowBounds rowBounds,
                                                              @Param("ew") Wrapper wrapper,
                                                              @Param("account") String account,
                                                              @Param("name") String name);

    List<DistributionVo> list(Page page, @Param("account") String account, @Param("status") Integer status, @Param("name") String name);

    /**
     * 获取骑手订单数量
     */
    Integer getOrderAmount(Integer id);
}
