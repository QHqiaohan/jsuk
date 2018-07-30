package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopMoney;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopMoneyService;
import com.jh.jsuk.service.UserTiXianService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @ApiOperation("提现")
    @RequestMapping(value = "/addTiXian", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addTiXian(@ModelAttribute UserTiXian userTiXian,
                            @ApiParam(value = "2=用户,0=商家,1=骑手", required = true) Integer type,
                            Integer userId) {
        Result result=userTiXianService.tixian(userTiXian,type,userId);
        return result;
    }


    /**
     * 后台管理系统
     * 财务管理
     */

    @ApiOperation("后台管理系统-财务管理-提现记录")
    @RequestMapping(value="/getTiXianPage",method={RequestMethod.GET,RequestMethod.POST})
    public Result getTiXianPage(Integer current,Integer size){
        current=current==null?1:current;
        size=size==null?10:size;

        Page page=new Page(current,size);

        List<UserTiXianVo> userTiXianList=userTiXianService.selectByAdvance(null,null,null,null);
        return new Result().success(page.setRecords(userTiXianList));
    }

    @ApiOperation("后台管理系统-财务管理-提现记录-高级检索")
    @RequestMapping(value="/advanceSearchTiXianPage",method={RequestMethod.GET,RequestMethod.POST})
    @ApiImplicitParams(value={
        @ApiImplicitParam(name="提现id,流水号",value="tixianId",dataType = "Integer"),
        @ApiImplicitParam(name="金额范围",value="amountScope",dataType = "String"),
        @ApiImplicitParam(name="状态,0=处理中，1=已提现，2=提现失败，3=取消",value="status",dataType = "Integer"),
        @ApiImplicitParam(name="当前页",value="current",dataType = "Integer"),
        @ApiImplicitParam(name="每页显示条数",value="size",dataType = "Integer")
    })
    public Result advanceSearchTiXianPage(Integer tixianId,String amountScope,Integer status,Integer current,Integer size){
        current=current==null?1:current;
        size=size==null?10:size;
        String[] scopes=null;
        Integer begin=null;
        Integer end=null;

        if(amountScope!=null){
            try {
                scopes = amountScope.split("-");    //以-劈开,格式:1000-2000
            }catch(Exception e){
                return new Result().erro("金额范围参数错误");
            }
        }
        begin=Integer.parseInt(scopes[0]);
        end=Integer.parseInt(scopes[1]);

        List<UserTiXianVo> userTiXianList=userTiXianService.selectByAdvance(tixianId,begin,end,status);
        Page page=new Page(current,size);

        return new Result().success(page.setRecords(userTiXianList));
    }
}

