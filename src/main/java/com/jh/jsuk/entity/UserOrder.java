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
 * 订单
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_order")
public class UserOrder extends Model<UserOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 订单价格
     */
    private BigDecimal orderPrice;
    /**
     * 配送费
     */
    private BigDecimal distributionFee;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 0在线支付,1=货到付款
     */
    private Integer payType;
    /**
     * 0待付款  1待发货  2=已发货 3=交易成功 4=申请退款 5=退款成功 6=交易关闭
     */
    private Integer status;
    /**
     * 0正常状态  1超时未接单退款
     */
    private Integer isUnsubscribe;
    /**
     * 0未评价 1已评价
     */
    private Integer isEvaluate;
    /**
     * 0全部未删除  1用户删除  2商家删除 3用户和商家删除  4骑手删除 5用户和骑手删除 6骑手和商家删除 7都删除
     */
    private Integer isDel;
    /**
     * 店铺id
     */
    private Integer managerId;
    /**
     * 收货地址id
     */
    private Integer addressId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 自动取消订单时间  30分钟
     */
    private Integer cancelTime;
    /**
     * 完成时间
     */
    private Date completeTime;
    /**
     * 配送时间
     */
    private Date sendTime;
    /**
     * 优惠券id
     */
    private Integer couponId;
    /**
     * 配送员id
     */
    private Integer distributionUserId;
    /**
     * 0:普通订单 1:秒杀订单
     */
    private Integer orderType;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getDistributionFee() {
        return distributionFee;
    }

    public void setDistributionFee(BigDecimal distributionFee) {
        this.distributionFee = distributionFee;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsUnsubscribe() {
        return isUnsubscribe;
    }

    public void setIsUnsubscribe(Integer isUnsubscribe) {
        this.isUnsubscribe = isUnsubscribe;
    }

    public Integer getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(Integer isEvaluate) {
        this.isEvaluate = isEvaluate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Integer cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getDistributionUserId() {
        return distributionUserId;
    }

    public void setDistributionUserId(Integer distributionUserId) {
        this.distributionUserId = distributionUserId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static final String ID = "id";

    public static final String ORDER_NUM = "order_num";

    public static final String ORDER_PRICE = "order_price";

    public static final String DISTRIBUTION_FEE = "distribution_fee";

    public static final String CREAT_TIME = "creat_time";

    public static final String PAY_TIME = "pay_time";

    public static final String PAY_TYPE = "pay_type";

    public static final String STATUS = "status";

    public static final String IS_UNSUBSCRIBE = "is_unsubscribe";

    public static final String IS_EVALUATE = "is_evaluate";

    public static final String IS_DEL = "is_del";

    public static final String MANAGER_ID = "manager_id";

    public static final String ADDRESS_ID = "address_id";

    public static final String USER_ID = "user_id";

    public static final String CANCEL_TIME = "cancel_time";

    public static final String COMPLETE_TIME = "complete_time";

    public static final String SEND_TIME = "send_time";

    public static final String COUPON_ID = "coupon_id";

    public static final String DISTRIBUTION_USER_ID = "distribution_user_id";

    public static final String ORDER_TYPE = "order_type";

    public static final String LOGISTICS_NO = "logistics_no";

    public static final String REMARK = "remark";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
        "id=" + id +
        ", orderNum=" + orderNum +
        ", orderPrice=" + orderPrice +
        ", distributionFee=" + distributionFee +
        ", creatTime=" + creatTime +
        ", payTime=" + payTime +
        ", payType=" + payType +
        ", status=" + status +
        ", isUnsubscribe=" + isUnsubscribe +
        ", isEvaluate=" + isEvaluate +
        ", isDel=" + isDel +
        ", managerId=" + managerId +
        ", addressId=" + addressId +
        ", userId=" + userId +
        ", cancelTime=" + cancelTime +
        ", completeTime=" + completeTime +
        ", sendTime=" + sendTime +
        ", couponId=" + couponId +
        ", distributionUserId=" + distributionUserId +
        ", orderType=" + orderType +
        ", logisticsNo=" + logisticsNo +
        ", remark=" + remark +
        "}";
    }
}
