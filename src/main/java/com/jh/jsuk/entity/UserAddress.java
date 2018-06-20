package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 普通用户地址表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_address")
public class UserAddress extends Model<UserAddress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货人手机号
     */
    private String phone;
    /**
     * 1:男 0:女
     */
    private Integer sex;
    /**
     * 经度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 邮政编码
     */
    private String postalCode;
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
     * 门牌号
     */
    private String roomNumber;
    /**
     * 是否默认 0:否 1:是
     */
    private Integer isDefault;
    /**
     * 是否删除 0:否 1:是
     */
    private Integer isDel;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 邀请人ID
     */
    private Integer invitationId;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    public Integer getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Integer invitationId) {
        this.invitationId = invitationId;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String PHONE = "phone";

    public static final String SEX = "sex";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String POSTAL_CODE = "postal_code";

    public static final String PROVINCE = "province";

    public static final String CITY = "city";

    public static final String AREA = "area";

    public static final String ADDRESS = "address";

    public static final String ROOM_NUMBER = "room_number";

    public static final String IS_DEFAULT = "is_default";

    public static final String IS_DEL = "is_del";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String INVITATION_ID = "invitation_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", phone=" + phone +
        ", sex=" + sex +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", postalCode=" + postalCode +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", address=" + address +
        ", roomNumber=" + roomNumber +
        ", isDefault=" + isDefault +
        ", isDel=" + isDel +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", invitationId=" + invitationId +
        "}";
    }
}
