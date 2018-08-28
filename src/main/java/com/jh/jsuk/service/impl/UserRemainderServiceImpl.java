package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserRemainderDao;
import com.jh.jsuk.entity.Member;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserRechargeRecord;
import com.jh.jsuk.entity.UserRemainder;
import com.jh.jsuk.entity.info.UserRemainderInfo;
import com.jh.jsuk.entity.vo.ChargeParamVo;
import com.jh.jsuk.entity.vo.UserRechargeVo;
import com.jh.jsuk.envm.UserRemainderStatus;
import com.jh.jsuk.envm.UserRemainderType;
import com.jh.jsuk.service.MemberService;
import com.jh.jsuk.service.UserRechargeRecordService;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.service.UserService;
import com.jh.jsuk.utils.OrderNumUtil;
import com.jh.jsuk.utils.PingPPUtil;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户余额表 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Service
public class UserRemainderServiceImpl extends ServiceImpl<UserRemainderDao, UserRemainder> implements UserRemainderService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRechargeRecordService recordService;

    @Override
    public UserRemainderInfo getRemainder(Integer userId) {
        UserRemainderInfo info = new UserRemainderInfo();
        List<UserRemainder> list = selectList(new EntityWrapper<UserRemainder>()
            .eq(UserRemainder.STATUS, UserRemainderStatus.PASSED.getKey())
            .eq(UserRemainder.USER_ID, userId));


        // 初始化记录总余额
        BigDecimal remain = new BigDecimal("0.00");
        info.setRemainder(remain);
        if (list == null || list.isEmpty()) {
            info.setCash(new BigDecimal("0"));
            return info;
        }
        /**
         * 已提现金额
         */
        BigDecimal cashed = new BigDecimal("0");
        /**
         * 总共可用提现金额
         */
        BigDecimal totalCash = new BigDecimal("0");
        for (UserRemainder remainder : list) {
            UserRemainderType type = remainder.getType();
            BigDecimal rmdr = remainder.getRemainder();
            if (type == null || rmdr == null)
                continue;
            switch (type) {
                // 只有红包 和 退款可以提现
                case GET_RED_PACKET:
                case REFUND://退款
                    totalCash = totalCash.add(rmdr);
                case RECHARGE://充值
                    remain = remain.add(rmdr);
                    break;
                case CASH://提现
                    cashed = cashed.add(rmdr);
                case CONSUME://消费
                    remain = remain.subtract(rmdr);
                    break;
            }
        }
        BigDecimal rem = totalCash.subtract(cashed);
        if (remain.compareTo(rem) >= 0) {
            info.setCash(rem);//可提现金额
        } else {
            info.setCash(new BigDecimal("0"));
        }
        info.setRemainder(remain);
        return info;
    }

    @Override
    public BigDecimal getConsumption(Integer userId) {
        List<UserRemainder> list = selectList(new EntityWrapper<UserRemainder>()
            .eq(UserRemainder.USER_ID, userId)
            .eq(UserRemainder.TYPE, UserRemainderType.CONSUME));
        // 初始化记录总余额
        BigDecimal remain = new BigDecimal("0.00");
        if (list == null || list.isEmpty())
            return remain;
        // 如果余额表有该用户信息
        for (UserRemainder remainder : list) {
            remain = remain.add(remainder.getRemainder());
        }
        return remain;
    }

    @Override
    public boolean hasRemain(Integer userId) {
        return hasRemain(userId, new BigDecimal("0.00"));
    }

    @Override
    public boolean hasRemain(Integer userId, BigDecimal bigDecimal) {
        if (userId == null || bigDecimal == null)
            return false;
        return getRemainder(userId).hasRemain(bigDecimal);
    }

    @Override
    public void consume(Integer userId, BigDecimal amount) throws Exception {
        consume(userId, amount, null);
    }

    @Override
    public void consume(Integer userId, BigDecimal amount, String orderNum) throws Exception {
        UserRemainder e = new UserRemainder();
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        e.setOrderNum(orderNum);
        e.setType(UserRemainderType.CONSUME);
        e.setStatus(UserRemainderStatus.PASSED);
        insert(e);
    }

    @Override
    public void recharge(Integer userId, BigDecimal amount) throws Exception {
        UserRemainder e = new UserRemainder();
        e.setType(UserRemainderType.RECHARGE);
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        insert(e);
    }

    @Override
    public UserRechargeVo memberCharge(Integer userId, Integer memberId, Integer payType) throws UnsupportedEncodingException, ChannelException {
        User user = userService.selectById(userId);
        Member member = memberService.selectById(memberId);
        //用户余额表
        UserRemainder e = new UserRemainder();
        e.setType(UserRemainderType.RECHARGE);
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setMemberId(memberId);
        e.setRemainder(new BigDecimal(member.getMemberPrice()));
        e.setOrderNum(OrderNumUtil.getOrderIdByUUId());
        e.insert();

        //用户充值记录
        UserRechargeRecord record = new UserRechargeRecord();
        record.setMemberId(memberId);
        record.setRechargeMethod(payType);
        record.setRechargeMoney(member.getMemberPrice());
        record.insert();

        ChargeParamVo paramVo = new ChargeParamVo();
        paramVo.setSubject("会员充值");
        paramVo.setClientIP(user.getLoginIp());
        paramVo.setOrderNo(e.getOrderNum());
        paramVo.setOpenId(user.getOpenId());
        paramVo.setAmount(new BigDecimal(member.getMemberPrice()));
        paramVo.setBody("会员充值");
        paramVo.setPayType(payType);
        Charge charge = PingPPUtil.createCharge(paramVo);
        UserRechargeVo userRechargeVo = new UserRechargeVo();
        userRechargeVo.setCharge(charge);
        userRechargeVo.setRechargeRecordId(record.getId());
        userRechargeVo.setRemainderId(e.getId());
        return userRechargeVo;
    }

    @Override
    public void chargeComplete(Integer remainderId, Integer rechargeRecordId, Integer status) {
        //用户余额
        UserRemainder userRemainder = selectById(remainderId);
        userRemainder.setStatus(UserRemainderStatus.PASSED);
        userRemainder.updateById();
        //用户充值记录
        UserRechargeRecord userRechargeRecord = recordService.selectById(rechargeRecordId);
        userRechargeRecord.setFinishTime(new Date());
        userRechargeRecord.setIsOk(status == 1 ? 2 : 1);
        userRechargeRecord.updateById();
    }

    @Override
    public void createCashApplying(Integer userId, String price, String tiXianNo, Integer bankId) {
        UserRemainder e = new UserRemainder();
        e.setType(UserRemainderType.CASH);
        e.setStatus(UserRemainderStatus.APPLYING);
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(new BigDecimal(price));
        e.setOrderNum(tiXianNo);
        e.insert();
    }

    @Override
    public void decline(String no) {
        UserRemainder entity = new UserRemainder();
        EntityWrapper<UserRemainder> wrapper = new EntityWrapper<>();
        entity.setStatus(UserRemainderStatus.REFUSED);
        wrapper.eq(UserRemainder.ORDER_NUM, no);
        update(entity, wrapper);
    }

    @Override
    public void gain(Integer userId, BigDecimal amount) {
        UserRemainder e = new UserRemainder();
        e.setType(UserRemainderType.REFUND);
        e.setStatus(UserRemainderStatus.PASSED);
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        e.insert();
    }

    @Override
    public void confirm(String no) {
        UserRemainder entity = new UserRemainder();
        EntityWrapper<UserRemainder> wrapper = new EntityWrapper<>();
        entity.setStatus(UserRemainderStatus.PASSED);
        wrapper.eq(UserRemainder.ORDER_NUM, no);
        update(entity, wrapper);
    }
}
