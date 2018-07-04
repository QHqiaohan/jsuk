package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.entity.vo.GoodsBrandVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 类型的详细品牌 Mapper 接口
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
public interface GoodsBrandDao extends BaseMapper<GoodsBrand> {

    List<GoodsBrandVo> selectMPage(Page page,@Param("ew") Wrapper<GoodsBrand> wrapper);
}
