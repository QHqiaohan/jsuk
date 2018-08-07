package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserInviteLog;
import com.jh.jsuk.dao.UserInviteLogDao;
import com.jh.jsuk.service.UserInviteLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-08-07
 */
@Service
public class UserInviteLogServiceImpl extends ServiceImpl<UserInviteLogDao, UserInviteLog> implements UserInviteLogService {

    @Override
    public void addInvite(Integer userId, Integer inviteUserId) {
        UserInviteLog log = new UserInviteLog();
        log.setUserId(userId);
        log.setInviteUserId(inviteUserId);
        log.setMoney(new BigDecimal("1"));
        log.insert();
    }
}
