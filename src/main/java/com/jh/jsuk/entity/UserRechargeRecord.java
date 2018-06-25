package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户充值记录
 * </p>
 *
 * @author lpf
 * @since 2018-06-24
 */
@TableName("js_user_recharge_record")
public class UserRechargeRecord extends Model<UserRechargeRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @TableField("userId")
    private Integer userId;
    /**
     * 充值类型11:安卓会员购买 12:安卓充值 21:IOS会员购买 22:IOS充值  31:公众号会员购买  32:公众号充值
     */
    private Integer rechargeType;
    /**
     * 充值金额
     */
    private String rechargeMoney;
    /**
     * 是否删除 0:否 1:是
     */
    private Integer idDel;
    /**
     * 创建时间
     */
    private Date createTime;


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

    public Integer getIdDel() {
        return idDel;
    }

    public void setIdDel(Integer idDel) {
        this.idDel = idDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String USERID = "userId";

    public static final String RECHARGE_TYPE = "recharge_type";

    public static final String RECHARGE_MONEY = "recharge_money";

    public static final String ID_DEL = "id_del";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserRechargeRecord{" +
        "id=" + id +
        ", userId=" + userId +
        ", rechargeType=" + rechargeType +
        ", rechargeMoney=" + rechargeMoney +
        ", idDel=" + idDel +
        ", createTime=" + createTime +
        "}";
    }
}
