package com.jh.jsuk.service.impl;

import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.ChargeParamVo;
import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.OrderNumUtil;
import com.jh.jsuk.utils.PingPPUtil;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.model.Charge;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
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
 * Description:第三方支付
 */
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

    @Override
    public Charge thirdPay(ThirdPayVo payVo) throws MessageException, UnsupportedEncodingException, ChannelException {
        ChargeParamVo paramVo = new ChargeParamVo();
        User user = userService.selectById(payVo.getUserId());
        paramVo.setBody(JSONObject.fromObject(payVo).toString());
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

    /**
     * 会员充值
     */
    private void userRecharge(ThirdPayVo payVo, ChargeParamVo paramVo) {
        Member member = memberService.selectById(payVo.getParam());
        //用户余额表
        UserRemainder e = new UserRemainder();
        e.setType(2);
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
