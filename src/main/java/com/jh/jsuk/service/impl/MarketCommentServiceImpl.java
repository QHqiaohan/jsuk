package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.MarketCommentDao;
import com.jh.jsuk.entity.MarketComment;
import com.jh.jsuk.entity.vo.MarketCommentVo;
import com.jh.jsuk.service.MarketCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 便捷生活&二手市场-评论 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class MarketCommentServiceImpl extends ServiceImpl<MarketCommentDao, MarketComment> implements MarketCommentService {

    @Override
    public Page findCommentByActivityId(Page page, Wrapper wrapper, Integer activityId) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.findCommentByActivityId(page, wrapper, activityId));
        return page;
    }

    @Override
    public List<MarketCommentVo> selectMarketCommentVoList(Integer activityId) {
        return baseMapper.selectMarketCommentVoList(activityId);
    }
}
