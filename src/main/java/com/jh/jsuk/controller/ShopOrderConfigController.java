package com.jh.jsuk.controller;


import com.jh.jsuk.entity.ShopOrderConfig;
import com.jh.jsuk.service.ShopOrderConfigService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单设置 前端控制器
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
@RestController
@RequestMapping("/shopOrderConfig")
public class ShopOrderConfigController {

    @Autowired
    ShopOrderConfigService shopOrderConfigService;

    @GetMapping
    public R get() {
        return R.succ(shopOrderConfigService.getConfig());
    }

    @PutMapping
    public R add(ShopOrderConfig config) throws Exception {
        shopOrderConfigService.setConfig(config);
        return R.succ();
    }

}

