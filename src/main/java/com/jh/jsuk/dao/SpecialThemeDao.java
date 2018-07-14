package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.SpecialTheme;
import com.jh.jsuk.entity.vo.SpecialThemeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 首页-专题精选 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
public interface SpecialThemeDao extends BaseMapper<SpecialTheme> {

    List<SpecialThemeVo> getVoByList(@Param("ew") Wrapper wrapper);
}
