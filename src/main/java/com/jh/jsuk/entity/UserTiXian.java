package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户提现记录
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@TableName("js_user_ti_xian")
public class UserTiXian extends Model<UserTiXian> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", name = "userId")
    private Integer userId;
    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID", name = "managerId")
    private Integer managerId;
    /**
     * 银行卡ID
     */
    @ApiModelProperty(value = "银行卡ID", name = "bankId")
    private Integer bankId;
    /**
     * 支付宝/收款账号
     */
    @ApiModelProperty(value = "支付宝/收款账号", name = "num")
    private String num;
    /**
     * 0=处理中,1=已提现,2=提现失败,3=取消
     */
    @ApiModelProperty(value = "0=处理中,1=已提现,2=提现失败,3=取消", name = "status")
    private Integer status;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;
    /**
     * 0=删除,1=未删除
     */
    @ApiModelProperty(value = "0=删除,1=未删除", name = "isDel")
    private Integer isDel;
    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额", name = "price")
    private String price;

    private Date createTime;

    /**
     * 提现审核，1：通过 0：不通过
     */
    private Integer examine;
    /**
     * 提现渠道
     */
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
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

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String MANAGER_ID = "manager_id";

    public static final String BANK_ID = "bank_id";

    public static final String NUM = "num";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String IS_DEL = "is_del";

    public static final String PRICE = "price";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserTiXian{" +
        "id=" + id +
        ", userId=" + userId +
        ", managerId=" + managerId +
        ", bankId=" + bankId +
        ", num=" + num +
        ", status=" + status +
        ", remark=" + remark +
        ", isDel=" + isDel +
        ", price=" + price +
        ", createTime=" + createTime +
        "}";
    }
}
