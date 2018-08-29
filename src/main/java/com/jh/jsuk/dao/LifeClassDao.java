package com.jh.jsuk.dao;

import com.jh.jsuk.entity.LifeClass;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 便捷生活分类 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface LifeClassDao extends BaseMapper<LifeClass> {

    List<Map<String, Object>> findLifeClasses();


}
