package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.UserRemainderStatus;
import com.jh.jsuk.envm.UserRemainderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户余额表
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@Setter
@Getter
@ToString
@TableName("js_user_remainder")
public class UserRemainder extends Model<UserRemainder> {

    public static final String ID = "id";
    public static final String CREATE_TIME = "create_time";
    public static final String TYPE = "type";
    public static final String REMAINDER = "remainder";
    public static final String USER_ID = "user_id";
    public static final String STATUS = "status";
    public static final String MEMBER_ID = "member_id";
    public static final String ORDER_NUM = "order_num";

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 类型,1=充值,-1=消费,0=其他
     */
    private UserRemainderType type;
    /**
     * 金额
     */
    private BigDecimal remainder;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 0:待支付 1:支付失败 2:支付成功
     */
    private UserRemainderStatus status;
    /**
     * 会员配置ID
     */
    private Integer memberId;
    /**
     * 单号
     */
    private String orderNum;
    /**
     * 平台流水号
     */
    private String platformNumber;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
