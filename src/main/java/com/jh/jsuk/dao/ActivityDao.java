package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.vo.ActivitySecondVo;
import com.jh.jsuk.entity.vo.ActivityVo;
import com.jh.jsuk.entity.vo.ActivityVoT;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    ActivityVo findActivityById(@Param("id") Integer id);

    /**
     * 根据活动id查询亲子、户外拓展、采摘活动、酒店住宿、特产购买活动详情
     */
    Activity getActivityInfoById(Integer id);

    ActivityVoT selectActivityVoT(@Param("id") Integer id);

    List<ActivitySecondVo> getSecondaryMarketList(Page page, @Param(value = "keywords") String keywords);

    List<ActivitySecondVo> listSecondGoods(Page page, @Param(value = "kw") String kw);

    ActivityVo selectMerryageCar(@Param("id") Integer id);
}
