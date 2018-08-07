package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserIntegral;
import com.jh.jsuk.dao.UserIntegralDao;
import com.jh.jsuk.service.UserIntegralService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户积分表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserIntegralServiceImpl extends ServiceImpl<UserIntegralDao, UserIntegral> implements UserIntegralService {

    @Override
    public Integer getIntegral(Integer userId) {
        EntityWrapper<UserIntegral> wrapper = new EntityWrapper<>();
        wrapper.eq(UserIntegral.USER_ID, userId);
        List<UserIntegral> integrals = selectList(wrapper);
        int rtn = 0;
        for (UserIntegral integral : integrals) {
            Integer integralType = integral.getIntegralType();
            Integer number = integral.getIntegralNumber();
            if (number == null)
                number = 0;
            if (integralType == null || integralType == -1) {
                rtn -= number;
            } else {
                rtn += number;
            }
        }
        return rtn;
    }
}
