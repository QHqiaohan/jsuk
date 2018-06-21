package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopVisit;
import com.jh.jsuk.entity.vo.ShopVisitorVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 商家端-店铺访问记录明细 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ShopVisitDao extends BaseMapper<ShopVisit> {


    List<ShopVisitorVo> getVisitList(RowBounds rowBounds, @Param("ew") Wrapper wrapper,
                                     @Param("shopId") Integer shopId, @Param("today") String today);

}
