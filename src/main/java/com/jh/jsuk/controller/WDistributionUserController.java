package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.utils.MD5Util;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public R list(Page page, String account, Integer status, String name) {
        return R.succ(distributionUserService.list(page, account, status, name));
    }

    @PostMapping("/review")
    public R review(Integer id) {
        DistributionUser distributionUser = distributionUserService.selectById(id);
        distributionUser.setStatus(1);
        distributionUser.updateById();
        return R.succ();
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

    @GetMapping("/allCount")
    public R allCount() {
        Map<String, Object> map = new HashMap<>();
        EntityWrapper<DistributionUser> wrapper = new EntityWrapper<>();
        wrapper.ne(DistributionUser.CAN_USE, 0)
            .eq(DistributionUser.STATUS, 0);
        int waitConfirm = distributionUserService.selectCount(wrapper);

        EntityWrapper<DistributionUser> wrapper1 = new EntityWrapper<>();
        wrapper.ne(DistributionUser.CAN_USE, 0)
            .eq(DistributionUser.STATUS, 1);
        int confirmPass = distributionUserService.selectCount(wrapper1);

        EntityWrapper<DistributionUser> wrapper2 = new EntityWrapper<>();
        wrapper.ne(DistributionUser.CAN_USE, 0);
        int all = distributionUserService.selectCount(wrapper2);

        map.put("wcm", waitConfirm);
        map.put("all", all);
        map.put("cmp", confirmPass);
        return R.succ(map);
    }
}
