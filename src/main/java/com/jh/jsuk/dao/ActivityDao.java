package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivityVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 发布活动相关表 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ActivityDao extends BaseMapper<Activity> {

    List getActivityList(RowBounds page, @Param("ew") Wrapper wrapper, @Param("userId") Integer userId);

    ActivityVo findActivity(Integer id);
}
