package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.dto.ShopGoodsDTO;
import com.jh.jsuk.envm.ShopGoodsStatus;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.service.GoodsEvaluateService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopGoodsSizeService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @Autowired
    GoodsCategoryService goodsCategoryService;

    @GetMapping("/list")
    public R list(Page page, String status, @RequestParam(required = false) String categoryId,
                  @RequestParam(required = false) String kw, @RequestParam(required = false)
                          String brandId) throws Exception {
        ShopGoodsStatus goodsStatus = null;
        if (status != null && !"all".equals(status)) {
            goodsStatus = EnumUitl.toEnum(ShopGoodsStatus.class, status, "getShortKey");
        }
        return R.succ(shopGoodsService.list(page, goodsStatus, categoryId, kw, brandId, session.getShopId()));
    }

    @GetMapping("/listRecycle")
    public R listRecycle(Page page, @RequestParam(required = false) String categoryId,
                         @RequestParam(required = false) String kw, @RequestParam(required = false)
                                 String brandId) throws Exception {
        return R.succ(shopGoodsService.listRecycle(page, categoryId, kw, brandId, session.getShopId()));
    }

    @GetMapping("/listEvaluate")
    public R listEvaluate(Page page, @RequestParam(required = false) String categoryId,
                          @RequestParam(required = false) String kw, @RequestParam(required = false)
                                  String brandId, String nickName) throws Exception {
        return R.succ(goodsEvaluateService.listEvaluate(page, categoryId, kw, brandId, session.getShopId(), nickName));
    }

    @GetMapping("/allCount")
    public R allCount() {
        Map<String, Object> map = new HashMap<>();
        EntityWrapper<ShopGoods> wrapper = new EntityWrapper<>();
        wrapper.ne(ShopGoods.IS_DEL, 1)
                .eq(ShopGoods.STATUS, ShopGoodsStatus.WAIT_CONFIRM.getKey())
                .eq(ShopGoods.SHOP_ID, session.getShopId());
        int waitConfirm = shopGoodsService.selectCount(wrapper);

        EntityWrapper<ShopGoods> wrapper1 = new EntityWrapper<>();
        wrapper1.ne(ShopGoods.IS_DEL, 1)
                .eq(ShopGoods.STATUS, ShopGoodsStatus.UPPER.getKey())
                .eq(ShopGoods.SHOP_ID, session.getShopId());
        int upper = shopGoodsService.selectCount(wrapper1);

        EntityWrapper<ShopGoods> wrapper2 = new EntityWrapper<>();
        wrapper2.ne(ShopGoods.IS_DEL, 1)
                .eq(ShopGoods.STATUS, ShopGoodsStatus.LOWER.getKey())
                .eq(ShopGoods.SHOP_ID, session.getShopId());
        int lower = shopGoodsService.selectCount(wrapper2);

        map.put(ShopGoodsStatus.WAIT_CONFIRM.getShortKey(), waitConfirm);
        map.put(ShopGoodsStatus.UPPER.getShortKey(), upper);
        map.put(ShopGoodsStatus.LOWER.getShortKey(), lower);
        map.put("all", waitConfirm + upper + lower);
        return R.succ(map);
    }

    @PostMapping("/add")
    public R goodsAdd(@RequestBody ShopGoodsDTO dto) {
        dto.setShopId(session.getShopId());
        //待审核
        dto.setStatus(0);
        dto.setIsDel(0);
        dto.setCreateTime(new Date());
        shopGoodsService.insert(dto);
        for (ShopGoodsSize size : dto.getSizes()) {
            size.setShopGoodsId(dto.getId());
            shopGoodsSizeService.insert(size);
        }
        return R.succ();
    }

    @PostMapping("/upper")
    public R upper(Integer goodsId) {
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setId(goodsId);
        shopGoods.setStatus(ShopGoodsStatus.UPPER.getKey());
        shopGoods.setUpdateTime(new Date());
        shopGoods.updateById();
        return R.succ();
    }

    @PostMapping("/lower")
    public R lower(Integer goodsId) {
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setId(goodsId);
        shopGoods.setStatus(ShopGoodsStatus.LOWER.getKey());
        shopGoods.setUpdateTime(new Date());
        shopGoods.updateById();
        return R.succ();
    }

    @PostMapping("/del")
    public R del(Integer goodsId) {
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setId(goodsId);
        shopGoods.setIsDel(1);
        shopGoods.setUpdateTime(new Date());
        shopGoods.updateById();
        return R.succ();
    }

    @PostMapping("/recover")
    public R recover(Integer goodsId) {
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setId(goodsId);
        shopGoods.setIsDel(0);
        shopGoods.setUpdateTime(new Date());
        shopGoods.updateById();
        return R.succ();
    }

    @PostMapping("/totallyDel")
    public R totallyDel(Integer goodsId) {
        ShopGoods shopGoods = shopGoodsService.selectById(goodsId);
        if (shopGoods.getIsDel() != 1) {
            return R.err(-11, "只能删除回收站里面的数据！");
        }
        shopGoodsService.deleteById(goodsId);
        EntityWrapper<ShopGoodsSize> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopGoodsSize.SHOP_GOODS_ID, goodsId);
        shopGoodsSizeService.delete(wrapper);
        return R.succ();
    }

    @GetMapping("/category")
    public R category(Page page, Integer parentId) {
        if(parentId == null)
            parentId = 0;
        EntityWrapper<GoodsCategory> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsCategory.PARENT_ID, parentId);
        return R.succ(goodsCategoryService.selectPage(page, wrapper));
    }

    @PostMapping("/category/add")
    public R categoryAdd(GoodsCategory category){
        category.insert();
        return R.succ();
    }

    @PostMapping("/category/edit")
    public R categoryEdit(GoodsCategory category){
        category.updateById();
        return R.succ();
    }

}
