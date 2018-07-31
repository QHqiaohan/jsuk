package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserRechargeRecord;
import com.jh.jsuk.entity.vo.UserRechargeRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRechargeRecordDao extends BaseMapper<UserRechargeRecord> {
    List<UserRechargeRecordVo> getRechargeList(Page page, @Param("rechargeId") Integer rechargeId);
}
