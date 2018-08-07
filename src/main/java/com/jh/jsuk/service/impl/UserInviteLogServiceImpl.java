package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserInviteLogDao;
import com.jh.jsuk.entity.UserInviteLog;
import com.jh.jsuk.entity.vo.UserInviteLogVo;
import com.jh.jsuk.service.UserInviteLogService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-08-07
 */
@Service
public class UserInviteLogServiceImpl extends ServiceImpl<UserInviteLogDao, UserInviteLog> implements UserInviteLogService {

    @Override
    public void addInvite(Integer userId, Integer inviteUserId) {
        UserInviteLog log = new UserInviteLog();
        log.setUserId(userId);
        log.setInviteUserId(inviteUserId);
        log.setMoney(new BigDecimal("1"));
        log.setLog("0");
        log.insert();
    }

    @Override
    public Page getRewardInfo(Page page, Integer userId) {
        return page.setRecords(baseMapper.getRewardInfo(page, userId));
    }

    @Override
    public BigDecimal getTotal(Integer userId) {
        return baseMapper.getTotal(userId);
    }

    @Override
    public Integer getLv1Cnt(Integer userId) {
        EntityWrapper<UserInviteLog> wrapper = new EntityWrapper<>();
        wrapper.eq(UserInviteLog.INVITE_USER_ID, userId);
        return selectCount(wrapper);
    }

    @Override
    public Integer getLv2Cnt(Integer userId) {
        return 0;
    }

    @Override
    public Integer getLv3Cnt(Integer userId) {
        return 0;
    }

    @Override
    public Map<String, Object> get2Count(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lv1", getLv1Cnt(userId));
        map.put("lv2", getLv2Cnt(userId));
        map.put("lv3", getLv3Cnt(userId));
        return map;
    }

    @Override
    public Page getDetail(Page page, Integer userId, String lv) {
        List<UserInviteLogVo> list = null;
        if("lv1".equals(lv)){
            list = baseMapper.lv1Detail(page,userId);
        }else if("lv2".equals(lv)){
            list = baseMapper.lv2Detail(page,userId);
        }else if("lv3".equals(lv)){
            list = baseMapper.lv3Detail(page,userId);
        }
        page.setRecords(list);
        return page;
    }

}
