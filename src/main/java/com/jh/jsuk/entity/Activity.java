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
 * 发布活动相关表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_activity")
public class Activity extends Model<Activity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手机
     */
    private String phone;
    /**
     * 联系人
     */
    private String name;
    /**
     * 活动标题
     */
    private String title;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 定金
     */
    private BigDecimal fixedPrice;
    /**
     * 内容
     */
    private String content;
    /**
     * 活动开始时间
     */
    private String startTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 创建时间
     */
    private Date publishTime;
    /**
     * 0=未删除, 1=删除
     */
    private Integer isDel;
    /**
     * 1=乡村旅游,2=便捷生活,3=二手市场
     */
    private Integer type;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 数值越大越靠前
     */
    private String rank;
    /**
     * 分类ID
     */
    private Integer classId;
    /**
     * 模块ID
     */
    private Integer modularId;
    /**
     * 车辆ID
     */
    private Integer carId;
    /**
     * 是否推荐,0=不推荐,1=推荐
     */
    private Integer isRecommend;
    /**
     * 图片
     */
    private String images;
    /**
     * 1=商家,2=需求
     */
    private Integer status;
    /**
     * 交易区域ID
     */
    private Integer transactionAreaId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigDecimal fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getModularId() {
        return modularId;
    }

    public void setModularId(Integer modularId) {
        this.modularId = modularId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTransactionAreaId() {
        return transactionAreaId;
    }

    public void setTransactionAreaId(Integer transactionAreaId) {
        this.transactionAreaId = transactionAreaId;
    }

    public static final String ID = "id";

    public static final String PHONE = "phone";

    public static final String NAME = "name";

    public static final String TITLE = "title";

    public static final String PRICE = "price";

    public static final String FIXED_PRICE = "fixed_price";

    public static final String CONTENT = "content";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String IS_DEL = "is_del";

    public static final String TYPE = "type";

    public static final String USER_ID = "user_id";

    public static final String RANK = "rank";

    public static final String CLASS_ID = "class_id";

    public static final String MODULAR_ID = "modular_id";

    public static final String CAR_ID = "car_id";

    public static final String IS_RECOMMEND = "is_recommend";

    public static final String IMAGES = "images";

    public static final String STATUS = "status";

    public static final String TRANSACTION_AREA_ID = "transaction_area_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Activity{" +
        "id=" + id +
        ", phone=" + phone +
        ", name=" + name +
        ", title=" + title +
        ", price=" + price +
        ", fixedPrice=" + fixedPrice +
        ", content=" + content +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", publishTime=" + publishTime +
        ", isDel=" + isDel +
        ", type=" + type +
        ", userId=" + userId +
        ", rank=" + rank +
        ", classId=" + classId +
        ", modularId=" + modularId +
        ", carId=" + carId +
        ", isRecommend=" + isRecommend +
        ", images=" + images +
        ", status=" + status +
        ", transactionAreaId=" + transactionAreaId +
        "}";
    }
}
