package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.service.UserAddressService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiao
 * @since 2017-11-10
 */
@Api(tags = {"收货地址相关操作API:"})
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private UserAddressService addressService;

    @ApiOperation("添加地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", required = true, value = "姓名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "gender", required = true, value = "性别", paramType = "query", dataType = "int", allowableValues = "range[0,1]"),
            @ApiImplicitParam(name = "longitude", required = true, value = "经度", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "latitude", required = true, value = "纬度", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "address", required = true, value = "地址名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "homeNum", required = true, value = "门牌号", paramType = "query", dataType = "string"),
    })
    @PostMapping("/add")
    public Result add(@ModelAttribute UserAddress address) {
        int count = addressService.selectCount(new EntityWrapper<UserAddress>()
                .eq(UserAddress.USER_ID, address.getUserId())
                .eq(UserAddress.IS_DEFAULT, 1)
                .eq(UserAddress.IS_DEL, 0));
        if (count == 0) {
            address.setIsDefault(1);
        }
        address.setIsDel(0);
        address.insert();
        return new Result().success();
    }

    @ApiOperation("修改地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "地址id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", required = true, value = "姓名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "gender", required = true, value = "性别", paramType = "query", dataType = "int", allowableValues = "range[0,1]"),
            @ApiImplicitParam(name = "longitude", required = true, value = "经度", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "latitude", required = true, value = "纬度", paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "address", required = true, value = "地址名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "homeNum", required = true, value = "门牌号", paramType = "query", dataType = "string"),
    })
    @PostMapping("/edit")
    public Result edit(@ModelAttribute UserAddress address) {
        address.updateById();
        return new Result().success();
    }

    @ApiOperation("删除地址")
    @PostMapping("/del")
    public Result del(@ApiParam(value = "地址id", required = true) @RequestParam Integer addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setIsDel(1);
        address.updateById();
        return new Result().success();
    }

    @ApiOperation("设置默认地址")
    @PostMapping("/setDefault")
    public Result setDefault(@ApiParam(value = "地址id", required = true) @RequestParam Integer addressId, Integer userId) {
        List<UserAddress> addresses = addressService.selectList(new EntityWrapper<UserAddress>()
                .eq(UserAddress.USER_ID, userId)
                .eq(UserAddress.IS_DEFAULT, 1)
                .eq(UserAddress.IS_DEL, 0)
                .ne(UserAddress.ID, addressId));
        for (UserAddress ads : addresses) {
            ads.setIsDefault(0);
            ads.updateById();
        }
        UserAddress address = addressService.selectById(addressId);
        address.setIsDefault(1);
        address.updateById();
        return new Result().success();
    }

    @ApiOperation("显示用户地址列表")
    @PostMapping("/list")
    public Result list(Integer userId) {
        List<UserAddress> addresses = addressService.selectList(new EntityWrapper<UserAddress>()
                .eq(UserAddress.USER_ID, userId)
                .eq(UserAddress.IS_DEL, 0)
                .orderBy(UserAddress.IS_DEFAULT, false));
        return new Result().success(addresses);
    }

    @ApiOperation("获取用户默认地址")
    @PostMapping("/getDefault")
    public Result getDefalut(Integer userId) {
        UserAddress address = addressService.selectOne(new EntityWrapper<UserAddress>()
                .eq(UserAddress.USER_ID, userId)
                .eq(UserAddress.IS_DEL, 0)
                .eq(UserAddress.IS_DEFAULT, 1));
        return new Result().success(address);
    }

}
