package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.DistributionApplyVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.service.DistributionApplyService;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.UserTiXianService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


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
    private DistributionApplyService distributionApplyService;
    @Autowired
    private DistributionUserService distributionUserService;

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

    @GetMapping("advanceSearchTiXianPage")
    public Result advanceSearchTiXianPage(Page page,
                                          @RequestParam(required = false) Integer tixianId,
                                          @RequestParam(required = false) String amountScope,
                                          @RequestParam(required = false) Integer status){
        String[] scopes=null;
        Integer begin=null;
        Integer end=null;

        if(amountScope!=null){
            try {
                scopes = amountScope.split("-");    //以-劈开,格式:1000-2000
            }catch(Exception e){
                return new Result().erro("金额范围参数错误");
            }
            begin=Integer.parseInt(scopes[0]);
            try{
                end=Integer.parseInt(scopes[1]);
            }catch(Exception e){
                end=null;
            }
        }
        Page<UserTiXianVo> userTiXianPage=userTiXianService.selectByAdvance(page,tixianId,begin,end,status);
        return new Result().success(userTiXianPage);
    }


    //平台-提现审核
    //@GetMapping("tiXianExamine")
    @ApiOperation("平台-提现记录-用户提现审核")
    @RequestMapping(value="tiXianExamine",method={RequestMethod.POST,RequestMethod.GET})
    public Result tiXianExamine(@RequestParam Integer tiXianId){
        UserTiXian userTiXian=userTiXianService.selectOne(new EntityWrapper<UserTiXian>()
                                                              .eq(UserTiXian.ID,tiXianId)
        );
        if(userTiXian==null){
            return new Result().erro("参数错误");
        }
        userTiXian.setExamine(1);
        userTiXian.updateById();

        return new Result().success("提现审核成功");
    }


    //平台-骑手提现列表
    @RequestMapping(value="searchDistributionUserTiXian",method={RequestMethod.POST,RequestMethod.GET})
    public Result searchDistributionUserTiXian(Page page){
           EntityWrapper<DistributionApply> ew=new EntityWrapper<>();
           Page distributionUserTiXianPage=distributionApplyService.searchDistributionUserTiXian(page,ew);
           return new Result().success(distributionUserTiXianPage);
    }

    //平台-骑手提现审核
    @RequestMapping(value="distributionTiXianExamine",method={RequestMethod.POST})
    public Result distributionTiXianExamine(@RequestParam Integer distributionApplyId,
                                            @RequestParam Integer status){
        DistributionApply distributionApply = distributionApplyService.selectById(distributionApplyId);
        if(distributionApply==null){
            return new Result().erro("系统错误");
        }
        distributionApply.setStatus(status);
        distributionApply.updateById();
        if(status==1){
            //审核通过
            Integer distributionUserId=distributionApply.getUserId();   //骑手Id
            DistributionUser distributionUser=distributionUserService.selectById(distributionUserId);
            if(distributionUser==null){
                return new Result().erro("系统错误");
            }
            if(distributionApply.getPoundage()==null){
                distributionApply.setPoundage(new BigDecimal(0));
            }
            if(distributionApply.getMoney()==null){
                distributionApply.setMoney(new BigDecimal(0));
            }
            BigDecimal account = distributionUser.getAccount().add(distributionApply.getMoney()).subtract(distributionApply.getPoundage());
            distributionUser.setAccount(account);
            distributionUser.updateById();
            return new Result().success("审核通过");
        }
        return new Result().erro("审核拒绝");

    }
}

