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
 * @author tj
 * @since 2018-07-20
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
     * 订单原始价格
     */
    private BigDecimal orderPrice;
    /**
     * 满减了多少
     */
    private BigDecimal fullReduce;
    /**
     * 优惠券优惠了多少
     */
    private BigDecimal couponReduce;
    /**
     * 积分抵扣了多少
     */
    private BigDecimal integralReduce;
    /**
     * 订单实际价格
     */
    private BigDecimal orderRealPrice;
    /**
     * 配送费
     */
    private BigDecimal distributionFee;
    /**
     * 配送时间
     */
    private Date distributionTime;
    /**
     * 配送方式 0：快递 1： 同城配送 2：到店自提
     */
    private Integer distributionType;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     *  0 余额   1 货到付款  2 支付宝  3 微信  4 银行卡
     */
    private Integer payType;
    /**
     *   0 : 待付款  1  : 待发货  2  : 待收货  3  : 售后  4  : 退款  5 : 退货   6  : 拒绝  7  : 取消
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
     * 商家删除
     */
    private Integer isShopDel;
    /**
     * 用户删除
     */
    private Integer isUserDel;
    /**
     * 关闭   1：关闭 0：未关闭
     */
    private Integer isClosed;
    /**
     * 店铺id
     */
    private Integer shopId;
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
     * 订单类型 0:普通订单 1:秒杀订单 2:会员购买 3:充值 4:到店支付
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
    /**
     * 订单的折扣，如果是会员优惠
     */
    private BigDecimal discount;
    /**
     * 积分规则
     */
    private Integer integralRuleId;
    /**
     * 满减规则
     */
    private Integer fullReduceId;
    /**
     * 平台流水号
     */
    private String platformNumber;
    private String goodsName;


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

    public BigDecimal getFullReduce() {
        return fullReduce;
    }

    public void setFullReduce(BigDecimal fullReduce) {
        this.fullReduce = fullReduce;
    }

    public BigDecimal getCouponReduce() {
        return couponReduce;
    }

    public void setCouponReduce(BigDecimal couponReduce) {
        this.couponReduce = couponReduce;
    }

    public BigDecimal getIntegralReduce() {
        return integralReduce;
    }

    public void setIntegralReduce(BigDecimal integralReduce) {
        this.integralReduce = integralReduce;
    }

    public BigDecimal getOrderRealPrice() {
        return orderRealPrice;
    }

    public void setOrderRealPrice(BigDecimal orderRealPrice) {
        this.orderRealPrice = orderRealPrice;
    }

    public BigDecimal getDistributionFee() {
        return distributionFee;
    }

    public void setDistributionFee(BigDecimal distributionFee) {
        this.distributionFee = distributionFee;
    }

    public Date getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(Date distributionTime) {
        this.distributionTime = distributionTime;
    }

    public Integer getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(Integer distributionType) {
        this.distributionType = distributionType;
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

    public Integer getIsShopDel() {
        return isShopDel;
    }

    public void setIsShopDel(Integer isShopDel) {
        this.isShopDel = isShopDel;
    }

    public Integer getIsUserDel() {
        return isUserDel;
    }

    public void setIsUserDel(Integer isUserDel) {
        this.isUserDel = isUserDel;
    }

    public Integer getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Integer isClosed) {
        this.isClosed = isClosed;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getIntegralRuleId() {
        return integralRuleId;
    }

    public void setIntegralRuleId(Integer integralRuleId) {
        this.integralRuleId = integralRuleId;
    }

    public Integer getFullReduceId() {
        return fullReduceId;
    }

    public void setFullReduceId(Integer fullReduceId) {
        this.fullReduceId = fullReduceId;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }



    public static final String ID = "id";

    public static final String ORDER_NUM = "order_num";

    public static final String ORDER_PRICE = "order_price";

    public static final String FULL_REDUCE = "full_reduce";

    public static final String COUPON_REDUCE = "coupon_reduce";

    public static final String INTEGRAL_REDUCE = "integral_reduce";

    public static final String ORDER_REAL_PRICE = "order_real_price";

    public static final String DISTRIBUTION_FEE = "distribution_fee";

    public static final String DISTRIBUTION_TIME = "distribution_time";

    public static final String DISTRIBUTION_TYPE = "distribution_type";

    public static final String CREAT_TIME = "creat_time";

    public static final String PAY_TIME = "pay_time";

    public static final String PAY_TYPE = "pay_type";

    public static final String STATUS = "status";

    public static final String IS_UNSUBSCRIBE = "is_unsubscribe";

    public static final String IS_EVALUATE = "is_evaluate";

    public static final String IS_SHOP_DEL = "is_shop_del";

    public static final String IS_USER_DEL = "is_user_del";

    public static final String IS_CLOSED = "is_closed";

    public static final String SHOP_ID = "shop_id";

    public static final String ADDRESS_ID = "address_id";

    public static final String USER_ID = "user_id";

    public static final String CANCEL_TIME = "cancel_time";

    public static final String COMPLETE_TIME = "complete_time";

    public static final String SEND_TIME = "send_time";

    public static final String COUPON_ID = "coupon_id";

    public static final String ORDER_TYPE = "order_type";

    public static final String LOGISTICS_NO = "logistics_no";

    public static final String REMARK = "remark";

    public static final String DISCOUNT = "discount";

    public static final String INTEGRAL_RULE_ID = "integral_rule_id";

    public static final String FULL_REDUCE_ID = "full_reduce_id";

    public static final String PLATFORM_NUMBER = "platform_number";

    public static final String GOODS_NAME = "goods_name";

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
            ", fullReduce=" + fullReduce +
            ", couponReduce=" + couponReduce +
            ", integralReduce=" + integralReduce +
            ", orderRealPrice=" + orderRealPrice +
            ", distributionFee=" + distributionFee +
            ", distributionTime=" + distributionTime +
            ", distributionType=" + distributionType +
            ", creatTime=" + creatTime +
            ", payTime=" + payTime +
            ", payType=" + payType +
            ", status=" + status +
            ", isUnsubscribe=" + isUnsubscribe +
            ", isEvaluate=" + isEvaluate +
            ", isShopDel=" + isShopDel +
            ", isUserDel=" + isUserDel +
            ", isClosed=" + isClosed +
            ", shopId=" + shopId +
            ", addressId=" + addressId +
            ", userId=" + userId +
            ", cancelTime=" + cancelTime +
            ", completeTime=" + completeTime +
            ", sendTime=" + sendTime +
            ", couponId=" + couponId +
            ", orderType=" + orderType +
            ", logisticsNo=" + logisticsNo +
            ", remark=" + remark +
            ", discount=" + discount +
            ", integralRuleId=" + integralRuleId +
            ", fullReduceId=" + fullReduceId +
            ", platformNumber=" + platformNumber +
            "}";
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
