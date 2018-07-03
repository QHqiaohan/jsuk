package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ShopAttribute;
import com.jh.jsuk.service.ShopAttributeService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺内部的商品分类-属性 前端控制器
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@RestController
@RequestMapping("/shopAttribute")
public class ShopAttributeController {

    @Autowired
    ShopAttributeService shopAttributeService;

    @GetMapping("/list")
    public R list(){
        Wrapper<ShopAttribute> wrapper = new EntityWrapper<>();
        wrapper.orderBy(ShopAttribute.SORT_ORDER,false);
        return R.succ(shopAttributeService.selectList(wrapper));
    }

}

