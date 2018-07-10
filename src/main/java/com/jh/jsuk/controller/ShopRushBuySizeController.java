//package com.jh.jsuk.controller;
//
//
//import com.baomidou.mybatisplus.plugins.Page;
//import com.jh.jsuk.entity.ShopRushBuySize;
//import com.jh.jsuk.service.ShopRushBuySizeService;
//import com.jh.jsuk.utils.R;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * <p>
// * 秒杀配置和商品规格关联表 前端控制器
// * </p>
// *
// * @author lpf
// * @since 2018-06-26
// */
//@RestController
//@RequestMapping("/shopRushBuySize")
//public class ShopRushBuySizeController {
//
//    @Autowired
//    ShopRushBuySizeService shopRushBuySizeService;
//
//    @GetMapping("/page")
//    public R listPage(Page page){
//        return R.succ(shopRushBuySizeService.listPage(page));
//    }
//
//    @PostMapping("/use")
//    public R use(Integer id){
//        ShopRushBuySize siz = new ShopRushBuySize();
//        siz.setId(id);
//        siz.setIsUse(1);
//        siz.updateById();
//        return R.succ();
//    }
//
//    @PostMapping("/notUse")
//    public R notUse(Integer id){
//        ShopRushBuySize siz = new ShopRushBuySize();
//        siz.setId(id);
//        siz.setIsUse(0);
//        siz.updateById();
//        return R.succ();
//    }
//
//    @PostMapping("/add")
//    public R add(ShopRushBuySize size){
//        shopRushBuySizeService.insert(size);
//        return R.succ();
//    }
//
//}
//
