package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserInviteLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.vo.UserInviteLogVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tj
 * @since 2018-08-07
 */
public interface UserInviteLogDao extends BaseMapper<UserInviteLog> {

    List<UserInviteLogVo> getRewardInfo(Page page, @Param("userId") Integer userId);

    BigDecimal getTotal(Integer userId);

    List<UserInviteLogVo> lv1Detail(Page page, @Param("userId") Integer userId);

    List<UserInviteLogVo> lv2Detail(Page page, @Param("userId") Integer userId);

    List<UserInviteLogVo> lv3Detail(Page page, @Param("userId") Integer userId);
}
