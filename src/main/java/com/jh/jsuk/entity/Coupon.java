package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 优惠券
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Data
@TableName("js_coupon")
public class Coupon extends Model<Coupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 满足价格
     */
    private BigDecimal fullPrice;
    /**
     * 优惠价格
     */
    private BigDecimal discount;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 优惠卷数量
     */
    private Integer num;
    /**
     * 1删除  0未删除
     */
    private Integer isDel;
    /**
     * 添加时间
     */
    private Date publishTime;
    /**
     * 优惠券类型  0满减券
     */
    private Integer couponType;
    /**
     * 没人限领多少张  0代表无限制
     */
    private Integer limitingNum;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 1 :全平台  2:公众号  3:APP
     */
    private Integer couponPlatform;

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String FULL_PRICE = "full_price";

    public static final String DISCOUNT = "discount";

    public static final String START_TIME = "start_time";

    public static final String END_TIME = "end_time";

    public static final String NUM = "num";

    public static final String IS_DEL = "is_del";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String COUPON_TYPE = "coupon_type";

    public static final String LIMITING_NUM = "limiting_num";

    public static final String COUPON_NAME = "coupon_name";

    public static final String COUPON_PLATFORM = "coupon_platform";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
