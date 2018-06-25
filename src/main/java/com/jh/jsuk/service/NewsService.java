package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.News;
import com.jh.jsuk.entity.NewsUser;

import java.util.List;

/**
 * <p>
 * 用户消息表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface NewsService extends IService<News> {

    /**
     * 获取消息列表索引
     *
     * @param userId
     * @return
     * @throws Exception
     */
    List<News> listIndex(Integer userId) throws Exception;

    /**
     * 没有发送成功的消息重新推送
     *
     * @param newsUserId
     */
    void push(Integer newsUserId);

    /**
     * 定时任务调用数据
     */
    void fixedCall();

    /**
     * 没有发送成功的消息重新推送
     *
     * @param newsUser
     */
    void push(NewsUser newsUser);

    /**
     * 使用线程池推送
     * @param news
     * @param receivedUserIds
     * @throws Exception
     */
    void push(News news, Integer... receivedUserIds) throws Exception;

    /**
     * 获取系统消息列表
     * @param userId
     * @param page
     * @return
     * @throws Exception
     */
    Page listSys(Integer userId, Page page) throws Exception;

    /**
     * 获取二手市场消息列表
     * @param userId
     * @param page
     * @return
     * @throws Exception
     */
    Page listSecondHandMarket(Integer userId, Page page) throws Exception;
}
