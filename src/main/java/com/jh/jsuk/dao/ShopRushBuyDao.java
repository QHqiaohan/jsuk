package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.entity.vo.RushBuyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 秒杀信息配置 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopRushBuyDao extends BaseMapper<ShopRushBuy> {

    List<Map<String,Object>> getShopRushBuyList(Page page, @Param("rushBuyId") Integer rushBuyId);

    @Select("select start_time,end_time from js_shop_rush_buy where id = #{id}")
    RushBuyVo shortVo(Integer id);

}
