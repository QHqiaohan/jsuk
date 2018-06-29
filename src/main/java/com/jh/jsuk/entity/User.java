package com.jh.jsuk.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 普通用户
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user")
public class User extends ParentUser<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 等级 0:普通 1:铜牌 2:银牌 3:金牌会员
     */
    private Integer level;
    /**
     * 0:女 1:男
     */
    private Integer sex;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 微信Token
     */
    private String wxToken;
    /**
     * 微博Token
     */
    private String weiboToken;
    /**
     * QQtoken
     */
    private String qqToken;
    /**
     * 1:可用 0禁用
     */
    private Integer canUse;
    /**
     * 1:开启  0:禁用
     */
    private Integer isSecondaryMarket;
    /**
     * 是否领取过优惠券 0:否 1:是
     */
    private Integer isAvailable;
    /**
     * 住址
     */
    private String address;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImg() {
        return StrUtil.trim(headImg);
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }

    public String getWeiboToken() {
        return weiboToken;
    }

    public void setWeiboToken(String weiboToken) {
        this.weiboToken = weiboToken;
    }

    public String getQqToken() {
        return qqToken;
    }

    public void setQqToken(String qqToken) {
        this.qqToken = qqToken;
    }

    public Integer getCanUse() {
        return canUse;
    }

    public void setCanUse(Integer canUse) {
        this.canUse = canUse;
    }

    public Integer getIsSecondaryMarket() {
        return isSecondaryMarket;
    }

    public void setIsSecondaryMarket(Integer isSecondaryMarket) {
        this.isSecondaryMarket = isSecondaryMarket;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public static final String ID = "id";

    public static final String NICK_NAME = "nick_name";

    public static final String PHONE = "phone";

    public static final String LEVEL = "level";

    public static final String SEX = "sex";

    public static final String HEAD_IMG = "head_img";

    public static final String PASSWORD = "password";

    public static final String LOGIN_IP = "login_ip";

    public static final String WX_TOKEN = "wx_token";

    public static final String WEIBO_TOKEN = "weibo_token";

    public static final String QQ_TOKEN = "qq_token";

    public static final String CAN_USE = "can_use";

    public static final String IS_SECONDARY_MARKET = "is_secondary_market";

    public static final String IS_AVAILABLE = "is_available";

    public static final String ADDRESS = "address";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName=" + nickName +
                ", phone=" + phone +
                ", level=" + level +
                ", sex=" + sex +
                ", headImg=" + headImg +
                ", password=" + password +
                ", loginIp=" + loginIp +
                ", wxToken=" + wxToken +
                ", weiboToken=" + weiboToken +
                ", qqToken=" + qqToken +
                ", canUse=" + canUse +
                ", isSecondaryMarket=" + isSecondaryMarket +
                ", isAvailable=" + isAvailable +
                ", address=" + address +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", lastLoginTime=" + lastLoginTime +
                "}";
    }
}
