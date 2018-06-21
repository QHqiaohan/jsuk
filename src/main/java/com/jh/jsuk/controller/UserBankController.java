package com.jh.jsuk.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 银行卡 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/userBank")
public class UserBankController {
    @Autowired
    private UserBankService bankService;

    @ApiOperation("添加银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "0商家端  1骑手端  2用户端",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "accountNumber", value = "银行卡号",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "银行名称",
                    required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "开户人姓名",
                    required = true, paramType = "query", dataType = "String")
    })
    @PostMapping("/add")
    public Result add(UserBank bank) {
        bank.insert();
        return new Result().success();
    }

    @ApiOperation("修改银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "表id",
                    required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "accountNumber", value = "银行卡号",
                    required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bankName", value = "银行名称",
                    required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "开户人姓名",
                    required = false, paramType = "query", dataType = "String"),
    })
    @PostMapping("/edit")
    public Result edit(UserBank bank) {
        bank.updateById();
        return new Result().success();
    }


    @ApiOperation("删除银行卡")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "表id",
                    required = true, paramType = "query", dataType = "integer"),
    })
    @PostMapping("/del")
    public Result del(Integer id) {
        bankService.deleteById(id);
        return new Result().success();
    }

    @ApiOperation("查看自己银行卡列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数",
                    required = false, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "0商家端  1骑手端 2用户端",
                    required = false, paramType = "query", dataType = "integer")
    })
    @PostMapping("/list")
    public Result list(Page page, Integer type, Integer userId) {
        Page bankPage = bankService.selectPage(page, new EntityWrapper<UserBank>()
                .eq(UserBank.USER_TYPE, type)
                .eq(UserBank.USER_ID, userId)
                .orderBy(UserBank.CREATE_TIME, false));
        return new Result().success(bankPage);
    }
}

