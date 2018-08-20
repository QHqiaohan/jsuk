package com.jh.jsuk.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserDao;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.info.UserRemainderInfo;
import com.jh.jsuk.entity.vo.ConsumeInfo;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.entity.vo.UserInfoVo2;
import com.jh.jsuk.entity.vo.UserListVo;
import com.jh.jsuk.envm.UserAuthenticationStatus;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
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

    @Autowired
    UserIntegralService userIntegralService;

    @Autowired
    UserRemainderService userRemainderService;

    @Autowired
    UserOrderService userOrderService;

    @Override
    public UserInfoVo selectInfoById(Integer id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    public void updateLoginStatus(Integer userId, UserType userType, String ipAddr, Date login) {
        if (userType == null) {
            return;
        }
        if (UserType.USER.equals(userType)) {
            User user = new User();
            user.setId(userId);
            user.setLastLoginTime(login);
            user.setLoginIp(ipAddr);
            user.updateById();
        } else if (UserType.DISTRIBUTION.equals(userType)) {
            DistributionUser user = new DistributionUser();
            user.setId(userId);
            user.setLastLoginTime(login);
            user.setLoginIp(ipAddr);
            user.updateById();
        } else if (userType.hasManageUserType()) {
            ManagerUser user = new ManagerUser();
            user.setId(userId);
            user.setLastLoginTime(login);
            user.setLoginIp(ipAddr);
            user.updateById();
        }
    }

    @Override
    public Page listPage(Page page, String kw, String nickName, List<String> date) {
        String start = null, stop = null;
        if (date != null && !date.isEmpty()) {
            start = date.get(0);
            stop = date.get(1);
        }
        if (kw != null) {
            kw = "%" + kw.trim() + "%";
        }
        if (nickName != null) {
            nickName = "%" + nickName.trim() + "%";
        }
        EntityWrapper wrapper = new EntityWrapper();
        if (StrUtil.isNotBlank(kw)) {
            wrapper.like(User.PHONE, kw);
        }
        if (StrUtil.isNotBlank(nickName)) {
            wrapper.like(User.NICK_NAME, nickName);
        }
        if (StrUtil.isNotBlank(start) && StrUtil.isNotBlank(stop)) {
            wrapper.gt(User.CREATE_TIME, DateTime.of(start, "yyyy-MM-dd"));
            wrapper.lt(User.CREATE_TIME, DateTime.of(stop, "yyyy-MM-dd"));
        }

        wrapper.eq(User.CAN_USE, 1);
        List<UserListVo> list = baseMapper.listPage(page, wrapper);
        for (UserListVo vo : list) {
            vo.setIntegral(userIntegralService.getIntegral(vo.getId()));
            vo.setConsumption(userRemainderService.getConsumption(vo.getId()));
            vo.setOrderCount(userOrderService.orderCount(vo.getId()));
        }
        return page.setRecords(list);
    }

    @Override
    public List<User> selectUserListBy(String keywords) {
        return baseMapper.selectUserListBy(keywords);
    }

    @Override
    public UserInfoVo2 selectUserInfoById(Integer id) {
        UserInfoVo2 vo = baseMapper.selectUserInfoById(id);
        if (StrUtil.isBlank(vo.getMemberName())) {
            vo.setMemberName("普通会员");
        }
        return vo;
    }

    @Override
    public Map<String, Object> discount(Integer lUserId) {
        return baseMapper.discount(lUserId);
    }

    @Override
    public Integer getIntegral(Integer userId) {
        return userIntegralService.getIntegral(userId);
    }

    @Override
    public UserRemainderInfo getRemainder(Integer userId) {
        return userRemainderService.getRemainder(userId);
    }

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Override
    public UserAuthenticationStatus getAuthenticationStatus(Integer userId) {
        return userAuthenticationService.getStatus(userId);
    }

    @Override
    public ConsumeInfo getConsume(Integer userId) {
        ConsumeInfo info = new ConsumeInfo();
        List<UserOrder> userOrderList = userOrderService.selectList(new EntityWrapper<UserOrder>()
            .eq(UserOrder.USER_ID, userId)
        );
        BigDecimal consumeCount = new BigDecimal(0.00);
        info.setConsumeAmount(consumeCount);
        if (userOrderList != null && userOrderList.size() > 0) {
            //该用户的订单数量
            info.setOrderCount(userOrderList.size());
            for (UserOrder userOrder : userOrderList) {
                BigDecimal orderRealPrice = userOrder.getOrderRealPrice();
                if (orderRealPrice == null) {
                    orderRealPrice = new BigDecimal("0.00");
                }
                consumeCount = consumeCount.add(orderRealPrice);
            }
        } else {
            info.setOrderCount(0);
        }
        return info;
    }


}
