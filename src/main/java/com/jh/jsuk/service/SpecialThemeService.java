package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.SpecialTheme;
import com.jh.jsuk.entity.vo.SpecialThemeVo;

import java.util.List;

/**
 * <p>
 * 首页-专题精选 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
public interface SpecialThemeService extends IService<SpecialTheme> {

    List<SpecialThemeVo> getVoByList(Wrapper wrapper);
}
