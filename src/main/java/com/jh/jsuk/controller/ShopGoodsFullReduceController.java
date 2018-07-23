package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopGoodsFullReduce;
import com.jh.jsuk.envm.FullReduceType;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopGoodsFullReduceService;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 满减活动表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Api(tags = {"商家端-满减活动"})
@RestController
@RequestMapping("/shopGoodsFullReduce")
public class ShopGoodsFullReduceController {

    @Autowired
    private ShopGoodsFullReduceService shopGoodsFullReduceService;

    @Autowired
    private ManagerUserService managerUserService;


    @ApiOperation("商家端-查询满减规则列表")
    @GetMapping("/findByShopId")
    public R findByShopId(Integer shopId, Integer[] goodsId) {
        if (shopId == null)
            return R.err("shopId 为空");
        Wrapper<ShopGoodsFullReduce> wrapper = new EntityWrapper<>();
        wrapper.eq(ShopGoodsFullReduce.SHOP_ID, shopId)
                .eq(ShopGoodsFullReduce.FULL_REDUCE_TYPE, FullReduceType.SHOP.getKey());
        List<ShopGoodsFullReduce> list = shopGoodsFullReduceService.selectList(wrapper);
        if (goodsId == null || goodsId.length == 0)
            return R.succ(list);
        Wrapper<ShopGoodsFullReduce> wrapper1 = new EntityWrapper<>();
        wrapper1.eq(ShopGoodsFullReduce.SHOP_ID, shopId)
                .eq(ShopGoodsFullReduce.FULL_REDUCE_TYPE, FullReduceType.GOODS.getKey())
                .in(ShopGoodsFullReduce.GOODS_ID, goodsId);
        List<ShopGoodsFullReduce> reduces = shopGoodsFullReduceService.selectList(wrapper1);
        shopGoodsFullReduceService.selectList(wrapper1);
        list.addAll(reduces);
        return R.succ(list);
    }

    @ApiOperation("商家端-查询满减")
    @RequestMapping(value = "/getGoodsFR", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getGoodsFR(Integer userId) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
        Integer shopId = managerUser.getShopId();
        List<ShopGoodsFullReduce> fullReduces = shopGoodsFullReduceService.selectList(new EntityWrapper<ShopGoodsFullReduce>()
                .eq(ShopGoodsFullReduce.SHOP_ID, shopId));
        return new Result().success(fullReduces);
    }

    @ApiOperation("商家端-新增满减")
    @RequestMapping(value = "/addGoodsFR", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addGoodsFR(@ModelAttribute ShopGoodsFullReduce shopGoodsFullReduce) {
        shopGoodsFullReduce.insert();
        return new Result().success();
    }

    @ApiOperation("商家端-删除满减")
    @RequestMapping(value = "/delGoodsFR", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delGoodsFR(@ApiParam(value = "满减ID", required = true) Integer id) {
        ShopGoodsFullReduce fullReduce = new ShopGoodsFullReduce();
        fullReduce.setId(id);
        fullReduce.deleteById();
        return new Result().success();
    }

}

