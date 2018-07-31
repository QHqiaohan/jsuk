package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserRechargeRecordDao;
import com.jh.jsuk.entity.UserRechargeRecord;
import com.jh.jsuk.entity.vo.UserRechargeRecordVo;
import com.jh.jsuk.service.UserRechargeRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class UserRechargeRecordServiceImpl extends ServiceImpl<UserRechargeRecordDao,UserRechargeRecord> implements UserRechargeRecordService {

    @Override
    public Page<UserRechargeRecordVo> getRechargeList(Page page, Integer rechargeId) {
        List<UserRechargeRecordVo> list=baseMapper.getRechargeList(page,rechargeId);
        page.setRecords(list);
        return page;
    }
}
