package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserRemainderDao;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.service.UserRemainderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户余额表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Service
public class UserRemainderServiceImpl extends ServiceImpl<UserRemainderDao, UserRemainder> implements UserRemainderService {

    @Override
    public BigDecimal getRemainder(Integer userId){
        List<UserRemainder> list = selectList(new EntityWrapper<UserRemainder>()
                .eq(UserRemainder.USER_ID, userId));
        // 初始化记录总余额
        BigDecimal remain = new BigDecimal("0.00");
        if (list == null || list.isEmpty())
            return remain;
        // 如果余额表有该用户信息
        for (UserRemainder remainder : list) {
            // 如果是消费
            if (remainder.getType() == -1) {
                remain = remain.subtract(remainder.getRemainder());
            } else {
                // 充值
                remain = remain.add(remainder.getRemainder());
            }
        }
        return remain;
    }

    @Override
    public boolean hasRemain(Integer userId) {
        return hasRemain(userId,new BigDecimal("0.00"));
    }

    @Override
    public boolean hasRemain(Integer userId, BigDecimal bigDecimal){
        return getRemainder(userId).compareTo(bigDecimal) > 0;
    }

    @Override
    public void consume(Integer userId, BigDecimal amount) throws Exception {
        UserRemainder e = new UserRemainder();
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        e.setType(-1);
        insert(e);
    }

    @Override
    public void recharge(Integer userId, BigDecimal amount) throws Exception {
        UserRemainder e = new UserRemainder();
        e.setType(1);
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        insert(e);
    }
}
