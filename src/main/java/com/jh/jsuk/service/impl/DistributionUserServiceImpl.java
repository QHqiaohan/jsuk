package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.DistributionUserDao;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.dto.MessageDTO;
import com.jh.jsuk.mq.DjsMessageProducer;
import com.jh.jsuk.service.DistributionUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 骑手信息 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class DistributionUserServiceImpl extends ServiceImpl<DistributionUserDao, DistributionUser> implements DistributionUserService {

    @Autowired
    DjsMessageProducer messageProducer;

    @Async
    @Override
    public void notifyRobbing() {
        MessageDTO data = new MessageDTO();
        data.setContent("有新订单啦");
        data.setTitle("");
        Wrapper<DistributionUser> wrapper = new EntityWrapper<>();
        wrapper.eq(DistributionUser.IS_ONLINE, 1);
        List<DistributionUser> list = selectList(wrapper);
        for (DistributionUser user : list) {
            data.setAlias(String.valueOf(user.getId()));
            messageProducer.send(data);
        }
    }
}
