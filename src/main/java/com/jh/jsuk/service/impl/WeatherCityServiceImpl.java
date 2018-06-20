package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.WeatherCity;
import com.jh.jsuk.dao.WeatherCityDao;
import com.jh.jsuk.service.WeatherCityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 城市天气 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class WeatherCityServiceImpl extends ServiceImpl<WeatherCityDao, WeatherCity> implements WeatherCityService {

}
