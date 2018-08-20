package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserRemainderDao;
import com.jh.jsuk.entity.Member;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserRechargeRecord;
import com.jh.jsuk.entity.UserRemainder;
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
    public BigDecimal getRemainder(Integer userId) {
        List<UserRemainder> list = selectList(new EntityWrapper<UserRemainder>()
            .eq(UserRemainder.USER_ID, userId));
        // 初始化记录总余额
        BigDecimal remain = new BigDecimal("0.00");
        if (list == null || list.isEmpty())
            return remain;
        // 如果余额表有该用户信息
        for (UserRemainder remainder : list) {
            // 如果是消费
            if (UserRemainderType.CONSUME.equals(remainder.getType())) {
                remain = remain.subtract(remainder.getRemainder());
            } else {
                // 充值
                remain = remain.add(remainder.getRemainder());
            }
        }
        return remain;
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
        return getRemainder(userId).compareTo(bigDecimal) > 0;
    }

    @Override
    public void consume(Integer userId, BigDecimal amount) throws Exception {
        UserRemainder e = new UserRemainder();
        e.setUserId(userId);
        e.setCreateTime(new Date());
        e.setRemainder(amount);
        e.setType(UserRemainderType.CONSUME);
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
}
