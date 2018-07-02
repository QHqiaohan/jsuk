package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 骑手信息
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_distribution_user")
public class DistributionUser extends ParentUser<DistributionUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 身份证正面
     */
    private String cardFront;
    /**
     * 身份证背面
     */
    private String cardBack;
    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;
    /**
     * 登陆ip
     */
    private String loginIp;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 0女  1男
     */
    private String gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 星数
     */
    private Integer starNum;
    /**
     * 骑手端账户余额
     */
    private BigDecimal account;
    /**
     * 创建时间
     */
    private Date publishTime;
    /**
     * 0 不可用  1可用
     */
    private Integer canUse;
    /**
     * 0 待审核   1 审核通过    2 审核拒绝
     */
    private Integer status;
    /**
     * 拒绝原因
     */
    private String desc;
    /**
     * 是否在线(0 休息中  1:接单中)
     */
    private Integer isOnline;

    public ParentUserEx toParentUser(){
        ParentUserEx ex = new ParentUserEx();
        ex.setUserId(id);
        ex.setPassword(password);
        ex.setCanUse(canUse);
        ex.setLastLogin(lastLoginTime);
        return ex;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public static final String ID = "id";

    public static final String PHONE = "phone";

    public static final String PASSWORD = "password";

    public static final String NAME = "name";

    public static final String ID_CARD_NO = "id_card_no";

    public static final String CARD_FRONT = "card_front";

    public static final String CARD_BACK = "card_back";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static final String LOGIN_IP = "login_ip";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String HEAD_IMG = "head_img";

    public static final String GENDER = "gender";

    public static final String AGE = "age";

    public static final String STAR_NUM = "star_num";

    public static final String ACCOUNT = "account";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String CAN_USE = "can_use";

    public static final String STATUS = "status";

    public static final String DESC = "desc";

    public static final String IS_ONLINE = "is_online";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DistributionUser{" +
        "id=" + id +
        ", phone=" + phone +
        ", password=" + password +
        ", name=" + name +
        ", idCardNo=" + idCardNo +
        ", cardFront=" + cardFront +
        ", cardBack=" + cardBack +
        ", lastLoginTime=" + lastLoginTime +
        ", loginIp=" + loginIp +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", headImg=" + headImg +
        ", gender=" + gender +
        ", age=" + age +
        ", starNum=" + starNum +
        ", account=" + account +
        ", publishTime=" + publishTime +
        ", canUse=" + canUse +
        ", status=" + status +
        ", desc=" + desc +
        ", isOnline=" + isOnline +
        "}";
    }
}
