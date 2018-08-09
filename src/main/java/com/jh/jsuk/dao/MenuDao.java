package com.jh.jsuk.dao;

import com.jh.jsuk.entity.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> queryMenuByUid(@Param("uid") Integer uid);

    void setMenu(@Param("menuId") Integer menuId, @Param("userId") Integer userId);
}
