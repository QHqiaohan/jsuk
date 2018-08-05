package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.service.ModularPortalService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Author:xyl
 * Date:2018/8/3 10:49
 * Description:专题管理
 */
@RestController
@RequestMapping("/modularPortal")
public class WModularPortalController {
    @Autowired
    private ModularPortalService modularPortalService;

    @PostMapping("/add")
    public R add(@RequestBody ModularPortal portal) {
//        portal.setLogo(logo);
//        portal.setParentId(parentId);
        portal.setStatus(1);
        portal.setPublishTime(new Date());
//        portal.setName(name);
        portal.insert();
        return R.succ();
    }

    @PostMapping("/remove")
    public R remove(@RequestBody ModularPortal modularPortal) {
        modularPortal.setStatus(0);
        modularPortal.setUpdateTime(new Date());
        modularPortal.updateById();
        return R.succ();
    }

    @PatchMapping("/update")
    public R update(ModularPortal modularPortal) {
        modularPortal.updateById();
        return R.succ();
    }

    @GetMapping
    public R list() {
        return R.succ(modularPortalService.list());
    }
}
