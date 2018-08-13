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
import org.springframework.util.CollectionUtils;
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
    private UserRemainderService userRemainderService;
    @Autowired
    private ShopMoneyService shopMoneyService;

    @ApiOperation("用户端-查询用户可提现余额")
    @RequestMapping(value = "/getRemainderByUid", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getRemainderByUid(@RequestParam Integer userId){

        BigDecimal remainder=new BigDecimal("0.00");
        Map map=new HashMap();

        List<UserRemainder> userRemainderList=userRemainderService.selectList(new EntityWrapper<UserRemainder>()
                                                                              .eq(UserRemainder.USER_ID,userId)
                                                                              .eq(UserRemainder.ID_DEL,0)
                                                                              .eq(UserRemainder.IS_OK,2)
        );
        if(userRemainderList!=null && userRemainderList.size()>0){
            for(UserRemainder userRemainder:userRemainderList){
                Integer type = userRemainder.getType();   //类型,1=充值,-1=消费,0=其他 2=购买会员
                if(userRemainder.getRemainder()==null){
                    userRemainder.setRemainder(new BigDecimal("0.00"));
                }
                if(type==1 || type==0){
                    remainder=remainder.add(userRemainder.getRemainder());
                }else if(type==-1){
                    remainder=remainder.subtract(userRemainder.getRemainder());
                }
            }
        }
        map.put("remainder",remainder);
        return new Result().success(map);
    }


    @ApiOperation("提现")
    @RequestMapping(value = "/addTiXian", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addTiXian(@ModelAttribute UserTiXian userTiXian,
                            @ApiParam(value = "2=用户,0=商家,1=骑手", required = true) Integer type,
                            @RequestParam Integer userId) {
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
        Map<String, Integer> map = setQueryParams(amountScope);
        Integer begin = map.get("begin");
        Integer end = map.get("end");
        Page<ShopUserTiXianVo> userTiXianPage=userTiXianService.selectByAdvance(page,tixianId,begin,end,status);
        return new Result().success(userTiXianPage);
    }



    /**
     * 后台管理系统
     * 财务管理
     * 用户提现列表
     */
    @GetMapping("advanceSearchUserTiXianPage")
    public Result advanceSearchUserTiXianPage(Page page,
                                          @RequestParam(required = false) Integer tixianId,
                                          @RequestParam(required = false) String amountScope,
                                          @RequestParam(required = false) Integer status){
        Map<String, Integer> map = setQueryParams(amountScope);
        Integer begin = map.get("begin");
        Integer end = map.get("end");
        Page<UserTiXianVo> userTiXianPage=userTiXianService.selectByAdvance2(page,tixianId,begin,end,status);
        return new Result().success(userTiXianPage);
    }

    //平台-商家端提现审核
    @PostMapping("/shopTiXianExamine")
    public Result shopTiXianExamine(@RequestParam Integer tiXianId,
                                    @RequestParam Integer shopManagerId){
        System.out.println("shopManagerId:"+shopManagerId);
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

        /**
         * 查询商家可提现余额
         */
        BigDecimal reminder = queryShopReminder(shopId);
        if(reminder.compareTo(new BigDecimal(userTiXian.getPrice()))==-1){
            return new Result().erro("可提现余额不足");
        }

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
    @ApiOperation("平台-提现记录-用户提现审核")
    @RequestMapping(value="tiXianExamine",method={RequestMethod.POST,RequestMethod.GET})
    public Result tiXianExamine(@RequestParam(value="tiXianId") Integer tiXianId,
                                @RequestParam(value="uid") Integer userId){
        UserTiXian userTiXian=userTiXianService.selectOne(new EntityWrapper<UserTiXian>()
                                                               .eq(UserTiXian.ID,tiXianId)
                                                               .eq("examine",0)
                                                               .eq(UserTiXian.USER_ID,userId)
        );
        if(userTiXian==null){
            return new Result().erro("提现记录数据不存在");
        }
        User user=userService.selectOne(new EntityWrapper<User>()
                                        .eq(User.ID,userId)
        );
        if(user==null){
            return new Result().erro("用户不存在");
        }
        //查询用户可提现金额
        Result r = getRemainderByUid(userId);
        Map map=(Map)r.getData();
        BigDecimal remainder=(BigDecimal)map.get("remainder");

        if(remainder.compareTo(new BigDecimal(userTiXian.getPrice()))==-1){
            return new Result().erro("余额不足");
        }
        /**
         * 可提现
         */
        userTiXian.setExamine(1);  //1.审核通过
        userTiXian.updateById();

        UserRemainder userRemainder=new UserRemainder();
        userRemainder.setUserId(userId);
        userRemainder.setType(-1);   //-1代表消费
        userRemainder.setRemainder(new BigDecimal(userTiXian.getPrice()));   //金额
        userRemainder.setIdDel(0);
        userRemainder.setCreateTime(new Date());
        userRemainder.setIsOk(2);
        userRemainder.insert();

        return new Result().success("提现成功");
        /*//查询用户红包记录,用户只有红包才能提现
        List<UserInviteLog> userInviteLogList=userInviteLogService.selectList(new EntityWrapper<UserInviteLog>()
                                                                              .eq(UserInviteLog.USER_ID,userId)
                                                                              .eq(UserInviteLog.IS_TIXIAN,0)  //1:已经提现,0未提现
        );
        if(userInviteLogList==null || userInviteLogList.size()==0){
            return new Result().erro("没有可以提现的金额");
        }
        //获取提现金额
        BigDecimal price=new BigDecimal(userTiXian.getPrice());
        if(price==null){
            return new Result().success("输入提现金额");
        }
        BigDecimal tixian_account=new BigDecimal("0.00");  //提现红包金额
        for(UserInviteLog inviteLog:userInviteLogList){
            if(inviteLog.getMoney()==null){
                inviteLog.setMoney(new BigDecimal(0));
            }
            tixian_account=tixian_account.add(inviteLog.getMoney());
        }
        if(price.compareTo(tixian_account)==1){   //提现金额大于红包金额
            return new Result().erro("红包金额不足");
        }
        //price小于等于红包总金额
        //记录最终的提现金额
        BigDecimal final_account=new BigDecimal("0.00");
        for(UserInviteLog inviteLog:userInviteLogList){
            if(price.compareTo(inviteLog.getMoney())==-1 || price.compareTo(inviteLog.getMoney())==0){
                inviteLog.setMoney(inviteLog.getMoney().subtract(price));
                inviteLog.updateById();
                final_account=final_account.add(price);
                break;
            }
            if(price.compareTo(inviteLog.getMoney())==1){
                price=price.subtract(inviteLog.getMoney());
                final_account=final_account.add(inviteLog.getMoney());
                inviteLog.setIsTixian(1);   //该红包已提现
                inviteLog.updateById();
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
        map.put("msg","success");*/
    }


    //平台-骑手提现列表
    @RequestMapping(value="searchDistributionUserTiXian",method={RequestMethod.POST,RequestMethod.GET})
    public Result searchDistributionUserTiXian(Page page,
                                               @RequestParam(required = false) Integer tixianId,
                                               @RequestParam(required = false) String amountScope,
                                               @RequestParam(required = false) Integer status){

        Map<String, Integer> map = setQueryParams(amountScope);
        Integer begin = map.get("begin");
        Integer end = map.get("end");
        EntityWrapper<DistributionApply> ew=new EntityWrapper<>();
        Page distributionUserTiXianPage=distributionApplyService.searchDistributionUserTiXian(page,ew,tixianId,begin,end,status);
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

    private Map<String,Integer> setQueryParams(String amountScope){
        Map<String,Integer> map=new HashMap<>();
        if(amountScope!=null) {
            String[] scopes=null;
            Integer begin=null;
            Integer end=null;
            scopes = amountScope.split("-");    //以-劈开,格式:1000-2000
            begin=Integer.parseInt(scopes[0]);
            try{
                end=Integer.parseInt(scopes[1]);
            }catch(Exception e){
                end=null;
            }
            map.put("begin",begin);
            map.put("end",end);
        }
        return map;
    }

    //查询商家可提现余额
    private BigDecimal queryShopReminder(Integer shopId) {
        List<ShopMoney> shopMoneyList = shopMoneyService.selectList(new EntityWrapper<ShopMoney>()
            .eq(ShopMoney.SHOP_ID, shopId));
        if (CollectionUtils.isEmpty(shopMoneyList)) {
            return new BigDecimal(0);
        } else {
            // 初始化余额
            BigDecimal sum = new BigDecimal("0.00");
            for (ShopMoney shopMoney : shopMoneyList) {  //计算商家余额
                // 金额
                BigDecimal money = new BigDecimal("0.00");
                if (shopMoney.getMoney() != null) {
                    money = new BigDecimal(shopMoney.getMoney());
                }
                // 消费类型,计算  类型,0=消费,1=收入
                Integer xfType = shopMoney.getType();
                if (xfType == 0) {
                    // 消费
                    sum = sum.subtract(money);
                } else if (xfType == 1) {
                    // 收入
                    sum = sum.add(money);
                }
            }
            return sum;
        }
    }

}

