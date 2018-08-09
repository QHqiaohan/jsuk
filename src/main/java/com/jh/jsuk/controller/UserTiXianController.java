package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.ShopUserTiXianVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRechargeRecordService userRechargeRecordService;
    @Autowired
    private UserInviteLogService userInviteLogService;

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
     * 商家提现
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
        Page<ShopUserTiXianVo> userTiXianPage=userTiXianService.selectByAdvance(page,tixianId,begin,end,status);
        return new Result().success(userTiXianPage);
    }



    /**
     * 后台管理系统
     * 财务管理
     * 用户提现
     */
    @GetMapping("advanceSearchUserTiXianPage")
    public Result advanceSearchUserTiXianPage(Page page,
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
        Page<UserTiXianVo> userTiXianPage=userTiXianService.selectByAdvance2(page,tixianId,begin,end,status);
        return new Result().success(userTiXianPage);
    }

    //平台-商家端提现审核
    @PostMapping("/shopTiXianExamine")
    public Result shopTiXianExamine(@RequestParam Integer tiXianId,@RequestParam Integer shopManagerId){
        UserTiXian userTiXian=userTiXianService.selectOne(new EntityWrapper<UserTiXian>()
                                                          .eq(UserTiXian.ID,tiXianId)
                                                          .eq(UserTiXian.MANAGER_ID,shopManagerId)
        );
        if(userTiXian==null){
            return new Result().erro("提现数据不存在");
        }
        userTiXian.setExamine(1);
        ManagerUser managerUser=managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                                                             .eq(ManagerUser.ID,shopManagerId)
                                                             .eq(ManagerUser.USER_TYPE,2)   //1平台,2商家
                                                             .eq(ManagerUser.CAN_USE,1)
        );
        if(managerUser==null){
            return new Result().erro("商家不存在");
        }
        Integer shopId=managerUser.getShopId();   //店铺id

        //商家交易,通过这张表计算商家余额,type=0表示消费,1表示收入
        ShopMoney shopMoney=new ShopMoney();
        shopMoney.setShopId(shopId);
        shopMoney.setMoney(userTiXian.getPrice());
        shopMoney.setType(0);
        shopMoney.insert();

        Shop shop=shopService.selectOne(new EntityWrapper<Shop>()
                                        .eq(Shop.ID,shopId)
        );
        BigDecimal shopAccount=shop.getAccountPoint();
        shopAccount=shopAccount.subtract(new BigDecimal(userTiXian.getPrice()));
        shop.setAccountPoint(shopAccount);

        userTiXian.updateById();
        shop.updateById();

        return new Result().success("审核成功");
    }


    //平台-用户提现审核
    //@GetMapping("tiXianExamine")
    @ApiOperation("平台-提现记录-用户提现审核")
    @RequestMapping(value="tiXianExamine",method={RequestMethod.POST,RequestMethod.GET})
    public Result tiXianExamine(@RequestParam Integer tiXianId,@RequestParam Integer userId){
        UserTiXian userTiXian=userTiXianService.selectOne(new EntityWrapper<UserTiXian>()
                                                              .eq(UserTiXian.ID,tiXianId)
        );
        if(userTiXian==null){
            return new Result().erro("提现记录数据不存在");
        }
        userTiXian.setExamine(1);

        User user=userService.selectOne(new EntityWrapper<User>()
                                        .eq(User.ID,userId)
        );
        if(user==null){
            return new Result().erro("用户不存在");
        }
        //查询用户红包记录,用户只有红包才能提现
        List<UserInviteLog> userInviteLogList=userInviteLogService.selectList(new EntityWrapper<UserInviteLog>()
                                                                              .eq(UserInviteLog.USER_ID,userId)
                                                                              .eq(UserInviteLog.IS_TIXIAN,0)  //1:已经提现,0未提现
        );
        if(userInviteLogList==null || userInviteLogList.size()==0){
            return new Result().erro("没有可以提现的金额");
        }
        //获取提现金额
        BigDecimal price=new BigDecimal(userTiXian.getPrice());
        BigDecimal tixian_account=new BigDecimal("0.00");  //提现红包金额
        for(UserInviteLog inviteLog:userInviteLogList){
            tixian_account=tixian_account.add(inviteLog.getMoney());
        }
        if(price.compareTo(tixian_account)==1){   //提现金额大于红包金额
            return new Result().erro("红包金额不足");
        }
        //记录最终的提现金额
        BigDecimal final_account=new BigDecimal("0.00");
        for(UserInviteLog inviteLog:userInviteLogList){
            if(price.compareTo(inviteLog.getMoney())==1 || price.compareTo(inviteLog.getMoney())==0){
                price=price.subtract(inviteLog.getMoney());
                final_account=final_account.add(inviteLog.getMoney());
                inviteLog.setIsTixian(1);   //该红包已提现
                inviteLog.updateById();
            }
            if(price.compareTo(inviteLog.getMoney())==-1){
                break;
            }
        }
        UserRemainder userRemainder=new UserRemainder();
        userRemainder.setUserId(userId);
        userRemainder.setType(-1);   //-1代表消费
        userRemainder.setRemainder(final_account);   //金额
        userRemainder.setIdDel(0);
        userRemainder.setCreateTime(new Date());
        userRemainder.insert();

        userTiXian.updateById();
        Map map=new HashMap<>();
        map.put("account",final_account);
        map.put("msg","success");
        return new Result().success(map);
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
            BigDecimal account = distributionUser.getAccount().subtract(distributionApply.getMoney()).subtract(distributionApply.getPoundage());
            distributionUser.setAccount(account);
            distributionUser.updateById();
            return new Result().success("审核通过");
        }
        return new Result().erro("审核拒绝");

    }
}

