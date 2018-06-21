package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopTodayMoney;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 商家端-今日交易额明细 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopTodayMoneyDao extends BaseMapper<ShopTodayMoney> {

    List getTodayMoneyList(RowBounds rowBounds, @Param("ew") Wrapper wrapper,
                           @Param("shopId") Integer shopId, @Param("today") String today);
}
