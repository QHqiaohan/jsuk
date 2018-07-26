package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.entity.vo.RushBuyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 秒杀信息配置 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopRushBuyDao extends BaseMapper<ShopRushBuy> {

    List getShopRushBuyList(Page page,@Param("rushBuyId") Integer rushBuyId,@Param("ew") Wrapper wrapper);

    @Select("select start_time,end_time from js_shop_rush_buy where id = #{id}")
    RushBuyVo shortVo(Integer id);

}
