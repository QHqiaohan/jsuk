package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户余额表
 * </p>
 *
 * @author lpf
 * @since 2018-06-21
 */
@TableName("js_user_remainder")
public class UserRemainder extends Model<UserRemainder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 操作时间
     */
    private Date publishTime;
    /**
     * 类型,1=充值,-1=消费,0=其他
     */
    private Integer type;
    /**
     * 用户余额
     */
    private BigDecimal remainder;
    /**
     * 用户ID
     */
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRemainder() {
        return remainder;
    }

    public void setRemainder(BigDecimal remainder) {
        this.remainder = remainder;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static final String ID = "id";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String TYPE = "type";

    public static final String REMAINDER = "remainder";

    public static final String USER_ID = "user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserRemainder{" +
        "id=" + id +
        ", publishTime=" + publishTime +
        ", type=" + type +
        ", remainder=" + remainder +
        ", userId=" + userId +
        "}";
    }
}
