package com.jh.jsuk.common;


import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.exception.NeedLoginException;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionListener {

    @Autowired
    Session session;

    @Autowired
    ManagerUserService managerUserService;

    @Autowired
    UserService userService;

    @Autowired
    DistributionUserService distributionUserService;


    public void updateSession(Integer userId, UserType userType) throws Exception{
        if(userId == null || userType ==null)
            throw new NeedLoginException();
        switch (userType) {
            case DISTRIBUTION:
                DistributionUser d = distributionUserService.selectById(userId);
                if(d == null)
                    throw new MessageException("用户不存在");
                session.setUserType(userType);
//                session.set


                break;
            case USER:




                break;
            case ADMIN:
            case CITY_ADMIN:
            case SHOP:


                break;
        }

//        if (userType == null) {
//            ManagerUser us = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.PHONE, phone));
//            if (us != null) {
//                user = us.toParentUser();
//                userType = user.getUserType();
//            }
//        } else if (UserType.USER.equals(userType)) {
//            User us = userService.selectOne(new EntityWrapper<User>().eq(User.PHONE, phone));
//            if (us != null)
//                user = us.toParentUser();
//        } else if (UserType.SHOP.equals(userType)) {
//            ManagerUser us = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.PHONE, phone));
//            if (us != null)
//                user = us.toParentUser();
//        } else if (UserType.DISTRIBUTION.equals(userType)) {
//            DistributionUser us = distributionUserService.selectOne(new EntityWrapper<DistributionUser>().eq(DistributionUser.PHONE, phone));
//            if (us != null)
//                user = us.toParentUser();
//        } else if (userType.hasManageUserType()) {
//            ManagerUser us = managerUserService.selectOne(new EntityWrapper<ManagerUser>().eq(ManagerUser.PHONE, phone));
//            if (us != null)
//                user = us.toParentUser();
//        }
    }





}
