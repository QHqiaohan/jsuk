package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.GoodsEvaluate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品评价 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface GoodsEvaluateDao extends BaseMapper<GoodsEvaluate> {

    List<GoodsEvaluate> list(@Param("ew") Wrapper<GoodsEvaluate> wrapper,@Param("count") Integer count);
}
