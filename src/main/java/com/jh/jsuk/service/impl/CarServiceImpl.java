package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.Car;
import com.jh.jsuk.dao.CarDao;
import com.jh.jsuk.service.CarService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车类别 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarDao, Car> implements CarService {

}
