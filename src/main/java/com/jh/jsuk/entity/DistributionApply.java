package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 配送端提现申请

 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_distribution_apply")
public class DistributionApply extends Model<DistributionApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer bankId;
    private BigDecimal money;
    /**
     * 0待审核  1通过  2拒绝
     */
    private Integer status;
    private Integer userId;
    /**
     * 拒绝原因
     */
    private String desc;
    private Date publishTime;
    /**
     * 手续费
     */
    private BigDecimal poundage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public static final String ID = "id";

    public static final String BANK_ID = "bank_id";

    public static final String MONEY = "money";

    public static final String STATUS = "status";

    public static final String USER_ID = "user_id";

    public static final String DESC = "desc";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String POUNDAGE = "poundage";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DistributionApply{" +
        "id=" + id +
        ", bankId=" + bankId +
        ", money=" + money +
        ", status=" + status +
        ", userId=" + userId +
        ", desc=" + desc +
        ", publishTime=" + publishTime +
        ", poundage=" + poundage +
        "}";
    }
}
