package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.dto.ShopSubmitOrderDto;
import com.jh.jsuk.entity.vo.GoodsVo;
import com.jh.jsuk.entity.vo.ShoppingCartVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserIntegralService userIntegralService;
    @Autowired
    private ShopGoodsFullReduceService shopGoodsFullReduceService;
    @Autowired
    private IntegralRuleService integralRuleService;
    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;
    @Autowired
    private UserOrderService userOrderService;

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
            //更新数据库中的购物车
           // shoppingCartService.updateById(shoppingCart1);
            shoppingCart1.updateById();
        }
        return new Result().success();
    }

    //编辑购物车,只涉及商品数量的增减
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

    /**
     *如果要批量删除可在前端循环调用controller
     */
    @ApiOperation("根据购物车id删除购物车")
    @PostMapping("/del")
    public Result del(@ApiParam(value = "购物车id", required = true) @RequestParam Integer shoppingCartId, Integer userId) {
        shoppingCartService.delete(new MyEntityWrapper<ShoppingCart>()
                                      .eq(ShoppingCart.USER_ID, userId)
                                      .eq(ShoppingCart.ID, shoppingCartId));
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


    //首页-购物车-显示我的购物车列表
    @ApiOperation("显示购物车列表")
    @PostMapping("/list")
    /**
     * goodsName可传可不传
     */
    public Result list(Integer userId, String goodsName) {
        List<ShoppingCartVo> shoppingCarts = shoppingCartService.selectVoList(String.valueOf(userId), goodsName);
/*        for (ShoppingCartVo vo:shoppingCarts) {
            vo.getShopName();//店铺名
            List<GoodsVo> list = vo.getGoods();
            for (GoodsVo gvo:list) {
                gvo.getGoodsName();
                gvo.getSalesPrice();
                gvo.getNum();
            }
        }*/
        if(shoppingCarts==null || shoppingCarts.size()==0){
               return new Result().erro("购物车空空如也");
        }
        return new Result().success(shoppingCarts);
    }

    /*
    给前台返回用户总积分
    给前台返回满减对象
    给前台返回积分抵扣规则
     */
    @ApiOperation("获取用户积分总数、满减对象、积分抵扣规则")
    @RequestMapping(value="/getIntegralAndFullReduce",method={RequestMethod.POST})
    public Result getIntegralAndFullReduce(Integer userId,Integer [] goodsIds){
        //封装返回结果的map
        Map<String,Object> map=new HashMap<>();

        //查询用户总积分
        UserIntegral userIntegral = userIntegralService.selectOne(new EntityWrapper<UserIntegral>()
                                                                     .eq(UserIntegral.USER_ID, userId)
        );
        if(userIntegral==null || userIntegral.getIntegralNumber()==null){
            map.put("jfCount",0);
        }else{
            map.put("jfCount",userIntegral.getIntegralNumber());
        }

        //满减对象Map
        Map<Integer,ShopGoodsFullReduce> reduceMap=new HashMap<>();
        //积分抵扣规则Map
        Map<Integer,IntegralRule> integralMap=new HashMap<>();
        for(Integer goodsId:goodsIds){
            //先根据goodsId查shopId
            Integer shopId=shopGoodsService.selectOne(new EntityWrapper<ShopGoods>().eq(ShopGoods.ID,goodsId)).getShopId();
           //查询满减券对象
            ShopGoodsFullReduce shopGoodsFullReduce = shopGoodsFullReduceService
                                                     .selectOne(new EntityWrapper<ShopGoodsFullReduce>()
                                                                .eq(ShopGoodsFullReduce.SHOP_ID, shopId)
                                                                .eq(ShopGoodsFullReduce.GOODS_ID,goodsId)
            );
            //前台根据goodsId 取对应的满减对象
            reduceMap.put(goodsId,shopGoodsFullReduce);

            /*
                查询积分抵扣规则
             */
            //根据goodsId查询goods_size
            ShopGoodsSize shopGoodsSize = shopGoodsSizeService.selectOne(new EntityWrapper<ShopGoodsSize>()
                                                                        .eq(ShopGoodsSize.SHOP_GOODS_ID, goodsId)
            );
            //查询该商品对应的积分抵扣规则
            IntegralRule interRule = integralRuleService.selectOne(new EntityWrapper<IntegralRule>()
                                                                   .eq(IntegralRule.SHOP_ID,shopId)
                                                                   .eq(IntegralRule.GOODS_SIZE_ID,shopGoodsSize.getId())
            );
            integralMap.put(goodsId,interRule);
        }

        map.put("shopGoodsFullReduceMap",reduceMap);
        map.put("integralMap",integralMap);

        return new Result().success(map);
    }


    /**
     * 用户-购物车-去结算
     * 金额计算（折扣、优惠券、积分来计算订单价格）
     * 计算订单金额
     * 更新用户积分总数
     */
    /**
     *
     * @param userId            用户id
     * @param cartPrice        购物车金额,没有使用满减、优惠券、积分的金额
     * @param orderPrice       订单金额,前台计算的使用满减、优惠券、积分的金额,后台再算一遍
     * @param coupons          可用优惠券数组
     * @param goodsIds         商品id数组
     * @return
     */
    @ApiOperation("用户-购物车-去结算-计算订单金额")
    @RequestMapping(value="/getOrderAmount",method ={RequestMethod.POST})
    public Result getOrderAmount(Integer userId,
                                 double cartPrice,
                                 double orderPrice,
                                 @RequestBody Coupon [] coupons,
                                 Integer goodsIds[]){

        //查询用户总积分
        UserIntegral userIntegral = userIntegralService.selectOne(new EntityWrapper<UserIntegral>()
                                                                 .eq(UserIntegral.USER_ID, userId)
        );
        Integer integralNum=userIntegral.getIntegralNumber();   //积分总数量

        for(Integer goodsId:goodsIds){
            //先根据goodsId查shopId
            Integer shopId=shopGoodsService.selectOne(new EntityWrapper<ShopGoods>().eq(ShopGoods.ID,goodsId)).getShopId();
            //查询满减券对象
            ShopGoodsFullReduce shopGoodsFullReduce = shopGoodsFullReduceService
                    .selectOne(new EntityWrapper<ShopGoodsFullReduce>()
                            .eq(ShopGoodsFullReduce.SHOP_ID, shopId)
                            .eq(ShopGoodsFullReduce.GOODS_ID,goodsId)
                    );


        }


        if(integralNum>0){

        }

        return null;
    }

    public Result getMoney(List<ShopSubmitOrderDto> orderDto) {
        BigDecimal money = null;
        try {
            money = userOrderService.orderPrice(orderDto);
            return new Result().success(money);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().erro("出错啦");
        }

    }
}

