package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserInviteLog;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tj
 * @since 2018-08-07
 */
public interface UserInviteLogService extends IService<UserInviteLog> {

    void addInvite(Integer userId,Integer inviteUserId);

    Page getRewardInfo(Page page, Integer userId);

    BigDecimal getTotal(Integer userId);

    Integer getLv1Cnt(Integer userId);

    Integer getLv2Cnt(Integer userId);

    Integer getLv3Cnt(Integer userId);

    Map<String,Object> get2Count(Integer userId);

    Page getDetail(Page page, Integer userId, String lv);
}
