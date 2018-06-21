package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 商户信息
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@TableName("js_shop_user")
public class ShopUser extends ParentUser<ShopUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺ID
     */
    private Integer shopId;
    /**
     * 商户id
     */
    private Integer managerUserId;
    /**
     * 是否商品免审核(0:否 1:是)
     */
    private Integer isWaiting;
    /**
     * 账号审核状态(0:未审核 1:通过  -1:驳回)
     */
    private Integer isCheck;
    /**
     * 描述
     */
    private String desc;
    /**
     * 法人姓名
     */
    private String legalPersonName;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(Integer managerUserId) {
        this.managerUserId = managerUserId;
    }

    public Integer getIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(Integer isWaiting) {
        this.isWaiting = isWaiting;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
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

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String MANAGER_USER_ID = "manager_user_id";

    public static final String IS_WAITING = "is_waiting";

    public static final String IS_CHECK = "is_check";

    public static final String DESC = "desc";

    public static final String LEGAL_PERSON_NAME = "legal_person_name";

    public static final String ID_CARD_NO = "id_card_no";

    public static final String CARD_FRONT = "card_front";

    public static final String CARD_BACK = "card_back";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopUser{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", managerUserId=" + managerUserId +
        ", isWaiting=" + isWaiting +
        ", isCheck=" + isCheck +
        ", desc=" + desc +
        ", legalPersonName=" + legalPersonName +
        ", idCardNo=" + idCardNo +
        ", cardFront=" + cardFront +
        ", cardBack=" + cardBack +
        "}";
    }
}
