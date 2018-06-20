package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.Log;
import com.jh.jsuk.dao.LogDao;
import com.jh.jsuk.service.LogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

}
