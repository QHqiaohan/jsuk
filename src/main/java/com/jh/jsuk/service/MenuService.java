package com.jh.jsuk.service;

import com.jh.jsuk.entity.Menu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface MenuService extends IService<Menu> {

    List<Menu> queryMenuByUid(Integer uid);

    void setMenu(Integer menuId, Integer userId);
}
