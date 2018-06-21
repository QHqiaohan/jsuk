package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.User;

/**
 * <p>
 * 普通用户 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserDao extends BaseMapper<User> {

    User findUserHeadImgById(Integer id);

}
