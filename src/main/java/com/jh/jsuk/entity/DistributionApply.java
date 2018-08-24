package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.DistributionApplyStatus;
import com.jh.jsuk.envm.DistributionApplyType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 配送端提现申请

 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
@TableName("js_distribution_apply")
public class DistributionApply extends Model<DistributionApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer bankId;
    private BigDecimal money;
    /**
     * 0待审核  1通过  2拒绝
     */
    private DistributionApplyStatus status;

    private DistributionApplyType type;

    private Integer userId;
    /**
     * 拒绝原因
     */
    private String desc;
    private Date publishTime;
    /**
     * 手续费
     */
    private BigDecimal poundage;

    private String userNickName;

    private String platformNo;

    public static final String ID = "id";

    public static final String BANK_ID = "bank_id";

    public static final String MONEY = "money";

    public static final String STATUS = "status";

    public static final String USER_ID = "user_id";

    public static final String TYPE = "type";

    public static final String DESC = "desc";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String POUNDAGE = "poundage";

    public static final String PLATFORM_NO = "platform_no";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
