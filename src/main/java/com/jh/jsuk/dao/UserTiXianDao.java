package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户提现记录 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
public interface UserTiXianDao extends BaseMapper<UserTiXian> {

    List<UserTiXianVo> selectByAdvance(@Param("tixianId") Integer tixianId,
                                       @Param("begin") Integer begin,
                                       @Param("end") Integer end,
                                       @Param("status") Integer status);
}
