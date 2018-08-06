package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.UserAddress;
import com.jh.jsuk.service.UserAddressService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 普通用户地址表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @ApiOperation("后台-用户管理-地址管理-设置默认地址")
    @RequestMapping(value="/setDefaultAddress",method = {RequestMethod.GET,RequestMethod.POST})
    public Result setDefaultAddress(@RequestParam Integer id, @RequestParam Integer isDefault){
        UserAddress userAddress=userAddressService.selectOne(new EntityWrapper<UserAddress>()
                                                                 .eq(UserAddress.ID,id)
        );
        if(userAddress!=null){
            userAddress.setIsDefault(isDefault);
            userAddress.updateById();
            return new Result().success("设置成功");
        }
        return new Result().erro("系统错误");
    }

}

