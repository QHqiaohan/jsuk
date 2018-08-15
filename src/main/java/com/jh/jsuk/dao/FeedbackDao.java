package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Feedback;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.vo.FeedBackVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 意见反馈 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface FeedbackDao extends BaseMapper<Feedback> {
    /**
     * 后台 意见反馈列表
     */
    List<FeedBackVo> list(Page page, @Param("kw") String kw);
}
