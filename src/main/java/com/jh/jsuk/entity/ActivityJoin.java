package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 参与活动表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_activity_join")
public class ActivityJoin extends Model<ActivityJoin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 0=待付款,1=进行中,2=完成
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String ACTIVITY_ID = "activity_id";

    public static final String USER_ID = "user_id";

    public static final String IS_DEL = "is_del";

    public static final String STATUS = "status";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityJoin{" +
        "id=" + id +
        ", activityId=" + activityId +
        ", userId=" + userId +
        ", isDel=" + isDel +
        ", status=" + status +
        "}";
    }
}
