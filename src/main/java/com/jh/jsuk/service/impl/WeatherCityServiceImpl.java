package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.WeatherCityDao;
import com.jh.jsuk.entity.WeatherCity;
import com.jh.jsuk.service.WeatherCityService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<WeatherCity> getOpenCityList() {
        return baseMapper.getOpenCityList();
    }

    @Override
    public List<WeatherCity> getOpenCityList2() {
        return baseMapper.getOpenCityList2();
    }
}
