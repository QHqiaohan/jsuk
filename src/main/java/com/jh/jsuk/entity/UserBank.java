package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;

import java.beans.Transient;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 银行卡
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_bank")
public class UserBank extends Model<UserBank> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 0商家端  1骑手端 2:普通用户
     */
    private Integer userType;
    private Integer userId;
    /**
     * 账号
     */
    private String bankNumber;


    private String accountNumber;

    @Transient
    public String getAccountNumber() {
        return accountNumber;
    }

    @Transient
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 开户人姓名
     */
    private String name;
    /**
     * 开户银行
     */
    private String bankName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * icon
     */
    private String icon;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static final String ID = "id";

    public static final String USER_TYPE = "user_type";

    public static final String USER_ID = "user_id";

    public static final String BANK_NUMBER = "bank_number";

    public static final String NAME = "name";

    public static final String BANK_NAME = "bank_name";

    public static final String CREATE_TIME = "create_time";

    public static final String ICON = "icon";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserBank{" +
        "id=" + id +
        ", userType=" + userType +
        ", userId=" + userId +
        ", bankNumber=" + bankNumber +
        ", name=" + name +
        ", bankName=" + bankName +
        ", createTime=" + createTime +
        ", icon=" + icon +
        "}";
    }
}
