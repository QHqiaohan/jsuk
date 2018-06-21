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
 * 店铺
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@TableName("js_shop")
public class Shop extends Model<Shop> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 模块ID
     */
    private Integer modularId;
    /**
     * 店铺头像
     */
    private String headImg;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 地址
     */
    private String address;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 店铺图片
     */
    private String shopImg;
    /**
     * 公告
     */
    private String announcement;
    /**
     * 营业开始时间
     */
    private Date startTime;
    /**
     * 营业结束时间
     */
    private Date endTime;
    /**
     * 店铺账户
     */
    private BigDecimal accountPoint;
    /**
     * 总访问量 
     */
    private Integer totalVolume;
    /**
     * 起送费
     */
    private BigDecimal deliveryFee;
    /**
     * 星数
     */
    private Integer starNum;
    /**
     * 省ID
     */
    private Integer provinceId;
    /**
     * 市ID
     */
    private Integer cityId;
    /**
     * 区县ID
     */
    private Integer areaId;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 是否可用  0不可用 1可用
     */
    private Integer canUse;
    /**
     * 是否推荐,0=不推荐,1=推荐
     */
    private Integer isRecommend;
    /**
     * 内容
     */
    private String content;
    /**
     * 优惠信息
     */
    private String discountInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModularId() {
        return modularId;
    }

    public void setModularId(Integer modularId) {
        this.modularId = modularId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
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

    public BigDecimal getAccountPoint() {
        return accountPoint;
    }

    public void setAccountPoint(BigDecimal accountPoint) {
        this.accountPoint = accountPoint;
    }

    public Integer getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Integer totalVolume) {
        this.totalVolume = totalVolume;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getCanUse() {
        return canUse;
    }

    public void setCanUse(Integer canUse) {
        this.canUse = canUse;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public static final String ID = "id";

    public static final String MODULAR_ID = "modular_id";

    public static final String HEAD_IMG = "head_img";

    public static final String SHOP_NAME = "shop_name";

    public static final String ADDRESS = "address";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String SHOP_IMG = "shop_img";

    public static final String ANNOUNCEMENT = "announcement";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String ACCOUNT_POINT = "account_point";

    public static final String TOTAL_VOLUME = "total_volume";

    public static final String DELIVERY_FEE = "delivery_fee";

    public static final String STAR_NUM = "star_num";

    public static final String PROVINCE_ID = "province_id";

    public static final String CITY_ID = "city_id";

    public static final String AREA_ID = "area_id";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String CAN_USE = "can_use";

    public static final String IS_RECOMMEND = "is_recommend";

    public static final String CONTENT = "content";

    public static final String DISCOUNT_INFO = "discount_info";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Shop{" +
        "id=" + id +
        ", modularId=" + modularId +
        ", headImg=" + headImg +
        ", shopName=" + shopName +
        ", address=" + address +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", shopImg=" + shopImg +
        ", announcement=" + announcement +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", accountPoint=" + accountPoint +
        ", totalVolume=" + totalVolume +
        ", deliveryFee=" + deliveryFee +
        ", starNum=" + starNum +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", publishTime=" + publishTime +
        ", canUse=" + canUse +
        ", isRecommend=" + isRecommend +
        ", content=" + content +
        ", discountInfo=" + discountInfo +
        "}";
    }
}
