package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.dao.ManagerUserDao;
import com.jh.jsuk.service.ManagerUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理用户 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ManagerUserServiceImpl extends ServiceImpl<ManagerUserDao, ManagerUser> implements ManagerUserService {

    @Override
    public List<Map> selectVoList(Wrapper wrapper) {
        return baseMapper.selectVoList(wrapper);
    }

    @Override
    public List<ManagerUser> getUserListByUsername(String username) {
        return baseMapper.getUserListByUsername(username);
    }

    @Override
    public ManagerUser shopManager(Integer shopId) {
        EntityWrapper<ManagerUser> wrapper = new EntityWrapper<>();
        wrapper.eq(ManagerUser.SHOP_ID, shopId);
        return selectOne(wrapper);
    }
}
