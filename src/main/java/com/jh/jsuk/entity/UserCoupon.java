package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 优惠券ID关联t_coupon
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@Getter
@Setter
@ToString
@TableName("js_user_coupon")
public class UserCoupon extends Model<UserCoupon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 优惠卷id
     */
    private Integer couponId;
    /**
     * 已使用
     */
    private Integer isUsed;
    /**
     * 添加时间
     */
    private Date publishTime;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String COUPON_ID = "coupon_id";

    public static final String IS_USED = "is_used";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
