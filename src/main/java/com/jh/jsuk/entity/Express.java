package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 快递跑腿
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_express")
public class Express extends Model<Express> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 寄件人地址ID
     */
    private Integer senderAddress;
    /**
     * 收件人地址ID
     */
    private Integer getAddress;
    /**
     * 物品类型ID
     */
    private Integer goodsType;
    /**
     * 预估重量
     */
    private String weight;
    /**
     * 真实重量
     */
    private String realWeight;
    /**
     * 是否删除 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 状态 1=待接单,2=待送货,3=待评价,0=待付款,4=已完成,5=已取消
     */
    private Integer status;
    /**
     * 订单类型 1=快递,2=跑腿
     */
    private Integer type;
    /**
     * 价格
     */
    private String price;
    /**
     * 备注
     */
    private String notes;
    /**
     * 要求送达时间
     */
    private Date requirementTime;
    /**
     * 创建时间
     */
    private Date publishTime;
    /**
     * 完成时间
     */
    private Date endTime;


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

    public Integer getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(Integer senderAddress) {
        this.senderAddress = senderAddress;
    }

    public Integer getGetAddress() {
        return getAddress;
    }

    public void setGetAddress(Integer getAddress) {
        this.getAddress = getAddress;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRealWeight() {
        return realWeight;
    }

    public void setRealWeight(String realWeight) {
        this.realWeight = realWeight;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getRequirementTime() {
        return requirementTime;
    }

    public void setRequirementTime(Date requirementTime) {
        this.requirementTime = requirementTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String SENDER_ADDRESS = "sender_address";

    public static final String GET_ADDRESS = "get_address";

    public static final String GOODS_TYPE = "goods_type";

    public static final String WEIGHT = "weight";

    public static final String REAL_WEIGHT = "real_weight";

    public static final String IS_DEL = "is_del";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String PRICE = "price";

    public static final String NOTES = "notes";

    public static final String REQUIREMENT_TIME = "requirement_time";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String END_TIME = "end_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Express{" +
        "id=" + id +
        ", userId=" + userId +
        ", senderAddress=" + senderAddress +
        ", getAddress=" + getAddress +
        ", goodsType=" + goodsType +
        ", weight=" + weight +
        ", realWeight=" + realWeight +
        ", isDel=" + isDel +
        ", status=" + status +
        ", type=" + type +
        ", price=" + price +
        ", notes=" + notes +
        ", requirementTime=" + requirementTime +
        ", publishTime=" + publishTime +
        ", endTime=" + endTime +
        "}";
    }
}
