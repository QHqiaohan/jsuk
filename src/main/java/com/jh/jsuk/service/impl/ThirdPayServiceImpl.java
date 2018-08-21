package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.ChargeParamVo;
import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.envm.ShopMoneyType;
import com.jh.jsuk.envm.UserRemainderStatus;
import com.jh.jsuk.envm.UserRemainderType;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.OrderNumUtil;
import com.jh.jsuk.utils.PingPPUtil;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.model.Charge;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Author:xyl
 * Date:2018/8/11 11:25
 * Description:第三方支付-服务类
 */
@Slf4j
@Service
public class ThirdPayServiceImpl implements ThirdPayService {
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private UserRemainderService userRemainderService;
    @Autowired
    private UserRechargeRecordService userRechargeRecordService;

    @Override
    public Charge thirdPay(ThirdPayVo payVo) throws MessageException, UnsupportedEncodingException, ChannelException {
        ThirdPayVoChild child = new ThirdPayVoChild();
        child.setPayVo(payVo);
        ChargeParamVo paramVo = new ChargeParamVo();
        User user = userService.selectById(payVo.getUserId());
        paramVo.setBody(JSONObject.fromObject(child).toString());
        paramVo.setClientIP(user.getLoginIp());
        paramVo.setOpenId(user.getOpenId());
        paramVo.setOrderNo(OrderNumUtil.getOrderIdByUUId());
        paramVo.setPayType(payVo.getPayType());
        switch (payVo.getType()) {
            //用户订单支付
            case 1:
                userOrderPay(payVo, paramVo);
                break;
            //到店支付
            case 2:
                paramVo.setAmount(new BigDecimal(payVo.getPrice()));
                paramVo.setSubject("到店支付");
                break;
            //会员充值
            case 3:
                userRecharge(payVo, paramVo);
                break;
            //快递跑腿
            case 4:
                Express express = expressService.selectById(payVo.getParam());
                paramVo.setAmount(new BigDecimal(express.getPrice()));
                paramVo.setSubject("快递跑腿");
                break;
            default:
                throw new MessageException("付款类型不存在。。");
        }
        return PingPPUtil.createCharge(paramVo);
    }

    @Override
    public void chargeBack(ThirdPayVoChild payVoChild) throws MessageException {
        ThirdPayVo payVo = payVoChild.getPayVo();
        switch (payVo.getType()) {
            //用户订单支付
            case 1:
                log.info("用户订单支付成功");
                userOrderComplete(payVo);
                break;
            //到店支付
            case 2:
                log.info("到店支付成功");
                payStore(payVo);
                break;
            //会员充值
            case 3:
                log.info("会员充值成功");
                userRechargeComplete(payVoChild);
                break;
            //快递跑腿
            case 4:
                log.info("快递跑腿支付成功");
                expressComplete(payVo);
                break;
            default:
                throw new MessageException("付款类型有误。。");
        }
    }




    /**
     * 快递跑腿用户支付成功
     * 回调
     */
    private void expressComplete(ThirdPayVo payVo) {
        Express express = expressService.selectById(payVo.getParam());
        express.setStatus(2);
        express.updateById();
    }

    /**
     * 会员充值成功
     *
     * 回调
     */
    private void userRechargeComplete(ThirdPayVoChild payVoChild) {
        //用户
        User user = userService.selectById(payVoChild.getPayVo().getUserId());
        user.setLevel(Integer.valueOf(payVoChild.getPayVo().getParam()));
        user.updateById();
        //用户余额
        UserRemainder userRemainder = userRemainderService.selectById(payVoChild.getUserRemainderId());
        userRemainder.setStatus(UserRemainderStatus.PASSED);
        userRemainder.updateById();
        //用户充值记录
        UserRechargeRecord userRechargeRecord = userRechargeRecordService.selectById(payVoChild.getUserRechargeRecordId());
        userRechargeRecord.setFinishTime(new Date());
        userRechargeRecord.setIsOk(2);
        userRechargeRecord.updateById();
    }

    /**
     * 到店支付成功
     *
     *
     * 回调
     *
     */
    private void payStore(ThirdPayVo payVo) {
        //商家余额
        ShopMoney shopMoney = new ShopMoney();
        shopMoney.setMoney(payVo.getPrice());
        shopMoney.setPublishTime(new Date());
        shopMoney.setType(ShopMoneyType.GAIN);
        shopMoney.setShopId(Integer.valueOf(payVo.getParam()));
        shopMoney.insert();
    }

    /**
     * 用户订单支付成功
     *
     * 回调
     *
     */
    private void userOrderComplete(ThirdPayVo payVo) {
        String[] ids = payVo.getParam().split(",");
        List<UserOrder> userOrders = userOrderService.selectBatchIds(Arrays.asList(ids));
        BigDecimal price = new BigDecimal("0.00");
        for (UserOrder userOrder : userOrders) {
            if(userOrder == null)
                continue;
            //修改订单信息
            userOrder.setStatus(OrderStatus.WAIT_DELIVER.getKey());
            userOrder.setPayTime(new Date());
            userOrder.setUpdateTime(new Date());
            userOrder.updateById();
            //订单总价格
            price = price.add(userOrder.getOrderRealPrice());
        }
        //商家余额
        ShopMoney shopMoney = new ShopMoney();
        shopMoney.setMoney(price.toString());
        shopMoney.setPublishTime(new Date());
        shopMoney.setType(ShopMoneyType.GAIN);
        shopMoney.setShopId(userOrders.get(0).getShopId());
        shopMoney.insert();
    }















    /**
     * 会员充值
     */
    private void userRecharge(ThirdPayVo payVo, ChargeParamVo paramVo) {
        Member member = memberService.selectById(payVo.getParam());
        //用户余额表
        UserRemainder e = new UserRemainder();
        e.setType(UserRemainderType.RECHARGE);
        e.setStatus(UserRemainderStatus.APPLYING);
        e.setUserId(payVo.getUserId());
        e.setCreateTime(new Date());
        e.setMemberId(member.getId());
        e.setRemainder(new BigDecimal(member.getMemberPrice()));
        e.setOrderNum(paramVo.getOrderNo());
        e.insert();

        //用户充值记录
        UserRechargeRecord record = new UserRechargeRecord();
        record.setMemberId(member.getId());
        record.setRechargeMethod(payVo.getPayType());
        record.setCreateTime(new Date());
        record.setRechargeMoney(member.getMemberPrice());
        record.setUserId(payVo.getUserId());
        record.insert();

        ThirdPayVoChild child = new ThirdPayVoChild();
        child.setPayVo(payVo);
        child.setUserRechargeRecordId(record.getId());
        child.setUserRemainderId(e.getId());

        paramVo.setAmount(new BigDecimal(member.getMemberPrice()));
        paramVo.setSubject("会员充值");
        paramVo.setBody(JSONObject.fromObject(child).toString());
    }

    /**
     * 用户订单支付
     */
    private void userOrderPay(ThirdPayVo payVo, ChargeParamVo paramVo) {
        String[] ids = payVo.getParam().split(",");
        List<UserOrder> userOrders = userOrderService.selectBatchIds(Arrays.asList(ids));
        BigDecimal price = new BigDecimal("0.00");
        for (UserOrder u : userOrders) {
            price = price.add(u.getOrderRealPrice());
            u.setPayType(payVo.getPayType());
            u.updateById();
        }
        paramVo.setAmount(price);
        paramVo.setSubject("商品订单支付");
    }
}
