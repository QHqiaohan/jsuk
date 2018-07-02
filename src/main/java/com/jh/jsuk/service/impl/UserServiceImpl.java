package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserDao;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public void updateLoginStatus(Integer userId, UserType userType, String ipAddr) {
        if (userType == null) {
            return;
        }
        if (UserType.USER.equals(userType)) {
            User user = new User();
            user.setId(userId);
            user.setLastLoginTime(new Date());
            user.setLoginIp(ipAddr);
            user.updateById();
        } else if (UserType.DISTRIBUTION.equals(userType)) {
            DistributionUser user = new DistributionUser();
            user.setId(userId);
            user.setLastLoginTime(new Date());
            user.setLoginIp(ipAddr);
            user.updateById();
        } else if (userType.hasManageUserType()) {
            ManagerUser user = new ManagerUser();
            user.setId(userId);
            user.setLastLoginTime(new Date());
            user.setLoginIp(ipAddr);
            user.updateById();
        }
    }

}
