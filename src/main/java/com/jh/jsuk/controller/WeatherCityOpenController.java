package com.jh.jsuk.controller;


import com.jh.jsuk.entity.WeatherCity;
import com.jh.jsuk.service.WeatherCityService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 区域开通 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Api(tags = "地址定位选择API:")
@RestController
@RequestMapping("/weatherCityOpen")
public class WeatherCityOpenController {

    @Autowired
    private WeatherCityService weatherCityService;

    @ApiOperation("用户端-获取已开通城市列表")
    @RequestMapping(value = "/getOpenCityList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getOpenCityList() {
        List<WeatherCity> weatherCityVoList = weatherCityService.getOpenCityList();
        return new Result().success(weatherCityVoList);
    }

//    /**
//     * 新增开通城市
//     *
//     * @param weatherCityId
//     * @return
//     */
//    @ApiIgnore
//    @RequestMapping(value = "/addOpenCityList", method = {RequestMethod.POST, RequestMethod.GET})
//    public Result addOpenCityList(Integer weatherCityId) {
//        WeatherCityOpen weatherCityOpen = new WeatherCityOpen();
//        weatherCityOpen.setWeatherCityId(weatherCityId);
//        weatherCityOpen.insert();
//        return new Result().success();
//    }

//    /**
//     * 取消开通城市
//     *
//     * @param id
//     * @return
//     */
//    @ApiIgnore
//    @RequestMapping(value = "/cancelCityList", method = {RequestMethod.POST, RequestMethod.GET})
//    public Result cancelCityList(Integer id) {
//        WeatherCityOpen weatherCityOpen = new WeatherCityOpen();
//        weatherCityOpen.setWeatherCityId(id);
//        weatherCityOpen.setStatus(0);
//        weatherCityOpen.updateById();
//        return new Result().success();
//    }

//    /**
//     * 删除开通城市
//     *
//     * @param id
//     * @return
//     */
//    @ApiIgnore
//    @RequestMapping(value = "/delOpenCityList", method = {RequestMethod.POST, RequestMethod.GET})
//    public Result delOpenCityList(Integer id) {
//        WeatherCityOpen weatherCityOpen = new WeatherCityOpen();
//        weatherCityOpen.setWeatherCityId(id);
//        weatherCityOpen.deleteById();
//        return new Result().success();
//    }
}

