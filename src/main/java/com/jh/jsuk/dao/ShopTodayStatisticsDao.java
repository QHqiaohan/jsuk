package com.jh.jsuk.dao;

import com.jh.jsuk.entity.ShopTodayStatistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商家端-今日统计数据 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopTodayStatisticsDao extends BaseMapper<ShopTodayStatistics> {
    ShopTodayStatistics getOneByshopId(@Param("da1")String da1, @Param("da2") String da2, @Param("shopId")Integer shopId);

}
