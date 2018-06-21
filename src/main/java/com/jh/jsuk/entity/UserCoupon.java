package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券ID关联t_coupon
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@TableName("js_user_coupon")
public class UserCoupon extends Model<UserCoupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 优惠卷id
     */
    private Integer couponId;
    /**
     * 0已使用 1未使用  2未开始  3已结束
     */
    private Integer status;
    /**
     * 添加时间
     */
    private Date publishTime;


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

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String COUPON_ID = "coupon_id";

    public static final String STATUS = "status";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserCoupon{" +
        "id=" + id +
        ", userId=" + userId +
        ", couponId=" + couponId +
        ", status=" + status +
        ", publishTime=" + publishTime +
        "}";
    }
}
