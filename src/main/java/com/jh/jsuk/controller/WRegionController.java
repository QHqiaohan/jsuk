package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Areas;
import com.jh.jsuk.entity.Citys;
import com.jh.jsuk.entity.Provinces;
import com.jh.jsuk.service.AreasService;
import com.jh.jsuk.service.CitysService;
import com.jh.jsuk.service.ProvincesService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

/**
 * Author:xyl
 * Date:2018/8/3 11:39
 * Description:后台 - 区域设置
 */
@RestController
@RequestMapping("/region")
public class WRegionController {
    @Autowired
    private ProvincesService provincesService;
    @Autowired
    private CitysService citysService;
    @Autowired
    private AreasService areasService;

    @PostMapping("/addProvinces")
    public R addProvinces(String name) {
        Provinces provinces = new Provinces();
        provinces.setProvinceName(name);
        provinces.insert();
        return R.succ();
    }

    @PostMapping("/addCity")
    public R addCity(String name, Integer upperId) {
        Citys city = new Citys();
        city.setCityName(name);
        city.setProvinceId(upperId);
        city.insert();
        return R.succ();
    }

    @PostMapping("/addAreas")
    public R addAreas(String name, Integer upperId) {
        Areas areas = new Areas();
        areas.setAreaName(name);
        areas.setCityId(upperId);
        areas.insert();
        return R.succ();
    }

    @GetMapping("/getProvinces")
    public R getProvinces() {
        return R.succ(provincesService.selectList(new EntityWrapper<Provinces>()
            .where("is_del = 0")));
    }

    @GetMapping("/getCitys")
    public R getCitys(Integer provinceId) {
        return R.succ(citysService.selectList(new EntityWrapper<Citys>()
            .eq(Citys.PROVINCE_ID, provinceId).where("is_del = 0")));
    }

    @GetMapping("/getAreas")
    public R getAreas(Integer cityId) {
        return R.succ(areasService.selectList(new EntityWrapper<Areas>()
            .eq(Areas.CITY_ID, cityId).where("is_del = 0")));
    }
}
