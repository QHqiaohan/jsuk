package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.service.GoodsEvaluateService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品评价:")
@RestController
@RequestMapping("/goodsEvaluate")
public class GoodsEvaluateController {

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @ApiOperation("获取指定数量的评价数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "count", required = false, value = "数量(默认1)", paramType = "query", dataType = "integer")
    })
    @GetMapping("/get")
    public Result get(Integer goodsId, @RequestParam(defaultValue = "1") Integer count) throws Exception {
        return new Result().success(goodsEvaluateService.get(goodsId, count));
    }

    @ApiOperation("获取评价数量")
    @GetMapping("/count")
    public Result count(Integer goodsId) throws Exception{
        return new Result().success(goodsEvaluateService.count(goodsId));
    }

    @ApiOperation("获取评价分页数据")
    @GetMapping("/list")
    public Result count(Integer goodsId, Page page) throws Exception{
        return new Result().success(goodsEvaluateService.listPage(goodsId,page));
    }


}

