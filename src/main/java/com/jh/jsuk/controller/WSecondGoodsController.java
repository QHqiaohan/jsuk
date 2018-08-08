package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.envm.ShopGoodsStatus;
import com.jh.jsuk.service.ActivityService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Author:xyl
 * Date:2018/8/7 10:54
 * Description: 二手市场
 */
@RestController
@RequestMapping("/secondGoods")
public class WSecondGoodsController {

    @Autowired
    private Session session;

    @Autowired
    private ActivityService activityService;


    @GetMapping("/list")
    public R list(Page page, @RequestParam(required = false) String kw)  {
        return R.succ(activityService.listSecondGoods(page, kw));
    }


    @PostMapping("/del")
    public R del(Integer goodsId) {
        return R.succ("删除成功");
    }


    /**
     * 商品审核
     *
     * @param goodsId
     * @param flag    0 审核不通过 1 审核通过
     * @return
     */
    @PostMapping("/review")
    public R review(Integer goodsId, Integer flag) {
//        //只有平台才能修改
//        if (session.getUserType().getKey() == 4) {
//            ShopGoods shopGoods = shopGoodsService.selectById(goodsId);
//            //只能修改待审核状态的商品
//            if (shopGoods.getStatus() == 0) {
//                shopGoods.setStatus(flag == 1 ? ShopGoodsStatus.UPPER.getKey() : ShopGoodsStatus.CLOSED.getKey());
//                shopGoods.setUpdateTime(new Date());
//                shopGoodsService.updateById(shopGoods);
//                return R.succ("操作成功");
//            }
//            return R.err("该商品已经审核过了");
//        }
        return R.err("权限不足");

    }
}
