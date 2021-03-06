package com.jh.jsuk.service;

import com.jh.jsuk.entity.UserAuthentication;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.envm.UserAuthenticationStatus;

/**
 * <p>
 * 实名认证 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserAuthenticationService extends IService<UserAuthentication> {

    UserAuthenticationStatus getStatus(Integer userId);

}
