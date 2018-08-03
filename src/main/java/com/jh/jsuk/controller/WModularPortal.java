package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ModularPortal;
import com.jh.jsuk.service.ModularPortalService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Author:xyl
 * Date:2018/8/3 10:49
 * Description:专题管理
 */
@RestController
@RequestMapping("/modularPortal")
public class WModularPortal {
    @Autowired
    private ModularPortalService modularPortalService;

    @PostMapping("/add")
    public R add(String[] images, Integer parentId, String name) {
        for (String logo : images) {
            ModularPortal portal = new ModularPortal();
            portal.setLogo(logo);
            portal.setParentId(parentId);
            portal.setStatus(1);
            portal.setPublishTime(new Date());
            portal.setName(name);
            portal.insert();
        }
        return R.succ();
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        ModularPortal modularPortal = new ModularPortal();
        modularPortal.setId(id);
        modularPortal.setStatus(0);
        modularPortal.updateById();
        return R.succ();
    }

    @PostMapping("/update")
    public R update(Integer id, String name) {
        ModularPortal modularPortal = new ModularPortal();
        modularPortal.setId(id);
        modularPortal.setName(name);
        modularPortal.updateById();
        return R.succ();
    }

    @GetMapping
    public R list(Integer parentId) {
        return R.succ(modularPortalService.selectList(new EntityWrapper<ModularPortal>()
            .eq(ModularPortal.PARENT_ID, parentId)
            .where("status = 1")));
    }
}
