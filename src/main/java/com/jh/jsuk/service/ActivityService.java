package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivityVo;

/**
 * <p>
 * 发布活动相关表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ActivityService extends IService<Activity> {

    Page getActivityList(Page page, Wrapper wrapper, Integer userId);

    ActivityVo findActivity(Integer id);
}
