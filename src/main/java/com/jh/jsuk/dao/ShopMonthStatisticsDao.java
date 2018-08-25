package com.jh.jsuk.dao;

import com.jh.jsuk.entity.ShopMonthStatistics;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商家端-月统计 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopMonthStatisticsDao extends BaseMapper<ShopMonthStatistics> {

    ShopMonthStatistics getmonthByShopId(@Param("shopId")Integer shopId,@Param("month")String month);

}
