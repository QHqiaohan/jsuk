package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.utils.MD5Util;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:xyl
 * Date:2018/8/1 17:23
 * Description:后台 配送管理
 */
@RestController
@RequestMapping("/distribution")
public class WDistributionUserController {
    @Autowired
    private DistributionUserService distributionUserService;
    @Autowired
    private ExpressService expressService;

    @GetMapping("/list")
    public R list(Page page, String account, String name) {
        return R.succ(distributionUserService.list(page, account, name));
    }

    @PostMapping("/del")
    public R del(Integer id) {
        DistributionUser distributionUser = distributionUserService.selectById(id);
        distributionUser.setCanUse(0);
        distributionUser.updateById();
        return R.succ();
    }

    @GetMapping("/get")
    public R get(Integer id) {
        return R.succ(distributionUserService.selectById(id));
    }

    @PatchMapping
    public R edit(DistributionUser distributionUser) {
        if (distributionUser.getPassword() != null) {
            distributionUser.setPassword(MD5Util.getMD5(distributionUser.getPassword()));
        }
        distributionUser.updateById();
        return R.succ();
    }

    @GetMapping("/orderDetails")
    public R orderDetails(Page page, Integer id) {
        return R.succ(expressService.listOrderByDistributionId(page, id));
    }
}
