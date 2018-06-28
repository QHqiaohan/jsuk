package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.MarketComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 便捷生活&二手市场-评论 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface MarketCommentDao extends BaseMapper<MarketComment> {

    Integer getCommentCount(Integer activityId);

    List findCommentByActivityId(RowBounds rowBounds, @Param("ew") Wrapper wrapper, @Param("activityId") Integer activityId);
}
