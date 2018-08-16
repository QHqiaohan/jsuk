package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.vo.UserInfoVo;
import com.jh.jsuk.entity.vo.UserInfoVo2;
import com.jh.jsuk.entity.vo.UserListVo;
import com.jh.jsuk.entity.vo.UserVo2;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
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

    List<User> selectUserListBy(@Param("keywords") String keywords);

    @Select("select nick_name from js_user where id = #{id}")
    String userName(Integer id);

    @Select("select nick_name,head_img,phone from js_user where id = #{id}")
    UserVo2 userVo2(Integer id);

    UserInfoVo2 selectUserInfoById(@Param("id")Integer id);

    BigDecimal discount(Integer userId);

}
