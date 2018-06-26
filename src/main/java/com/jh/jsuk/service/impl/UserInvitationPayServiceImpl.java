package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserInvitationPayDao;
import com.jh.jsuk.entity.UserInvitationPay;
import com.jh.jsuk.service.UserInvitationPayService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 邀请用户 服务实现类
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-23
 */
@Service
public class UserInvitationPayServiceImpl extends ServiceImpl<UserInvitationPayDao, UserInvitationPay> implements UserInvitationPayService {

    @Override
    public List<UserInvitationPay> selectInfoByUser(Integer userId, Integer invitationId) {
        List<UserInvitationPay> invitationUserPayList = baseMapper.selectInfoByUser(userId, invitationId);
        return invitationUserPayList;
    }

    @Override
    public Integer selectInvitationNumByUser(Integer invitationId) {
        Integer userNum = baseMapper.selectInvitationNumByUser(invitationId);
        return userNum;
    }
}

