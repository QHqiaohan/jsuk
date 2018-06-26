package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserInvitationPay;

import java.util.List;

/**
 * <p>
 * 邀请用户 服务类
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-23
 */
public interface UserInvitationPayService extends IService<UserInvitationPay> {

    List<UserInvitationPay> selectInfoByUser(Integer userId, Integer invitationId);

    Integer selectInvitationNumByUser(Integer invitationId);

}
