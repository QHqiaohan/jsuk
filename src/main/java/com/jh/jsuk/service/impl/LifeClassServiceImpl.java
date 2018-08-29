package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.LifeClass;
import com.jh.jsuk.dao.LifeClassDao;
import com.jh.jsuk.service.LifeClassService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 便捷生活分类 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class LifeClassServiceImpl extends ServiceImpl<LifeClassDao, LifeClass> implements LifeClassService {

    @Override
    public List<Map<String, Object>> findLifeClasses() {
        return baseMapper.findLifeClasses();
    }
}
