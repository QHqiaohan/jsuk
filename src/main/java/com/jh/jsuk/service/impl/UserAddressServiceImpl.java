package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.dao.UserAddressDao;
import com.jh.jsuk.service.UserAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 普通用户地址表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddress> implements UserAddressService {

}
