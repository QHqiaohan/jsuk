package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.vo.ShopVisitorVo;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopVisitService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 商家端-店铺访问记录明细 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"店铺访问明细"})
@RestController
@RequestMapping("/shopVisit")
public class ShopVisitController {

    @Autowired
    private ShopVisitService shopVisitService;
    @Autowired
    private ManagerUserService managerUserService;

    @ApiOperation("商家端-查看今日访客列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "userId", value = "商家id", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "today", value = "年月日", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getTodayVisit", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getTodayVisit(Page page, Integer userId, String today) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        today = sdf.format(date);

        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
        Integer shopId = managerUser.getShopId();
        MyEntityWrapper<ShopVisitorVo> ew = new MyEntityWrapper<>();
        Page visitList = shopVisitService.getVisitList(page, ew, shopId, today);
        return new Result().success(visitList);
    }

}

