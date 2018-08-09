package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 便捷生活&二手市场-评论
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_market_comment")
public class MarketComment extends Model<MarketComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 创建时间
     */
    private Date publishTime;
    /**
     * 回复的评论ID
     */
    private Integer commentId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 活动ID
     */
    private Integer activityId;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;

    //1=二手市场，2=乡村旅游，3=便捷生活
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public static final String ID = "id";

    public static final String COMMENT = "comment";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String COMMENT_ID = "comment_id";

    public static final String USER_ID = "user_id";

    public static final String ACTIVITY_ID = "activity_id";

    public static final String IS_DEL = "is_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MarketComment{" +
        "id=" + id +
        ", comment=" + comment +
        ", publishTime=" + publishTime +
        ", commentId=" + commentId +
        ", userId=" + userId +
        ", activityId=" + activityId +
        ", isDel=" + isDel +
        "}";
    }
}
