package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.info.UserRemainderInfo;
import com.jh.jsuk.entity.vo.ConsumeInfo;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.entity.vo.UserInfoVo2;
import com.jh.jsuk.envm.UserAuthenticationStatus;
import com.jh.jsuk.envm.UserType;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     *
     * @param userId
     * @param userType
     * @param ipAddr
     * @param login
     */
    void updateLoginStatus(Integer userId, UserType userType, String ipAddr, Date login);

    Page listPage(Page page, String kw, String nickName, List<String> dates);

    List<User> selectUserListBy(String keywords);

    UserInfoVo2 selectUserInfoById(Integer id);

    Map<String,Object> discount(Integer lUserId);

    /**
     * 获取用户积分
     * @param userId
     * @return
     */
    Integer getIntegral(Integer userId);

    /**
     * 用户余额
     * @param userId
     * @return
     */
    UserRemainderInfo getRemainder(Integer userId);

    /**
     * 获取审核状态
     * @param userId
     * @return
     */
    UserAuthenticationStatus getAuthenticationStatus(Integer userId);

    ConsumeInfo getConsume(Integer userId);

}
