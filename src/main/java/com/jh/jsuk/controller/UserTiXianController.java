package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopMoney;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopMoneyService;
import com.jh.jsuk.service.UserTiXianService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户提现记录 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Api(tags = {"提现"})
@RestController
@RequestMapping("/userTiXian")
public class UserTiXianController {

    @Autowired
    private UserTiXianService userTiXianService;
    @Autowired
    private ShopMoneyService shopMoneyService;
    @Autowired
    private ManagerUserService managerUserService;

    @ApiOperation("提现")
    @RequestMapping(value = "/addTiXian", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addTiXian(@ModelAttribute UserTiXian userTiXian,
                            @ApiParam(value = "2=用户,0=商家,1=骑手", required = true) Integer type,
                            Integer userId) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
        if(managerUser==null){
            return new Result().erro("该商家不存在");
        }
        Integer shopId = managerUser.getShopId();
        if (type == 2) {
            return new Result().success();
        } else if (type == 0) {        //商家提现
            List<ShopMoney> shopMoneyList = shopMoneyService.selectList(new EntityWrapper<ShopMoney>()
                    .eq(ShopMoney.SHOP_ID, shopId));
            if (CollectionUtils.isEmpty(shopMoneyList)) {
                return new Result().erro("账户余额不足");
            } else {
                // 初始化余额
                double sum = 0;
                for (ShopMoney shopMoney : shopMoneyList) {  //计算商家余额
                    // 金额
                    String money = shopMoney.getMoney();
                    //Integer m = Integer.parseInt(money);
                    double m=Double.parseDouble(money);
                    // 消费类型,计算  类型,0=消费,1=收入
                    Integer xfType = shopMoney.getType();
                    if (xfType == 0) {
                        // 消费
                        sum -= m;
                    } else if (xfType == 1) {
                        // 收入
                        sum += m;
                    }
                }
                if (sum >= Double.parseDouble(userTiXian.getPrice())) {
                    userTiXian.insert();
                    return new Result().success("提现成功");
                } else {
                    return new Result().erro("账户余额不足");
                }
            }
        } else if (type == 1) {
            return new Result().success();
        }
        return new Result().erro("参数错误");
    }

}

