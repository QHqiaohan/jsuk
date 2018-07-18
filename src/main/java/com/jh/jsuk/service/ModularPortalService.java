package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.entity.vo.ModularPortalVo;

import java.util.List;

/**
 * <p>
 * 模块分类 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ModularPortalService extends IService<ModularPortal> {

    List<ModularPortalVo> getModularList();

    List<ModularPortal> findChildListByParentId(Integer id);
}
