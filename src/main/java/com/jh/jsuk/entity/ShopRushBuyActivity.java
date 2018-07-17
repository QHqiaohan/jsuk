package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 秒杀活动
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@TableName("js_shop_rush_buy_activity")
public class ShopRushBuyActivity extends Model<ShopRushBuyActivity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 配置id
     */
    private Integer rushBuyId;
    /**
     * 规格id
     */
    private Integer goodsSizeId;
    /**
     * 1=删除,0=未删除
     */
    private Integer isDel;
    private Date publishTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getRushBuyId() {
        return rushBuyId;
    }

    public void setRushBuyId(Integer rushBuyId) {
        this.rushBuyId = rushBuyId;
    }

    public Integer getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(Integer goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String ACTIVITY_NAME = "activity_name";

    public static final String RUSH_BUY_ID = "rush_buy_id";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    public static final String IS_DEL = "is_del";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopRushBuyActivity{" +
        "id=" + id +
        ", activityName=" + activityName +
        ", rushBuyId=" + rushBuyId +
        ", goodsSizeId=" + goodsSizeId +
        ", isDel=" + isDel +
        ", publishTime=" + publishTime +
        "}";
    }
}
