package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.ActivityTransactionArea;

/**
 * <p>
 * 便捷生活-交易区域 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ActivityTransactionAreaDao extends BaseMapper<ActivityTransactionArea> {

    ActivityTransactionArea findAreaById(Integer id);

}
