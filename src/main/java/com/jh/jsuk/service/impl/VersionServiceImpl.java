package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.Version;
import com.jh.jsuk.dao.VersionDao;
import com.jh.jsuk.service.VersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 版本信息 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class VersionServiceImpl extends ServiceImpl<VersionDao, Version> implements VersionService {
    @Override
    public Version getNewVersion() {
        return baseMapper.getNewVersion();
    }
}
