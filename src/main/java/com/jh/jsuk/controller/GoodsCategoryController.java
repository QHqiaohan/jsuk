package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品类型相关API:")
@RestController
@RequestMapping(value = "/goodsCategory", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsCategoryController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/list")
    public R list() {
        Wrapper<GoodsCategory> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsCategory.STATUS, 1);
        return R.succ(goodsCategoryService.selectList(wrapper));
    }

}

