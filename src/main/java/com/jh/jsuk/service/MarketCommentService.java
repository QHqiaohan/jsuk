package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.MarketComment;

/**
 * <p>
 * 便捷生活&二手市场-评论 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface MarketCommentService extends IService<MarketComment> {

    Page findCommentByActivityId(Page page, Wrapper wrapper, Integer activityId);
}
