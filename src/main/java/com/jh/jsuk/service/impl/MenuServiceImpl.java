package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.Menu;
import com.jh.jsuk.dao.MenuDao;
import com.jh.jsuk.service.MenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Override
    public List<Menu> queryMenuByUid(Integer uid) {
        return baseMapper.queryMenuByUid(uid);
    }

    @Override
    public void setMenu(Integer menuId, Integer userId) {
        baseMapper.setMenu(menuId,userId);
    }
}
