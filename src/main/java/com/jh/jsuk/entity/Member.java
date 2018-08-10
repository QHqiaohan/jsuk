package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 会员价格配置
 * </p>
 *
 * @author tj
 * @since 2018-08-10
 */
@Setter
@Getter
@ToString
@TableName("js_sys_member")
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员价格
     */
    private String memberPrice;
    /**
     * 会员折扣
     */
    private BigDecimal memberDiscount;
    private Date createTime;
    private Date updateTime;

    public static final String ID = "id";

    public static final String MEMBER_NAME = "member_name";

    public static final String MEMBER_PRICE = "member_price";

    public static final String MEMBER_DISCOUNT = "member_discount";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
