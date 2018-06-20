package com.jh.jsuk.service;

import com.jh.jsuk.entity.Version;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 版本信息 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface VersionService extends IService<Version> {
    Version getNewVersion();
}
