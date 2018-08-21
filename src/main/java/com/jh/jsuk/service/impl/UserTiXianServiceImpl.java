package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserTiXianDao;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.ShopUserTiXianVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.envm.UserCashStatus;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.OrderNumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户提现记录 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Slf4j
@Service
public class UserTiXianServiceImpl extends ServiceImpl<UserTiXianDao, UserTiXian> implements UserTiXianService {
    @Autowired
    private ShopMoneyService shopMoneyService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void tixian(UserTiXian cash) throws Exception {
        UserType userType = cash.getUserType();
        cash.setTiXianNo(OrderNumUtil.getOrderIdByUUId());
        cash.setStatus(UserCashStatus.APPLYING);
        cash.insert();
        switch (userType) {
            case SHOP:
                shopCash(cash);
                break;
            case DISTRIBUTION:
                distributionCash(cash);
                break;
            case USER:
                userCash(cash);
                break;
            case ADMIN:
            case CITY_ADMIN:
            default:
                log.error("未知提现类型");
                break;
        }

//        if (type == 2) {             //2=用户,0=商家,1=骑手
//            //用户提现
//            userTiXian.setUserId(userId);
//            // TODO
////            userTiXian.setExamine(0);
//            userTiXian.insert();
//            return new Result().success("提现申请成功,等待平台审核");
//        } else if (type == 0) {
//            /**商家提现
//             * 商家注册的时候注册3张表:
//             * js_shop; js_shop_user; js_manager_user
//             * js_shop的id对应js_manager_user的shop_id
//             * js_shop_user的manager_user_id对应js_manager_user的id
//             */
//            ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
//                .eq(ManagerUser.ID, userId));
//            if (managerUser == null) {
//                return new Result().erro("该商家不存在");
//            }
//            //获取店铺id
//            Integer shopId = managerUser.getShopId();
//            System.out.println("店铺id:" + shopId);
//            /**
//             * 商家交易明细表,计算商家余额
//             */
//            List<ShopMoney> shopMoneyList = shopMoneyService.selectList(new EntityWrapper<ShopMoney>()
//                .eq(ShopMoney.SHOP_ID, shopId));
//            if (CollectionUtils.isEmpty(shopMoneyList)) {
//                return new Result().erro("账户余额不足");
//            } else {
//                // 初始化余额
//                BigDecimal sum = shopMoneyService.getShopMoney(shopId);
//                if (Double.parseDouble(userTiXian.getPrice()) <= 0) {
//                    return new Result().erro("提现金额错误");
//                }
//                //比较提现金额和余额
//                int i = sum.compareTo(new BigDecimal(userTiXian.getPrice()));
//                if (i == 1 || i == 0) {
//                    userTiXian.insert();
//                    return new Result().success("提现成功,等待平台审核");
//                } else {
//                    return new Result().erro("账户余额不足");
//                }
//            }
//        } else if (type == 1) {    //骑手提现
//            return new Result().success();
//        }
//        return new Result().erro("参数错误");
    }

    @Autowired
    UserRemainderService userRemainderService;

    @Autowired
    DistributionApplyService distributionApplyService;

    private void userCash(UserTiXian cash) throws Exception {
        userRemainderService.createCashApplying(cash.getUserId(), cash.getPrice(), cash.getTiXianNo());
    }

    private void distributionCash(UserTiXian cash) throws Exception {
        distributionApplyService.createCashApplying(cash.getUserId(), new BigDecimal(cash.getPrice()), cash.getTiXianNo());
    }

    private void shopCash(UserTiXian cash) throws Exception {
        shopMoneyService.createCashApplying(cash.getManagerId(), new BigDecimal(cash.getPrice()), cash.getTiXianNo());
    }

    @Override
    public Page<ShopUserTiXianVo> selectByAdvance(Page page, String tixianId, Integer begin, Integer end, Integer status) {
        List<ShopUserTiXianVo> list = baseMapper.selectByAdvance(page, tixianId, begin, end, status);
        return page.setRecords(list);
    }

    @Override
    public Page<UserTiXianVo> selectByAdvance2(Page page, String tixianId, Integer begin, Integer end, Integer status) {
        List<UserTiXianVo> list = baseMapper.selectByAdvance2(page, tixianId, begin, end, status);
        for (UserTiXianVo vo : list) {
            vo.setUserName(userService.userName(vo.getUserId(), vo.getUserType()));
            System.out.println(vo);
        }

        return page.setRecords(list);
    }

    @Override
    public void pass(Integer id) {
        UserTiXian ent = selectById(id);
        ent.setStatus(UserCashStatus.PASSED);
        ent.updateById();
        String no = ent.getTiXianNo();
        UserType userType = ent.getUserType();
        switch (userType) {
            case SHOP:
            case ADMIN:
            case CITY_ADMIN:
                shopMoneyService.confirm(no);
                break;
            case DISTRIBUTION:
                distributionApplyService.confirm(no);
                break;
            case USER:
                userRemainderService.confirm(no);
                break;
        }
    }

    @Override
    public void decline(Integer id) {
        UserTiXian ent = selectById(id);
        ent.setStatus(UserCashStatus.REFUSED);
        ent.updateById();
        String no = ent.getTiXianNo();
        UserType userType = ent.getUserType();
        switch (userType) {
            case SHOP:
            case ADMIN:
            case CITY_ADMIN:
                shopMoneyService.decline(no);
                break;
            case DISTRIBUTION:
                distributionApplyService.decline(no);
                break;
            case USER:
                userRemainderService.decline(no);
                break;
        }
    }

}
