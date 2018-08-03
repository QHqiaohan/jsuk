package com.jh.jsuk.controller;

import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.entity.vo.BannerVo;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R showBanners() {
        List<BannerVo> bannerVos = bannerService.listBanners();
        return R.succ(bannerVos);
    }

    @PostMapping("/remove")
    public R removeBanners(@RequestBody Banner banner) {
        banner.setIsValid(0);
        banner.updateById();
        return R.succ();
    }

    @PostMapping("/add")
    public R addBanners(@RequestBody Banner banner) {
        banner.insert();
        return R.succ();
    }
}
