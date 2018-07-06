package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 秒杀配置和商品规格关联表
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Data
@TableName("js_shop_rush_buy_size")
public class ShopRushBuySize extends Model<ShopRushBuySize> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer rushBuyId;
    private Integer goodsSizeId;
    /**
     * TODO 修改 1 为删除 0 为未删除
     * 0=删除,1=未删除
     */
    private Integer isDel;
    private Integer isUse;
    private Date publishTime;


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getRushBuyId() {
//        return rushBuyId;
//    }
//
//    public void setRushBuyId(Integer rushBuyId) {
//        this.rushBuyId = rushBuyId;
//    }
//
//    public Integer getGoodsSizeId() {
//        return goodsSizeId;
//    }
//
//    public void setGoodsSizeId(Integer goodsSizeId) {
//        this.goodsSizeId = goodsSizeId;
//    }
//
//    public Integer getIsDel() {
//        return isDel;
//    }
//
//    public void setIsDel(Integer isDel) {
//        this.isDel = isDel;
//    }
//
//    public Date getPublishTime() {
//        return publishTime;
//    }
//
//    public void setPublishTime(Date publishTime) {
//        this.publishTime = publishTime;
//    }

    public static final String ID = "id";

    public static final String RUSH_BUY_ID = "rush_buy_id";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    public static final String IS_DEL = "is_del";

    public static final String IS_USE = "is_use";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopRushBuySize{" +
        "id=" + id +
        ", rushBuyId=" + rushBuyId +
        ", goodsSizeId=" + goodsSizeId +
        ", isDel=" + isDel +
        ", publishTime=" + publishTime +
        "}";
    }
}
