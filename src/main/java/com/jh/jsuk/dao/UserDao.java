package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.entity.vo.UserListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 普通用户 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserDao extends BaseMapper<User> {

    UserInfoVo selectInfoById(Integer id);

    User findUserHeadImgById(Integer id);

    List<UserListVo> listPage(Page page,@Param("ew") EntityWrapper wrapper);
}
