package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.UserCashStatus;
import com.jh.jsuk.envm.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户提现记录
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
@Setter
@Getter
@ToString
@TableName("js_user_ti_xian")
public class UserTiXian extends Model<UserTiXian> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流水号
     */
    private String tiXianNo;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", name = "userId")
    private Integer userId;

    private UserType userType;
    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID", name = "managerId")
    private Integer managerId;
    /**
     * 银行卡ID
     */
    @ApiModelProperty(value = "银行卡ID", name = "bankId")
    private Integer bankId;
    /**
     * 支付宝/收款账号
     */
    @ApiModelProperty(value = "支付宝/收款账号", name = "num")
    private String num;
    /**
     * 0=处理中,1=已提现,2=提现失败,3=取消
     */
    @ApiModelProperty(value = "0=处理中,1=已提现,2=提现失败,3=取消", name = "status")
    private UserCashStatus status;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    /**
     * 提现金额
     */
    @ApiModelProperty(value = "提现金额", name = "price")
    private String price;

    private Date createTime;

    /**
     * 提现渠道
     */
    private String destination;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String MANAGER_ID = "manager_id";

    public static final String BANK_ID = "bank_id";

    public static final String NUM = "num";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String PRICE = "price";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
