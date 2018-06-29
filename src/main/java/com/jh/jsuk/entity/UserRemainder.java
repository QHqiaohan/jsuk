package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户余额表
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@TableName("js_user_remainder")
public class UserRemainder extends Model<UserRemainder> {

    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String TYPE = "type";
    public static final String REMAINDER = "remainder";
    public static final String USER_ID = "user_id";
    public static final String IS_OK = "is_ok";
    public static final String MEMBER_ID = "member_id";
    public static final String ID_DEL = "id_del";
    public static final String ORDER_NUM = "order_num";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 类型,1=充值,-1=消费,0=其他
     */
    private Integer type;
    /**
     * 金额
     */
    private BigDecimal remainder;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 0:待支付 1:支付失败 2:支付成功
     */
    private Integer isOk;
    /**
     * 会员配置ID
     */
    private Integer memberId;
    /**
     * 是否删除 0:否 1:是
     */
    private Integer idDel;
    /**
     * 单号
     */
    private String orderNum;
    /**
     * 平台流水号
     */
    private String platformNumber;

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }

    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getIdDel() {
        return idDel;
    }

    public void setIdDel(Integer idDel) {
        this.idDel = idDel;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserRemainder{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", type=" + type +
                ", remainder=" + remainder +
                ", userId=" + userId +
                ", isOk=" + isOk +
                ", memberId=" + memberId +
                ", idDel=" + idDel +
                ", orderNum=" + orderNum +
                "}";
    }
}
