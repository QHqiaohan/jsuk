package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.News;
import com.jh.jsuk.dao.NewsDao;
import com.jh.jsuk.service.NewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {

}
