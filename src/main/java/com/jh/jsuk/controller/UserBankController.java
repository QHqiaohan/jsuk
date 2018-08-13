package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.UserBank;
import com.jh.jsuk.service.UserBankService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 银行卡 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"银行卡相关:"})
@RestController
@RequestMapping("/bank")
public class UserBankController {
    @Autowired
    private UserBankService bankService;

    @Autowired
    Session session;

    @ApiOperation("用户-添加银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "0商家端  1骑手端  2用户端",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "bankNumber", value = "银行卡号",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "银行名称",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "开户人姓名",
                    required = true, paramType = "query", dataType = "String")
    })
    @PostMapping("/add")
    public Result add(UserBank bank) throws Exception {
        bank.setCreateTime(new Date());
        bank.setUserType(session.lUserType().getKey());
        bank.insert();
        return new Result().success();
    }

    @ApiOperation("用户-修改银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "表id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "bankNumber", value = "银行卡号",
                    required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "银行名称",
                    required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "开户人姓名",
                    required = false, paramType = "query", dataType = "String")
    })
    @PostMapping("/edit")
    public Result edit(UserBank bank) {
        bank.updateById();
        return new Result().success();
    }


    @ApiOperation("用户-删除银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "表id",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @PostMapping("/del")
    public Result del(Integer id) {
        bankService.deleteById(id);
        return new Result().success();
    }

    @ApiOperation("用户-查看自己银行卡列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer")
    })
    @PostMapping("/list")
    public Result list(Page page) throws Exception {
        Page bankPage = bankService.selectPage(page, new EntityWrapper<UserBank>()
                .eq(UserBank.USER_TYPE, session.lUserType().getKey())
                .eq(UserBank.USER_ID, session.lUserId())
                .orderBy(UserBank.CREATE_TIME, false));
        return new Result().success(bankPage);
    }
}

