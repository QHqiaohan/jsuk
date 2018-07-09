package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * Banner 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"轮播图Banner:"})
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    @ApiOperation(value = "快递跑腿banner")
    @PostMapping("/expressRun")
    public Result expressRun() {
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                .eq(Banner.BANNER_LOCATION, 11)
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.SORT, false));
        if (bannerList.size() == 0) {
            return new Result().success("还没有banner");
        } else {
            return new Result().success(bannerList);
        }
    }

}

