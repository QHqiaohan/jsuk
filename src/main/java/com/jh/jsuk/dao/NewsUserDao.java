package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.NewsUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户消息表 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface NewsUserDao extends BaseMapper<NewsUser> {

    List<NewsUser> getNotPushed(@Param("ew") Wrapper<NewsUser> wrapper);
}
