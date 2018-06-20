package com.jh.jsuk.dao;

import com.jh.jsuk.entity.Version;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 版本信息 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface VersionDao extends BaseMapper<Version> {
    Version getNewVersion();
}
