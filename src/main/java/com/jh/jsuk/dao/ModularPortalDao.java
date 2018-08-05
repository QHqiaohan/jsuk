package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.entity.vo.ModularPortalVo;
import com.jh.jsuk.entity.vo.ModularPortalVo2;

import java.util.List;

/**
 * <p>
 * 模块分类 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ModularPortalDao extends BaseMapper<ModularPortal> {

    List<ModularPortalVo> getModularList();

    List<ModularPortal> findChildrenModular();

    List<ModularPortal> findChildListByParentId(Integer id);

    List<ModularPortalVo2> list();
}
