package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.WeatherCity;

import java.util.List;

/**
 * <p>
 * 城市天气 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface WeatherCityService extends IService<WeatherCity> {

    List<WeatherCity> getOpenCityList();

    List<WeatherCity> getOpenCityList2();

}
