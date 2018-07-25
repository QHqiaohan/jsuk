package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@TableName("js_distribution_detail")
public class DistributionDetail extends Model<DistributionDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private BigDecimal money;
    private String detail;
    private Date publishTime;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String MONEY = "money";

    public static final String DETAIL = "detail";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
