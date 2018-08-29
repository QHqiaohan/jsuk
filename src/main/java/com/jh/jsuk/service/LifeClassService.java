package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.LifeClass;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 便捷生活分类 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface LifeClassService extends IService<LifeClass> {

    List<Map<String,Object>> findLifeClasses();
}
