package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户积分表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_user_integral")
public class UserIntegral extends Model<UserIntegral> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 积分类型 1:购物获赠  -1:购买抵扣
     */
    private Integer integralType;
    /**
     * 积分数
     */
    private Integer integralNumber;
    /**
     * 时间
     */
    private Date craTime;


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

    public Integer getIntegralType() {
        return integralType;
    }

    public void setIntegralType(Integer integralType) {
        this.integralType = integralType;
    }

    public Integer getIntegralNumber() {
        return integralNumber;
    }

    public void setIntegralNumber(Integer integralNumber) {
        this.integralNumber = integralNumber;
    }

    public Date getCraTime() {
        return craTime;
    }

    public void setCraTime(Date craTime) {
        this.craTime = craTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String INTEGRAL_TYPE = "integral_type";

    public static final String INTEGRAL_NUMBER = "integral_number";

    public static final String CRA_TIME = "cra_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserIntegral{" +
        "id=" + id +
        ", userId=" + userId +
        ", integralType=" + integralType +
        ", integralNumber=" + integralNumber +
        ", craTime=" + craTime +
        "}";
    }
}
