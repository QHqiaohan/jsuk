package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserDao;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 普通用户 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public UserInfoVo selectInfoById(Integer id) {
        return baseMapper.selectInfoById(id);
    }

}
