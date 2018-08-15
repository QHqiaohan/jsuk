package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Feedback;
import com.jh.jsuk.dao.FeedbackDao;
import com.jh.jsuk.service.FeedbackService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 意见反馈 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao, Feedback> implements FeedbackService {

    @Override
    public Page list(Page page, String kw) {
        return page.setRecords(baseMapper.list(page,kw));
    }
}
