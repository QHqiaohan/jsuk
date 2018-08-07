package com.jh.jsuk.service;

import com.jh.jsuk.entity.UserInviteLog;
import com.baomidou.mybatisplus.service.IService;

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

}
