package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserRechargeRecord;
import com.jh.jsuk.entity.vo.UserRechargeRecordVo;

public interface UserRechargeRecordService extends IService<UserRechargeRecord> {
    Page<UserRechargeRecordVo> getRechargeList(Page page, Integer rechargeId);
}
