package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.service.GoodsBrandService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类型的详细品牌 前端控制器
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@Api(tags = "商品类型-品牌相关操作:")
@RestController
@RequestMapping("/goodsBrand")
public class GoodsBrandController {

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private GoodsBrandService goodsBrandService;

    @GetMapping("/list")
    public R list() {
        Wrapper<GoodsBrand> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsBrand.STATUS, 1);
        return R.succ(goodsBrandService.selectList(wrapper));
    }

}

