package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户充值记录
 */
public class UserRechargeRecord extends Model<UserRechargeRecord> implements Serializable{

    //充值id
    private Integer id;

    //用户id
    private Integer userId;

    //充值类型
    private Integer rechargeType;

    //充值金额
    private String rechargeMoney;

    //是否删除
    private Integer isDel;

    //创建时间
    private Date createTime;

    //0:待支付 1：支付成功 2：支付失败
    private Integer isOk;

    //会员配置id
    private Integer memberId;

    //赠送金额
    private String sendMoney;

    //充值方式
    private Integer rechargeMethod;

    //到账时间
    private Date finishTime;

    //状态,0:未到账，1：已到账
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(String sendMoney) {
        this.sendMoney = sendMoney;
    }

    public Integer getRechargeMethod() {
        return rechargeMethod;
    }

    public void setRechargeMethod(Integer rechargeMethod) {
        this.rechargeMethod = rechargeMethod;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
