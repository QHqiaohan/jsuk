package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserInvitationPay;

import java.util.List;

/**
 * <p>
 * 邀请用户 Mapper 接口
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-23
 */
public interface UserInvitationPayDao extends BaseMapper<UserInvitationPay> {

    List<UserInvitationPay> selectInfoByUser(Integer userId, Integer invitationId);

    Integer selectInvitationNumByUser(Integer invitationId);

}
