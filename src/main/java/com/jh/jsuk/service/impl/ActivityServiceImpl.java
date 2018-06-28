package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ActivityDao;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.service.ActivityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发布活动相关表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, Activity> implements ActivityService {

    @Override
    public Page getActivityList(Page page, Wrapper wrapper, Integer userId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getActivityList(page, wrapper, userId));
        return page;
    }

    @Override
    public ActivityVo findActivity(Integer id) {
        return baseMapper.findActivity(id);
    }
}
