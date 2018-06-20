package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户评价
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_evaluate")
public class UserEvaluate extends Model<UserEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 给店铺评价的星数
     */
    private Integer shopStarNum;
    /**
     * 给骑手评价的星数
     */
    private Integer distributionStarNum;
    /**
     * 客服评价
     */
    private Integer serviceStartNum;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价图片
     */
    private String img;
    /**
     * 0好评  1中评 2差评
     */
    private Integer status;
    /**
     * 是否已删除,0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 1:普通用户 2:骑手
     */
    private Integer typeCode;
    /**
     * 评价时间
     */
    private Date createTime;


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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getShopStarNum() {
        return shopStarNum;
    }

    public void setShopStarNum(Integer shopStarNum) {
        this.shopStarNum = shopStarNum;
    }

    public Integer getDistributionStarNum() {
        return distributionStarNum;
    }

    public void setDistributionStarNum(Integer distributionStarNum) {
        this.distributionStarNum = distributionStarNum;
    }

    public Integer getServiceStartNum() {
        return serviceStartNum;
    }

    public void setServiceStartNum(Integer serviceStartNum) {
        this.serviceStartNum = serviceStartNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ORDER_ID = "order_id";

    public static final String GOODS_ID = "goods_id";

    public static final String SHOP_STAR_NUM = "shop_star_num";

    public static final String DISTRIBUTION_STAR_NUM = "distribution_star_num";

    public static final String SERVICE_START_NUM = "service_start_num";

    public static final String CONTENT = "content";

    public static final String IMG = "img";

    public static final String STATUS = "status";

    public static final String IS_DEL = "is_del";

    public static final String TYPE_CODE = "type_code";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserEvaluate{" +
        "id=" + id +
        ", userId=" + userId +
        ", orderId=" + orderId +
        ", goodsId=" + goodsId +
        ", shopStarNum=" + shopStarNum +
        ", distributionStarNum=" + distributionStarNum +
        ", serviceStartNum=" + serviceStartNum +
        ", content=" + content +
        ", img=" + img +
        ", status=" + status +
        ", isDel=" + isDel +
        ", typeCode=" + typeCode +
        ", createTime=" + createTime +
        "}";
    }
}
