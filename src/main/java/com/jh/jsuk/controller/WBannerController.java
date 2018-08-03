package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:xyl
 * Date:2018/8/3 10:24
 * Description:后台 - banner图片管理
 */
@RestController
@RequestMapping("/banner")
public class WBannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping
    public R showBanners(Integer location) {
        return R.succ(bannerService.selectList(new EntityWrapper<Banner>()
            .eq(Banner.BANNER_LOCATION, location)
            .where("is_valid = 1")));
    }

    @PostMapping("/remove")
    public R removeBanners(Integer[] ids) {
        for (Integer id : ids) {
            Banner banner = new Banner();
            banner.setId(id);
            banner.setIsValid(0);
            banner.updateById();
        }
        return R.succ();
    }

    @PostMapping("/add")
    public R addBanners(String[] images, Integer location) {
        for (String image : images) {
            Banner banner = new Banner();
            banner.setBannerLocation(location);
            banner.setImage(image);
            banner.setIsValid(1);
            banner.insert();
        }
        return R.succ();
    }
}
