package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/wgoods")
public class WGoodsController {

    @Autowired
    Session session;

    @Autowired
    ShopGoodsService shopGoodsService;

    @GetMapping("/list")
    public R list(Page page, String status, @RequestParam(required = false) String categoryId,
                  @RequestParam(required = false) String search, @RequestParam(required = false)
                          String brandId) throws Exception {
        return R.succ();
    }


}
