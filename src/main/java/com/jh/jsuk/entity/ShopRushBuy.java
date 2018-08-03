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
 * 秒杀信息配置
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
@TableName("js_shop_rush_buy")
public class ShopRushBuy extends Model<ShopRushBuy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date startTime;

    private Date endTime;
    /**
     * 1删除 0 未删除
     */
    @JsonIgnore
    private Integer isDel;

    /**
     * 1 启用 0禁用
     */
    private Integer isUse;

    public static final String ID = "id";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String IS_DEL = "is_del";

    public static final String IS_USE = "is_use";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
