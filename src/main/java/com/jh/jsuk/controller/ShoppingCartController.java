package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ShoppingCart;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShoppingCartService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@Api(tags = "购物车")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ManagerUserService managerUserService;
    @Autowired
    UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("加入购物车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "shopId", value = "店铺id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsId", value = "商品id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "sizeId", value = "规格id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "num", value = "数量",
                    required = true, paramType = "query", dataType = "integer")
    })
    @PostMapping("/add")
    public Result add(@ModelAttribute ShoppingCart shoppingCart, Integer userId) {
        ShoppingCart shoppingCart1 = shoppingCartService.selectOne(new EntityWrapper<ShoppingCart>()
                .eq(ShoppingCart.USER_ID, userId)
                .eq(ShoppingCart.SHOP_ID, shoppingCart.getShopId())
                .eq(ShoppingCart.GOODS_ID, shoppingCart.getGoodsId()));
        if (shoppingCart1 == null) {
            shoppingCart.insert();
        } else {
            shoppingCart1.setNum(shoppingCart1.getNum() + 1);
        }
        return new Result().success();
    }

    @ApiOperation("修改购物车")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "购物车id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "num", value = "数量",
                    required = true, paramType = "query", dataType = "integer")
    })
    @PostMapping("/edit")
    public Result edit(ShoppingCart shoppingCart) {
        if (shoppingCart.getNum() == 0) {
            shoppingCart.deleteById();
        } else {
            shoppingCart.updateById();
        }
        return new Result().success();
    }

    @ApiOperation("根据购物车id删除购物车")
    @PostMapping("/del")
    public Result del(@ApiParam(value = "购物车id", required = true) @RequestParam Integer shoppingCartId, Integer userId) {
        shoppingCartService.delete(new MyEntityWrapper<ShoppingCart>().eq(ShoppingCart.USER_ID, userId).eq(ShoppingCart.ID, shoppingCartId));
        return new Result().success();
    }

    @ApiOperation("获取用户购物车数量")
    @PostMapping("/getUserCartCount")
    public Result getUserCartCount(@ApiParam(value = "用户id", required = true) @RequestParam Integer userId) {
        int count = shoppingCartService.selectCount(new MyEntityWrapper<ShoppingCart>().eq(ShoppingCart.USER_ID, userId));
        return new Result().success("获取购物车数量成功", count);
    }

    @ApiOperation("根据购shopId goodsId userId删除")
    @PostMapping("/del2")
    public Result del2(@ApiParam(value = "店铺id", required = true) @RequestParam Integer shopId,
                       @ApiParam(value = "商品id", required = true) @RequestParam Integer goodsId,
                       Integer userId) {
        shoppingCartService.delete(new EntityWrapper<ShoppingCart>()
                .eq(ShoppingCart.SHOP_ID, shopId)
                .eq(ShoppingCart.GOODS_ID, goodsId)
                .eq(ShoppingCart.USER_ID, userId));
        return new Result().success();
    }

    @ApiOperation("显示购物车列表")
    @PostMapping("/list")
    public Result list(Integer userId) {
        List<ShoppingCartVo> shoppingCarts = shoppingCartService.selectVoList(String.valueOf(userId));
        return new Result().success(shoppingCarts);
    }
}

