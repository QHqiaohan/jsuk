package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserOrderLog;
import com.jh.jsuk.dao.UserOrderLogDao;
import com.jh.jsuk.service.UserOrderLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单跟踪日志 服务实现类
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
@Service
public class UserOrderLogServiceImpl extends ServiceImpl<UserOrderLogDao, UserOrderLog> implements UserOrderLogService {

}
