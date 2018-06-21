package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 邀请用户
 * </p>
 *
 * @author xuchuruo
 * @since 2018-05-23
 */
@TableName("js_user_invitation_pay")
public class UserInvitationPay extends Model<UserInvitationPay> {

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String INVITATION_ID = "invitation_id";
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 被邀请用户ID
     */
    private Integer userId;
    /**
     * 邀请人ID
     */
    private Integer invitationId;

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

    public Integer getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Integer invitationId) {
        this.invitationId = invitationId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserInvitationPay{" +
                "id=" + id +
                ", userId=" + userId +
                ", invitationId=" + invitationId +
                "}";
    }
}
