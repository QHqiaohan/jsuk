package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.GoodsBrandService;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private GoodsBrandService goodsBrandService;

    @ApiOperation("用户端-根据类型ID获取品牌列表")
    @GetMapping("/getBrandByCategoryId")
    public Result getBrandByCategoryId(@ApiParam(value = "分类ID", required = true) Integer categoryId) {
        List<GoodsBrand> goodsBrandList = goodsBrandService.selectList(new EntityWrapper<GoodsBrand>()
                .eq(GoodsBrand.ID, categoryId)
                .eq(GoodsBrand.STATUS, 1)
                .orderBy(GoodsBrand.SORT_ORDER, false));
        return new Result().success(goodsBrandList);
    }

    @ApiOperation("用户端-商品类型里的-根据所选品牌ID查询商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    paramType = "query", dataType = "integer"),
    })
    @PostMapping("/getShopGoodsByBrandId")
    public Result getShopGoodsByBrandId(@ApiParam(value = "品牌ID") Integer brandId, Page page) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByBrandId(page, ew, brandId);
        return new Result().success(goodsPage);
    }

}

