package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户浏览记录_收藏
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_footprint")
public class UserFootprint extends Model<UserFootprint> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺ID
     */
    private Integer shopId;
    /**
     * 商品ID
     */
    private Integer shopGoodsId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 类型, 1=足迹,2=收藏
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Date createTime;


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

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String SHOP_GOODS_ID = "shop_goods_id";

    public static final String USER_ID = "user_id";

    public static final String TYPE = "type";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserFootprint{" +
        "id=" + id +
        ", shopId=" + shopId +
        ", shopGoodsId=" + shopGoodsId +
        ", userId=" + userId +
        ", type=" + type +
        ", createTime=" + createTime +
        "}";
    }
}
