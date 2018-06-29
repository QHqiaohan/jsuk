package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.DistributionUser;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 骑手信息 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DistributionUserDao extends BaseMapper<DistributionUser> {

    Integer updateAccount(@Param("amount") BigDecimal amount,@Param("userId") Integer userId);
}
