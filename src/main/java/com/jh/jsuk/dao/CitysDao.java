package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Citys;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    List<Map<String, Object>> page(Page page,@Param("kw") String kw);

}
