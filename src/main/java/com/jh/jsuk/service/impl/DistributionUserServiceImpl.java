package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.DistributionUserDao;
import com.jh.jsuk.entity.DistributionDetail;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.dto.MessageDTO;
import com.jh.jsuk.entity.vo.PlatformDistributionUserVo;
import com.jh.jsuk.mq.DjsMessageProducer;
import com.jh.jsuk.service.DistributionDetailService;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.utils.MyEntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 骑手信息 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Slf4j
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

    @Override
    public void addAccount(BigDecimal amount, Integer userId) {
        Integer count = baseMapper.updateAccount(amount, userId);
        log.info("更新行数量:{}", count);
    }

    @Autowired
    DistributionDetailService distributionDetailService;

    @Override
    public BigDecimal getRemainder(Integer distributionUserId) {
        BigDecimal bigDecimal = new BigDecimal("0.00");
        Wrapper<DistributionDetail> wrapper = new EntityWrapper<>();
        wrapper.eq(DistributionDetail.USER_ID, distributionUserId);
        List<DistributionDetail> distributionDetails = distributionDetailService.selectList(wrapper);
        if (distributionDetails == null || distributionDetails.isEmpty())
            return bigDecimal;
        for (DistributionDetail detail : distributionDetails) {
            BigDecimal money = detail.getMoney();
            if (money == null) {
                continue;
            }
            bigDecimal.add(money);
        }
        return bigDecimal;
    }

    @Override
    public Page getDistributionUserList(Page page, Wrapper wrapper) {
        wrapper=SqlHelper.fillWrapper(page,wrapper);

        return page.setRecords(baseMapper.getDistributionUserList(page,wrapper));
    }

    @Override
    public Page searchDistributionUserBy(Page page, Wrapper wrapper, String account, String name) {
        wrapper=SqlHelper.fillWrapper(page,wrapper);
        page.setRecords(baseMapper.searchDistributionUserBy(page,wrapper,account,name));
        return page;
    }

    @Override
    public Page list(Page page, String account, String name) {
        return page.setRecords(baseMapper.list(page,account,name));
    }
}
