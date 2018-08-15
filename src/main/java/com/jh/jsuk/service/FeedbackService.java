package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Feedback;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 意见反馈 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface FeedbackService extends IService<Feedback> {

    Page list(Page page, String kw);
}
