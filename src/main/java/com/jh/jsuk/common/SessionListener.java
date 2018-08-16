package com.jh.jsuk.common;


import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.exception.NeedLoginException;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopService;
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

    @Autowired
    ShopService shopService;

    public boolean isSessionValid() {
        return session.isValid();
    }

    public boolean canUse() {
        return session.canUse();
    }

    public void updateSession(Integer userId, UserType userType) throws Exception {
        if (userId == null || userType == null)
            throw new NeedLoginException();

        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");
        System.out.println("update ------------------------ session");

        session.setUserId(userId);
        session.setUserType(userType);
        switch (userType) {
            case DISTRIBUTION:
                DistributionUser d = distributionUserService.selectById(userId);
                if (d == null)
                    throw new MessageException("用户不存在");
                session.setLastLogin(d.getLastLoginTime());
                session.setCanUse(d.getCanUse());
                session.setNickName(d.getName());
                session.setPhone(d.getPhone());
                session.setHeadImage(d.getHeadImg());
                break;
            case USER:
                User u = userService.selectById(userId);
                if (u == null)
                    throw new MessageException("用户不存在");
                session.setLastLogin(u.getLastLoginTime());
                session.setCanUse(u.getCanUse());
                session.setNickName(u.getNickName());
                session.setPhone(u.getPhone());
                session.setHeadImage(u.getHeadImg());
                break;
            case ADMIN:
            case CITY_ADMIN:
            case SHOP:
                ManagerUser m = managerUserService.selectById(userId);
                if (m == null)
                    throw new MessageException("用户不存在");
                session.setLastLogin(m.getLastLoginTime());
                session.setCanUse(m.getCanUse());
                session.setNickName(m.getNickName());
                session.setPhone(m.getPhone());
                session.setHeadImage(m.getHeadImg());
                session.setShopId(m.getShopId());
                Shop shop = shopService.selectById(m.getShopId());
                String province = m.getProvince();
                session.setProvinceId(province == null ? null : Integer.valueOf(province));
                String city = m.getCity();
                session.setCityId(city == null ? null : Integer.valueOf(city));
                if (shop != null)
                    session.setShopName(shop.getShopName());
                break;
        }


        System.out.println(session);

    }


}
