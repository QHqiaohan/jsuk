package com.jh.jsuk.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.vo.UserRechargeRecordVo;
import com.jh.jsuk.service.UserRechargeRecordService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户充值记录
 */
@RestController
@RequestMapping("userRechargeRecord")
public class UserRechargeRecordController {

    @Autowired
    private UserRechargeRecordService userRechargeRecordService;

    @ApiOperation("平台-财务管理-充值记录")
    @RequestMapping(value="getRechargeList",method={RequestMethod.POST,RequestMethod.GET})
    public Result getRechargeList(Page page, Integer rechargeId){
        Page<UserRechargeRecordVo> rechargePage=userRechargeRecordService.getRechargeList(page,rechargeId);
        return new Result().success(rechargePage);
    }

}
