package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.envm.ShopGoodsStatus;
import com.jh.jsuk.service.GoodsEvaluateService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wgoods")
public class WGoodsController {

    @Autowired
    Session session;

    @Autowired
    ShopGoodsService shopGoodsService;

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @GetMapping("/list")
    public R list(Page page, String status, @RequestParam(required = false) String categoryId,
                  @RequestParam(required = false) String kw, @RequestParam(required = false)
                          String brandId) throws Exception {
        ShopGoodsStatus goodsStatus = null;
        if (status != null && !"all".equals(status)) {
            goodsStatus = EnumUitl.toEnum(ShopGoodsStatus.class, status, "getShortKey");
        }
        return R.succ(shopGoodsService.list(page, goodsStatus, categoryId, kw, brandId,session.getShopId()));
    }

    @GetMapping("/listRecycle")
    public R listRecycle(Page page, @RequestParam(required = false) String categoryId,
                  @RequestParam(required = false) String kw, @RequestParam(required = false)
                          String brandId) throws Exception {
        return R.succ(shopGoodsService.listRecycle(page,  categoryId, kw, brandId,session.getShopId()));
    }

    @GetMapping("/listEvaluate")
    public R listEvaluate(Page page, @RequestParam(required = false) String categoryId,
                  @RequestParam(required = false) String kw, @RequestParam(required = false)
                          String brandId,String nickName) throws Exception {
        return R.succ(goodsEvaluateService.listEvaluate(page,  categoryId, kw, brandId,session.getShopId(),nickName));
    }

    @GetMapping("/allCount")
    public R allCount(){
        Map<String,Object> map = new HashMap<>();
        EntityWrapper<ShopGoods> wrapper = new EntityWrapper<>();
        wrapper.ne(ShopGoods.IS_DEL,1)
                .eq(ShopGoods.STATUS,ShopGoodsStatus.WAIT_CONFIRM.getKey());
        int waitConfirm = shopGoodsService.selectCount(wrapper);

        EntityWrapper<ShopGoods> wrapper1 = new EntityWrapper<>();
        wrapper1.ne(ShopGoods.IS_DEL,1)
                .eq(ShopGoods.STATUS,ShopGoodsStatus.UPPER.getKey());
        int upper = shopGoodsService.selectCount(wrapper1);

        EntityWrapper<ShopGoods> wrapper2 = new EntityWrapper<>();
        wrapper2.ne(ShopGoods.IS_DEL,1)
                .eq(ShopGoods.STATUS,ShopGoodsStatus.LOWER.getKey());
        int lower = shopGoodsService.selectCount(wrapper2);

        map.put(ShopGoodsStatus.WAIT_CONFIRM.getShortKey(),waitConfirm);
        map.put(ShopGoodsStatus.UPPER.getShortKey(),upper);
        map.put(ShopGoodsStatus.LOWER.getShortKey(),lower);
        map.put("all",waitConfirm + upper + lower);
        return R.succ(map);
    }


}
