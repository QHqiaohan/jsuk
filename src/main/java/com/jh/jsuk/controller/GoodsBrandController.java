package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.service.GoodsBrandService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

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
    private GoodsBrandService goodsBrandService;

    @GetMapping("/list")
    public R list() {
        Wrapper<GoodsBrand> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsBrand.STATUS, 1);
        return R.succ(goodsBrandService.selectList(wrapper));
    }

    @GetMapping("/page")
    public R list(Page page, String kw) {
        Wrapper<GoodsBrand> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsBrand.STATUS, 1);
        return R.succ(goodsBrandService.selectMPage(page, kw, wrapper));
    }
    @ApiOperation("用户端-根据类型ID获取品牌列表")
    @RequestMapping(value = "/getBrandByCategoryId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getBrandByCategoryId(@ApiParam(value = "分类ID", required = true) Integer categoryId) {
        List<GoodsBrand> goodsBrandList = goodsBrandService.selectList(new EntityWrapper<GoodsBrand>()
                .eq(GoodsBrand.CATEGORY_ID, categoryId)
                .eq(GoodsBrand.STATUS, 1)
                .orderBy(GoodsBrand.SORT_ORDER, false));
        return new Result().success(goodsBrandList);
    }

    @PostMapping("/add")
    public R add(GoodsBrand goodsBrand){
        goodsBrand.insert();
        return R.succ();
    }

    @PostMapping("/edit")
    public R edit(GoodsBrand goodsBrand){
        goodsBrand.updateById();
        return R.succ();
    }
    /*@ApiOperation("用户端-商品类型里的-根据所选品牌ID查询商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/getShopGoodsByBrandId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsByBrandId(@ApiParam(value = "品牌ID") Integer brandId, Page page) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByBrandId(page, ew, brandId);
        return new Result().success(goodsPage);
    }*/
    @ApiOperation("商家端-根据类型ID获取品牌列表")
    @RequestMapping(value = "/getBrandList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getBrandList() {
        List<GoodsBrand> goodsBrandList = goodsBrandService.selectList(new EntityWrapper<GoodsBrand>()
                .eq(GoodsBrand.STATUS, 1)
                .orderBy(GoodsBrand.SORT_ORDER, false));
        return new Result().success(goodsBrandList);
    }

}

