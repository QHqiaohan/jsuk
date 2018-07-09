package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopGoodsFullReduce;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopGoodsFullReduceService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

