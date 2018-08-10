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
 * 
 * </p>
 *
 * @author tj
 * @since 2018-08-07
 */
@Setter
@Getter
@ToString
@TableName("js_user_invite_log")
public class UserInviteLog extends Model<UserInviteLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String log;

    private Integer userId;

    private Integer inviteUserId;

    private BigDecimal money;

    private Date createTime;

    //是否提现,1:是，0：否
    private Integer isTixian;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsTixian() {
        return isTixian;
    }

    public void setIsTixian(Integer isTixian) {
        this.isTixian = isTixian;
    }

    public static final String ID = "id";

    public static final String LOG = "log";

    public static final String USER_ID = "user_id";

    public static final String INVITE_USER_ID = "invite_user_id";

    public static final String MONEY = "money";

    public static final String CREATE_TIME = "create_time";

    public static final String IS_TIXIAN = "is_tixian";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
