package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.service.CitysService;
import com.jh.jsuk.service.ProvincesService;
import com.jh.jsuk.service.WeatherCityOpenService;
import com.jh.jsuk.service.WeatherCityService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 城市 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "城市相关")
@RestController
@RequestMapping("/cities")
public class CitiesController {

    @Autowired
    CitysService citysService;

    @ApiOperation("根据城市名获取信息")
    @GetMapping("/getCityByName")
    public R getCityByName(@ApiParam String name) {
        EntityWrapper<Citys> wrapper = new EntityWrapper<>();
        wrapper.like(Citys.CITY_NAME, "%" + name + "%");
        return R.succ(citysService.selectOne(wrapper));
    }

    @PutMapping
    public R add(WeatherCityOpen weatherCityOpen){
        weatherCityOpenService.insert(weatherCityOpen);
        return R.succ();
    }

    @PatchMapping
    public R edit(WeatherCityOpen weatherCityOpen){
        weatherCityOpenService.updateById(weatherCityOpen);
        return R.succ();
    }

    @GetMapping
    public R cities(Page page, String kw) {
        return R.succ(citysService.page(page, kw));
    }

    @Autowired
    ProvincesService provincesService;

    @Autowired
    WeatherCityService weatherCityService;

    @Autowired
    WeatherCityOpenService weatherCityOpenService;

    @DeleteMapping
    public R delete(Integer id){
        weatherCityOpenService.deleteById(id);
        return R.succ();
    }

    @GetMapping("/wall")
    public R wall() {
        return R.succ(weatherCityService.selectList(null));
    }

    @GetMapping("/all")
    public R all() {
        List<Provinces> provinces = provincesService.selectList(null);
        List<Citys> cts = citysService.selectList(null);
        List<Province> list = new ArrayList<>();
        for (Provinces province : provinces) {
            Integer provinceId = province.getId();
            Province pvc = new Province(provinceId, province.getAlias());
            list.add(pvc);
            if (provinceId == null)
                continue;
            Iterator<Citys> iterator = cts.iterator();
            while (iterator.hasNext()) {
                Citys next = iterator.next();
                if (provinceId.equals(next.getProvinceId())) {
                    List<City> cities = pvc.getCities();
                    if (cities == null) {
                        cities = new ArrayList<>();
                        pvc.setCities(cities);
                    }
                    cities.add(new City(next.getId(), next.getCityName()));
                    iterator.remove();
                }
            }
        }
        return R.succ(list);
    }

}

