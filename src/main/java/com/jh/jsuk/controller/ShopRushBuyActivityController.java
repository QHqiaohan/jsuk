package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.service.ShopRushBuyActivityService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@RestController
@RequestMapping("/shopRushBuyActivity")
public class ShopRushBuyActivityController {

    @Autowired
    ShopRushBuyActivityService shopRushBuyActivityService;

    @Autowired
    Session session;

    @GetMapping
    public R page(Page page){
        Integer shopId = null;
        if(session.isShop()){
            shopId = session.getShopId();
        }
        return R.succ(shopRushBuyActivityService.page(page,shopId));
    }

    @PutMapping
    public R add(ShopRushBuyActivity ent){
        ent.insert();
        return R.succ();
    }

    @PatchMapping
    public R edit(ShopRushBuyActivity ent){
        ent.updateById();
        return R.succ();
    }

    @DeleteMapping
    public R delete(ShopRushBuyActivity ent){
        ent.setIsDel(1);
        ent.updateById();
        return R.succ();
    }

    @GetMapping("/get")
    public R get(Integer id){
        return R.succ(shopRushBuyActivityService.selectVo(id));
    }

}

