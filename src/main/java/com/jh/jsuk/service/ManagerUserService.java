package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ManagerUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理用户 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ManagerUserService extends IService<ManagerUser> {

    List<Map> selectVoList(Wrapper wrapper);
}
