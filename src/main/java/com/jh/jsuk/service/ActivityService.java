package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.entity.vo.ActivityVoT;

import java.util.List;

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

    ActivityVo findActivityById(Integer id);

    /**
     * 亲子、户外拓展、采摘活动、酒店住宿、特产购买
     * 根据modularId查询模块对应的活动
     */
    List<Activity> getActivityListByModularId(Integer modularId);

    /**
     * 根据活动id查询亲子、户外拓展、采摘活动、酒店住宿、特产购买活动详情
     */
    Activity getActivityInfoById(Integer id);

    ActivityVoT selectActivityVoT(Integer id);
}
