package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
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
public interface NewsUserService extends IService<NewsUser> {

    /**
     * 获取最多 500条没有推送的消息
     * @return
     */
    List<NewsUser> getNotPushed();

}
