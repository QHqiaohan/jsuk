package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户领取优惠券
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_coupon_user")
public class CouponUser extends Model<CouponUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 优惠券Id
     */
    private Integer couponId;
    /**
     * 1:已使用 2 未使用 3:未开始 4:已结束
     */
    private Integer status;
    /**
     * 领取时间
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

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String COUPON_ID = "coupon_id";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CouponUser{" +
        "id=" + id +
        ", userId=" + userId +
        ", couponId=" + couponId +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
