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
 * 优惠券
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_coupon")
public class Coupon extends Model<Coupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商家Id
     */
    private Integer managerUserId;
    /**
     * 满足价格
     */
    private BigDecimal fullPrice;
    /**
     * 优惠价格
     */
    private BigDecimal discount;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 优惠卷数量
     */
    private Integer num;
    /**
     * 0删除  1未删除
     */
    private Integer isDel;
    /**
     * 添加时间
     */
    private Date publishTime;
    /**
     * 优惠券类型 1:全平台 0:商家
     */
    private Integer couponType;
    /**
     * 没人限领多少张  0代表无限制
     */
    private Integer limitingNum;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 1 :全平台  2:公众号  3:APP
     */
    private Integer couponPlatform;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(Integer managerUserId) {
        this.managerUserId = managerUserId;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getLimitingNum() {
        return limitingNum;
    }

    public void setLimitingNum(Integer limitingNum) {
        this.limitingNum = limitingNum;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponPlatform() {
        return couponPlatform;
    }

    public void setCouponPlatform(Integer couponPlatform) {
        this.couponPlatform = couponPlatform;
    }

    public static final String ID = "id";

    public static final String MANAGER_USER_ID = "manager_user_id";

    public static final String FULL_PRICE = "full_price";

    public static final String DISCOUNT = "discount";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String NUM = "num";

    public static final String IS_DEL = "is_del";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String COUPON_TYPE = "coupon_type";

    public static final String LIMITING_NUM = "limiting_num";

    public static final String COUPON_NAME = "coupon_name";

    public static final String COUPON_PLATFORM = "coupon_platform";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Coupon{" +
        "id=" + id +
        ", managerUserId=" + managerUserId +
        ", fullPrice=" + fullPrice +
        ", discount=" + discount +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", num=" + num +
        ", isDel=" + isDel +
        ", publishTime=" + publishTime +
        ", couponType=" + couponType +
        ", limitingNum=" + limitingNum +
        ", couponName=" + couponName +
        ", couponPlatform=" + couponPlatform +
        "}";
    }
}
