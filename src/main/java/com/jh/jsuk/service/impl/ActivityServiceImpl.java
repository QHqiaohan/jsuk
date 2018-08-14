package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.ActivityDao;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivitySecondVo;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.entity.vo.ActivityVoT;
import com.jh.jsuk.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ActivityVo findActivityById(Integer id) {
        return baseMapper.findActivityById(id);
    }

    /**
     * 亲子、户外拓展、采摘活动、酒店住宿、特产购买
     * 根据modularId查询模块对应的活动
     */
    @Override
    public List<Activity> getActivityListByModularId(Integer modularId) {
        EntityWrapper ew = new EntityWrapper();
        ew.setEntity(new Activity());
        ew.where("modular_id={0}", modularId);

        return baseMapper.selectList(ew);
    }

    /**
     * 根据活动id查询亲子、户外拓展、采摘活动、酒店住宿、特产购买活动详情
     */
    @Override
    public Activity getActivityInfoById(Integer id) {

        return baseMapper.getActivityInfoById(id);
    }

    @Override
    public ActivityVoT selectActivityVoT(Integer id) {
        return baseMapper.selectActivityVoT(id);
    }

    @Override
    public Page<ActivitySecondVo> getSecondaryMarketList(Page page, String keywords) {
        List<ActivitySecondVo> list = baseMapper.getSecondaryMarketList(page, keywords);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<ActivitySecondVo> listSecondGoods(Page page, String kw) {
        List<ActivitySecondVo> vos = baseMapper.listSecondGoods(page, kw);
        for (ActivitySecondVo vo : vos) {
            if (vo.getImages() != null) {
                vo.setImageArray(vo.getImages().split(","));
            }
        }
        page.setRecords(vos);
        return page;
    }

    @Override
    public ActivityVo selectMerryageCar(Integer id) {
        return baseMapper.selectMerryageCar(id);
    }

}
