package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.ShopRushBuyActivity;
import com.jh.jsuk.entity.vo.rushbuy.RushBuyActivityVO;
import com.jh.jsuk.entity.vo.rushbuy.RushBuySizeVo;
import com.jh.jsuk.service.ShopGoodsSizeService;
import com.jh.jsuk.service.ShopRushBuyActivityService;
import com.jh.jsuk.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 秒杀活动 前端控制器
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@Slf4j
@RestController
@RequestMapping("/shopRushBuyActivity")
public class ShopRushBuyActivityController {

    @Autowired
    ShopRushBuyActivityService shopRushBuyActivityService;

    @Autowired
    ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    Session session;

    @GetMapping
    public R page(Page page) {
        Integer shopId = null;
        if (session.isShop()) {
            shopId = session.getShopId();
        }
        return R.succ(shopRushBuyActivityService.page(page, shopId));
    }

    private void update(List<RushBuySizeVo> sizes){
        if (sizes != null && !sizes.isEmpty()) {
            for (RushBuySizeVo size : sizes) {
                ShopGoodsSize sz = new ShopGoodsSize();
                sz.setId(size.getId());
                sz.setKillStock(size.getKillStock());
                sz.setKillPrice(size.getKillPrice() == null ? null : String.valueOf(size.getKillPrice()));
                shopGoodsSizeService.updateById(sz);
            }
        }
    }

    @PutMapping
    public R add(@RequestBody RushBuyActivityVO ent) {
        try{
            shopRushBuyActivityService.insert(ent);
            update(ent.getSizes());
            return R.succ();
        }catch (Exception e){
            return R.err("商品重复");
        }
    }

    @PatchMapping
    public R edit(@RequestBody RushBuyActivityVO ent) {
        shopRushBuyActivityService.updateById(ent);
        update(ent.getSizes());
        return R.succ();
    }

    @DeleteMapping
    public R delete(ShopRushBuyActivity ent) {
        ent.setIsDel(1);
        ent.updateById();
        return R.succ();
    }

    @GetMapping("/get")
    public R get(Integer id) {
        return R.succ(shopRushBuyActivityService.selectVo(id));
    }

}

