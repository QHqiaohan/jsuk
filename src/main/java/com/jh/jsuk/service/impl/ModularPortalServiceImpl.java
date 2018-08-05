package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ModularPortalDao;
import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.entity.vo.ModularPortalVo;
import com.jh.jsuk.entity.vo.ModularPortalVo2;
import com.jh.jsuk.service.ModularPortalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 模块分类 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ModularPortalServiceImpl extends ServiceImpl<ModularPortalDao, ModularPortal> implements ModularPortalService {

    @Resource
    private ModularPortalDao modularPortalDao;

    @Override
    public List<ModularPortalVo> getModularList() {
        return baseMapper.getModularList();
    }

    @Override
    public List<ModularPortal> findChildListByParentId(Integer id) {
        return modularPortalDao.findChildListByParentId(id);
    }

    @Override
    public List<ModularPortalVo2> list() {

        return baseMapper.list();
    }
}
