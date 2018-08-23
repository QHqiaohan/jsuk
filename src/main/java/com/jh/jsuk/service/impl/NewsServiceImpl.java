package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.NewsDao;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.dto.MessageDTO;
import com.jh.jsuk.envm.NewsType;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.mq.MessagePushProducer;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.service.NewsService;
import com.jh.jsuk.service.NewsUserService;
import com.jh.jsuk.utils.JPushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Slf4j
@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {

    @Override
    public List<News> listIndex(Integer userId) throws Exception {
        List<Integer> index = baseMapper.selectMessageIndex(userId);
        List<News> list = new ArrayList<>();
        for (Integer type : index) {
            list.add(baseMapper.newsContent(userId, type));
        }
        return list;
    }

    @Resource(name = "pushTask")
    ThreadPoolTaskExecutor pushTaskExecutor;

    @Autowired
    NewsUserService newsUserService;

    @Override
    public void push(Integer newsUserId) {
        push(newsUserService.selectById(newsUserId));
    }

    @Override
    public void fixedCall() {
        List<NewsUser> notPushed = newsUserService.getNotPushed();
        if (notPushed == null || notPushed.isEmpty()) {
            return;
        }
        for (NewsUser user : notPushed) {
            push(user);
        }
    }

    @Override
    public void push(NewsUser newsUser) {
        if (newsUser == null) {
            return;
        }
        if (newsUser.isPushed()) {
            return;
        }
        News news = selectById(newsUser.getNewsId());
        pushTaskExecutor.execute(() -> {
            try {
                JPushUtils.push(UserType.USER, newsUser.getReceivedId(), news.getContent(), news.getTitle());
                newsUser.setIsPushed(1);
                newsUser.updateById();
            } catch (Exception e) {
                log.error("推送消息 title:{} content:{}失败", news.getTitle(), news.getContent(), e);
            }
        });
    }

    @Override
    public void push(News news, Integer... receivedUserIds) throws Exception {
        if (news.getNewsType() == null) {
            throw new Exception("类型不能为空");
        }
        if (NewsType.SH_MKT.eq(news.getNewsType())) {
            if (news.getMarketCommentId() == null) {
                throw new Exception("marketCommentId不能为空");
            }
        }
        news.setCreateTime(new Date());
        insert(news);
        for (Integer receivedUserId : receivedUserIds) {
            Integer newsId = news.getId();
            NewsUser newsUser = new NewsUser();
            newsUser.setNewsId(newsId);
            newsUser.setReceivedId(receivedUserId);
            newsUser.setIsPushed(0);
            newsUser.setIsRead(0);
            newsUser.insert();
            pushTaskExecutor.execute(() -> {
                try {
                    JPushUtils.push(UserType.USER, receivedUserId, news.getContent(), news.getTitle());
                    newsUser.setIsPushed(1);
                    newsUser.updateById();
                } catch (Exception e) {
                    log.error("推送消息 title:{} content:{}失败", news.getTitle(), news.getContent(), e);
                }
            });
        }
    }

    public void setRead(List<Integer> newsIds, Integer userId) {
        if (newsIds == null || newsIds.isEmpty()) {
            return;
        }
        Wrapper<NewsUser> wpr = new EntityWrapper<>();
        wpr.in(NewsUser.NEWS_ID, newsIds)
            .eq(NewsUser.RECEIVED_ID, userId)
            .eq(NewsUser.IS_READ, 0);
        NewsUser newsUser = new NewsUser();
        newsUser.setIsRead(1);
        newsUser.setIsPushed(1);
        newsUserService.update(newsUser, wpr);
    }

    @Override
    public Page listSys(Integer userId, Page page) throws Exception {
        Wrapper<News> wrapper = new EntityWrapper<>();
        wrapper.where("1=1").orderBy(News.CREATE_TIME, false);
        List<News> list = baseMapper.list(userId, NewsType.SYS.getKey(), page, wrapper);
        List<Integer> newsIds = new ArrayList<>();
        for (News news : list) {
            newsIds.add(news.getId());
        }
        setRead(newsIds, userId);
        return page.setRecords(list);

    }

    @Override
    public Page listSecondHandMarket(Integer userId, Page page) throws Exception {
        Wrapper<News> wrapper = new EntityWrapper<>();
        wrapper.where("1=1").orderBy(News.CREATE_TIME, false);
        List<News> list = baseMapper.list(userId, NewsType.SH_MKT.getKey(), page, wrapper);
        List<Integer> newsIds = new ArrayList<>();
        for (News news : list) {
            newsIds.add(news.getId());
        }
        setRead(newsIds, userId);
        return page.setRecords(list);
    }

    @Override
    public void pushComment(MarketComment marketComment, Activity activity) throws Exception {
        News news = new News();
        news.setNewsType(NewsType.SH_MKT);
        news.setTitle("二手市场有人留言了");
        news.setContent(marketComment.getComment());
        news.setMarketCommentId(activity.getId());
        news.setSendUserId(marketComment.getUserId() == null ? null : String.valueOf(marketComment.getUserId()));
        push(news, activity.getUserId());
    }

    @Autowired
    ExpressService expressService;

    @Autowired
    MessagePushProducer messagePushProducer;

    @Override
    public void urgeDistribution(Integer expressId,String userName) {
        Express express = expressService.selectById(expressId);
        MessageDTO data = new MessageDTO();
        data.setContent("订单(" + express.getOrderNo() + ")请尽快送货,催单人:" + userName);
        messagePushProducer.pushDistp(data);
    }
}
