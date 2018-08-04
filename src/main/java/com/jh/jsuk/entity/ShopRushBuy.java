package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.ShopRushBuyStatus;
import com.jh.jsuk.utils.Date2;
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
    private Integer isDel;

    @TableField(exist = false)
    private ShopRushBuyStatus status;

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

    public void checkStatus() {
        Date2 date2 = new Date2();
        date2.setYear2(1970);
        date2.setMonth2(0);
        date2.setDay2(1);
        Date2 end = new Date2(endTime);
        Date2 start = new Date2(startTime);
        if (date2.isBefore(start)) {
            status = ShopRushBuyStatus.NOT_STARTED;
        } else if (date2.isAfter(start) && date2.isBefore(end)) {
            status = ShopRushBuyStatus.ON_GOING;
        } else if (date2.isAfter(end)) {
            status = ShopRushBuyStatus.OVER;
        }
    }

}
