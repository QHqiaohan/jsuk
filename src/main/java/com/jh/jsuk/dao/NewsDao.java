package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.News;
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
public interface NewsDao extends BaseMapper<News> {

    List<Integer> selectMessageIndex(Integer userId);

    News newsContent(@Param("userId") Integer userId,@Param("type") Integer type);

    List<News> list(@Param("userId") Integer userId,@Param("newsType") Integer newsType, Page page,@Param("ew") Wrapper<News> wrapper);
}
