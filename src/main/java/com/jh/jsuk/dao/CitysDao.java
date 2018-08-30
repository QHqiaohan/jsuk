package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Citys;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface CitysDao extends BaseMapper<Citys> {

    @Select("select city_name from js_sys_citys where id = #{cityId}")
    String cityName(Integer cityId);

    List<Map<String, Object>> page(Page page,@Param("kw") String kw);

    Integer getcitynumBycityname(@Param("cityName") String cityName);
}
