package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 秒杀活动
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@Setter
@Getter
@ToString
@TableName("js_shop_rush_buy_activity")
public class ShopRushBuyActivity extends Model<ShopRushBuyActivity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer shopId;

    /**
     * 配置id
     */
    private Integer rushBuyId;

    private Integer goodsId;

    /**
     * 1=删除,0=未删除
     */
    @JsonIgnore
    private Integer isDel;

    /**
     * 1=上架,0=下架
     */
    @JsonIgnore
    private Integer isUse;

    private Date publishTime;

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String ACTIVITY_NAME = "activity_name";

    public static final String RUSH_BUY_ID = "rush_buy_id";

    public static final String IS_DEL = "is_del";

    public static final String IS_USE = "is_use";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
