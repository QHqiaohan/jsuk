package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ManagerUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理用户 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ManagerUserDao extends BaseMapper<ManagerUser> {

    List<Map> selectVoList(@Param("ew") Wrapper wrapper);

    List<ManagerUser> getUserListByUsername(@Param("username") String username);
}
