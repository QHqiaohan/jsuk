package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.service.ActivityService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:xyl
 * Date:2018/8/7 10:54
 * Description: 后台 - 二手市场
 */
@RestController
@RequestMapping("/secondGoods")
public class WSecondGoodsController {

    @Autowired
    private ActivityService activityService;


    @GetMapping("/list")
    public R list(Page page, @RequestParam(required = false) String kw) {
        return R.succ(activityService.listSecondGoods(page, kw));
    }

    @GetMapping("/list2")
    public R list2(Page page, @RequestParam(required = false) String kw) {
        return R.succ(activityService.listSecondGoods2(page, kw));
    }


    @PostMapping("/del")
    public R del(Integer id) {
        Activity activity = new Activity();
        activity.setIsDel(1);
        activity.setId(id);
        activity.updateById();
        return R.succ("删除成功");
    }


    @PostMapping("/review")
    public R review(Integer id) {
        Activity activity = new Activity();
        activity.setExamine(1);
        activity.setId(id);
        activity.updateById();
        return R.succ("操作成功");
    }
}
