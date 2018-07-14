package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.SpecialThemeDao;
import com.jh.jsuk.entity.SpecialTheme;
import com.jh.jsuk.entity.vo.SpecialThemeVo;
import com.jh.jsuk.service.SpecialThemeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页-专题精选 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Service
public class SpecialThemeServiceImpl extends ServiceImpl<SpecialThemeDao, SpecialTheme> implements SpecialThemeService {

    @Override
    public List<SpecialThemeVo> getVoByList(Wrapper wrapper) {
        return baseMapper.getVoByList(wrapper);

    }
}
