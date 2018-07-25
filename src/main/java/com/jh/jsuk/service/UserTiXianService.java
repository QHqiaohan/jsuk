package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.utils.Result;

/**
 * <p>
 * 用户提现记录 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
public interface UserTiXianService extends IService<UserTiXian> {

    Result tixian(UserTiXian userTiXian, Integer type, Integer userId);
}
