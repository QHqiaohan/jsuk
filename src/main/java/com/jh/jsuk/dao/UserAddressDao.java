package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserAddress;

/**
 * <p>
 * 普通用户地址表 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserAddressDao extends BaseMapper<UserAddress> {

    UserAddress getUserAddressbyId(Integer id);
}
