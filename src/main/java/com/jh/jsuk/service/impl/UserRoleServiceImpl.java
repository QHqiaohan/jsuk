package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserRole;
import com.jh.jsuk.dao.UserRoleDao;
import com.jh.jsuk.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

}
