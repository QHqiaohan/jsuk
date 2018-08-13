package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserTiXianDao;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.ShopUserTiXianVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户提现记录 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Service
public class UserTiXianServiceImpl extends ServiceImpl<UserTiXianDao, UserTiXian> implements UserTiXianService {
    @Autowired
    private ShopMoneyService shopMoneyService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private UserService userService;

    @Override
    public Result tixian(UserTiXian userTiXian, Integer type, Integer userId) {
        if (type == 2) {             //2=用户,0=商家,1=骑手
            //用户提现
            userTiXian.setUserId(userId);
            userTiXian.setExamine(0);
            userTiXian.setIsDel(0);
            userTiXian.insert();
            return new Result().success("提现申请成功,等待平台审核");
        } else if (type == 0) {
            /**商家提现
             * 商家注册的时候注册3张表:
             * js_shop; js_shop_user; js_manager_user
             * js_shop的id对应js_manager_user的shop_id
             * js_shop_user的manager_user_id对应js_manager_user的id
             */
            ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
            if(managerUser==null){
                return new Result().erro("该商家不存在");
            }
            //获取店铺id
            Integer shopId = managerUser.getShopId();
            System.out.println("店铺id:"+shopId);
            /**
             * 商家交易明细表,计算商家余额
             */
            List<ShopMoney> shopMoneyList = shopMoneyService.selectList(new EntityWrapper<ShopMoney>()
                    .eq(ShopMoney.SHOP_ID, shopId));
            if (CollectionUtils.isEmpty(shopMoneyList)) {
                return new Result().erro("账户余额不足");
            } else {
                // 初始化余额
                BigDecimal sum = new BigDecimal("0.00");
                for (ShopMoney shopMoney : shopMoneyList) {  //计算商家余额
                    // 金额
                    BigDecimal money=new BigDecimal("0.00");
                    if(shopMoney.getMoney()!=null){
                         money=new BigDecimal(shopMoney.getMoney());
                    }
                    // 消费类型,计算  类型,0=消费,1=收入
                    Integer xfType = shopMoney.getType();
                    if (xfType == 0) {
                        // 消费
                        sum=sum.subtract(money);
                    } else if (xfType == 1) {
                        // 收入
                        sum=sum.add(money);
                    }
                }
                if(Double.parseDouble(userTiXian.getPrice())<=0){
                    return new Result().erro("提现金额错误");
                }
                //比较提现金额和余额
                int i=sum.compareTo(new BigDecimal(userTiXian.getPrice()));
                if (i==1 || i==0) {
                    userTiXian.insert();
                    return new Result().success("提现成功,等待平台审核");
                } else {
                    return new Result().erro("账户余额不足");
                }
            }
        } else if (type == 1) {    //骑手提现
            return new Result().success();
        }
        return new Result().erro("参数错误");
    }

    @Override
    public Page<ShopUserTiXianVo> selectByAdvance(Page page, Integer tixianId, Integer begin, Integer end, Integer status) {
        List<ShopUserTiXianVo> list=baseMapper.selectByAdvance(page,tixianId,begin,end,status);
        return page.setRecords(list);
    }

    @Override
    public Page<UserTiXianVo> selectByAdvance2(Page page, Integer tixianId, Integer begin, Integer end, Integer status) {
        List<UserTiXianVo> list=baseMapper.selectByAdvance2(page,tixianId,begin,end,status);
        return page.setRecords(list);
    }

}
