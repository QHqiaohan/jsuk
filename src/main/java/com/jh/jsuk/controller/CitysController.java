package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Citys;
import com.jh.jsuk.service.CitysService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/citys")
public class CitysController {

    @Autowired
    CitysService citysService;

    @ApiOperation("根据城市名获取信息")
    @GetMapping("/getCityByName")
    public R getCityByName(@ApiParam String name){
        EntityWrapper<Citys> wrapper = new EntityWrapper<>();
        wrapper.like(Citys.CITY_NAME,"%"+name +"%");
        return R.succ(citysService.selectOne(wrapper));
    }

}

