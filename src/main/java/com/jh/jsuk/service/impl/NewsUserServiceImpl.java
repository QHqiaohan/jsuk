package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.NewsUserDao;
import com.jh.jsuk.entity.NewsUser;
import com.jh.jsuk.service.NewsUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户消息表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class NewsUserServiceImpl extends ServiceImpl<NewsUserDao, NewsUser> implements NewsUserService {

    @Override
    public List<NewsUser> getNotPushed() {
        Wrapper<NewsUser> wrapper = new EntityWrapper<>();
        wrapper.ne(NewsUser.IS_PUSHED, 1).or().isNull(NewsUser.IS_PUSHED);
        return baseMapper.getNotPushed(wrapper);
    }
}
