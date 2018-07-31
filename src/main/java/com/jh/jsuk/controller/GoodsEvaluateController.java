package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.dto.GoodsEvaluateDto;
import com.jh.jsuk.service.GoodsEvaluateService;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
@RequestMapping(value = "/goodsEvaluate", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsEvaluateController {

    @Autowired
    GoodsEvaluateService goodsEvaluateService;

    @ApiOperation("用户-获取指定数量的评价数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "count", value = "数量(默认1)", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/get", method = {RequestMethod.POST, RequestMethod.GET})
    public Result get(Integer goodsId, @RequestParam(defaultValue = "1") Integer count) throws Exception {
        return new Result().success(goodsEvaluateService.get(goodsId, count));
    }

    @ApiOperation("用户-获取评价数量")
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public Result count(Integer goodsId) throws Exception {
        return new Result().success(goodsEvaluateService.count(goodsId));
    }

    @ApiOperation("用户-各类型评价数量")
    @RequestMapping(value = "/counts", method = {RequestMethod.POST, RequestMethod.GET})
    public Result counts(Integer goodsId) throws Exception {
        return new Result().success(goodsEvaluateService.counts(goodsId));
    }

    @ApiOperation("用户-获取评价分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品id", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "评价类型 全部:all(默认),好评:gd 中评:mdm  差评:ngt",
                    paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public Result count(Integer goodsId, @RequestParam(defaultValue = "all") String type, Page page) throws Exception {
        return new Result().success(goodsEvaluateService.listPage(goodsId, type, page));
    }

    @ApiOperation("用户端-添加商品评价")
    @RequestMapping(value = "/addEvaluate", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addEvaluate(@RequestBody ArrayList<GoodsEvaluateDto> list) {
        if(list == null || list.isEmpty())
            return R.err("没数据");
        for (GoodsEvaluateDto dto : list) {
            Integer goodsStar = dto.getGoodsStar();
            Integer sendStar = dto.getSendStar();
            Integer serviceStar = dto.getServiceStar();
            if (goodsStar != null && sendStar != null && serviceStar != null) {
                int fullStar = (goodsStar + sendStar + serviceStar) / 3;
                dto.setStarNumber(fullStar);
            } else {
                dto.setStarNumber(5);
            }
            goodsEvaluateService.insert(dto);
        }
        return new Result().success("添加成功");
    }


}

