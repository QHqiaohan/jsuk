package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.envm.UserType;

/**
 * <p>
 * 普通用户 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserService extends IService<User> {

    UserInfoVo selectInfoById(Integer id);

    /**
     * 更新用户的最近更新状态
     * @param userId
     * @param userType
     * @param ipAddr
     */
    void updateLoginStatus(Integer userId, UserType userType, String ipAddr);

}
