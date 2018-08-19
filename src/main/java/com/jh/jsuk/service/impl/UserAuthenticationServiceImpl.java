package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserAuthentication;
import com.jh.jsuk.dao.UserAuthenticationDao;
import com.jh.jsuk.envm.UserAuthenticationStatus;
import com.jh.jsuk.service.UserAuthenticationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实名认证 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserAuthenticationServiceImpl extends ServiceImpl<UserAuthenticationDao, UserAuthentication> implements UserAuthenticationService {

    @Override
    public UserAuthenticationStatus getStatus(Integer userId) {
        EntityWrapper<UserAuthentication> wrapper = new EntityWrapper<>();
        wrapper.eq(UserAuthentication.USER_ID, userId);
        UserAuthentication userAuthentication = selectOne(wrapper);
        if (userAuthentication != null)
            return userAuthentication.getStatus();
        return null;
    }
}
