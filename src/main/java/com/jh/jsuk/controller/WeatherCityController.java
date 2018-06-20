package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.WeatherCity;
import com.jh.jsuk.service.WeatherCityService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市天气 前端控制器
 * </p>
 *
 * @author xuchuruo
 * @since 2018-06-14
 */
@Api(tags = {"天气相关"})
@RestController
@RequestMapping(value = "/sys/weatherCity", method = {RequestMethod.POST, RequestMethod.GET})
public class WeatherCityController {

    @Autowired
    WeatherCityService weatherCityService;

    @ApiOperation("查询天气信息信息")
    @RequestMapping("weather/query")
    public Result queryWeather(@ApiParam(value = "城市名称 eg:成都", required = true) @RequestParam String countyname) {
        if (StrUtil.isBlank(countyname)) {
            return new Result().erro("参数错误");
        }
        try {
            WeatherCity weatherCity = weatherCityService.selectOne(new MyEntityWrapper<WeatherCity>().eq(WeatherCity.COUNTY_NAME, countyname.trim()));
            if (weatherCity == null) {
                return new Result().erro("暂无城市数据");
            }
            Integer areaId = weatherCity.getAreaId();
            String data = HttpUtil.post(Constant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            Map map = JSONUtil.toBean(data, Map.class);
            String code = String.valueOf(map.get("code"));
            if (StrUtil.equals("200", code)) {
                Object value = map.get("value");
                List<Map> list = JSONUtil.toList((JSONArray) value, Map.class);
                Map<String, Object> res = Maps.newHashMap();
                if (list.size() == 1) {
                    Map map1 = list.get(0);
                    Object realtime = map1.get("realtime");
                    Map map2 = JSONUtil.toBean((JSONObject) realtime, Map.class);
                    Object temp = map2.get("temp");
                    Object weather = map2.get("weather");
                    res.put("temp", temp);
                    res.put("weather", weather);
                }
                return new Result().success("查询成功", res);
            } else {
                return new Result().erro("网络繁忙,请稍后再试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().erro("查询天气失败，请联系网站管理员！");
        }
    }

    @ApiIgnore
    @RequestMapping("weather/set")
    public Result setWeather(String json) {
        try {
            List<WeatherCity> weatherCities = JSONUtil.toList(JSONUtil.parseArray(json), WeatherCity.class);
            weatherCityService.insertOrUpdateBatch(weatherCities, 100);
            return new Result().success("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().erro("查询天气失败，请联系网站管理员！");
        }
    }
}

