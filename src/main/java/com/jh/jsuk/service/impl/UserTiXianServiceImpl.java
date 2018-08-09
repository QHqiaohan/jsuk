package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserTiXianDao;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.ManagerUser;
import com.jh.jsuk.entity.ShopMoney;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.service.DistributionUserService;
import com.jh.jsuk.service.ManagerUserService;
import com.jh.jsuk.service.ShopMoneyService;
import com.jh.jsuk.service.UserTiXianService;
import com.jh.jsuk.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private DistributionUserService distributionUserService;

    @Override
    @Transactional
    public Result tixian(UserTiXian userTiXian, Integer type, Integer userId) {

        if (type == 2) {             //2=用户,0=商家,1=骑手
            //用户提现
            return new Result().success();
        } else if (type == 0) {        //商家提现
            ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
                .eq(ManagerUser.ID, userId));
            if(managerUser==null){
                return new Result().erro("该商家不存在");
            }
            Integer shopId = managerUser.getShopId();
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
                if(Double.parseDouble(userTiXian.getPrice())<=0){
                    return new Result().erro("提现金额错误");
                }
                if (sum >= Double.parseDouble(userTiXian.getPrice())) {
                    userTiXian.insert();
                    /*ShopMoney shopMoney=new ShopMoney();
                    shopMoney.setShopId(shopId);
                    shopMoney.setMoney("-"+userTiXian.getPrice());
                    shopMoney.setType(0);
                    shopMoney.setPublishTime(new Date());
                    shopMoney.insert();*/
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
    public Page<UserTiXianVo> selectByAdvance(Page page, Integer tixianId, Integer begin, Integer end, Integer status) {
        List<UserTiXianVo> list=baseMapper.selectByAdvance(page,tixianId,begin,end,status);
        return page.setRecords(list);
    }

/*    @Override
    public List<UserTiXianVo> selectByAdvance(Integer tixianId, Integer begin, Integer end, Integer status) {
        return baseMapper.selectByAdvance(tixianId,begin,end,status);
    }*/
}
