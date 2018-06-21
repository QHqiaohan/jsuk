package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 管理用户
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_manager_user")
public class ManagerUser extends ParentUser<ManagerUser> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 账号
     */
    private String userName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 商品ID
     */
    private Integer shopId;
    /**
     * 用户类型 1:平台 2:商家
     */
    private Integer userType;
    /**
     * 是否可用 0:否  1:是
     */
    private Integer canUse;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 座机
     */
    private String telPhone;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 邮箱
     */
    private String eMail;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * login IP
     */
    private String loginIp;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getCanUse() {
        return canUse;
    }

    public void setCanUse(Integer canUse) {
        this.canUse = canUse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String NAME = "name";

    public static final String PASSWORD = "password";

    public static final String SHOP_ID = "shop_id";

    public static final String USER_TYPE = "user_type";

    public static final String CAN_USE = "can_use";

    public static final String PHONE = "phone";

    public static final String TEL_PHONE = "tel_phone";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String AREA = "area";

    public static final String ADDRESS = "address";

    public static final String HEAD_IMG = "head_img";

    public static final String E_MAIL = "e_mail";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String LOGIN_IP = "login_ip";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ManagerUser{" +
        "id=" + id +
        ", userName=" + userName +
        ", name=" + name +
        ", password=" + password +
        ", shopId=" + shopId +
        ", userType=" + userType +
        ", canUse=" + canUse +
        ", phone=" + phone +
        ", telPhone=" + telPhone +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", address=" + address +
        ", headImg=" + headImg +
        ", eMail=" + eMail +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", loginIp=" + loginIp +
        ", lastLoginTime=" + lastLoginTime +
        "}";
    }
}
