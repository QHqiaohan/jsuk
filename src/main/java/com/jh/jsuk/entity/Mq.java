package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jh.jsuk.envm.MqStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 消息记录
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
@TableName("js_mq")
public class Mq extends Model<Mq> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发送时间
     */
    private Date sentTime;
    /**
     * 消费时间
     */
    private Date consumeTime;
    /**
     * 1 创建 2 发送 3 消费
     */
    private Integer status;
    /**
     * 队列名称
     */
    private String queueName;
    /**
     * 消息内容
     */
    private String body;
    /**
     * 失败原因
     */
    private String failureReason;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public Date getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Date consumeTime) {
        this.consumeTime = consumeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public static final String ID = "id";

    public static final String CREATE_TIME = "create_time";

    public static final String SENT_TIME = "sent_time";

    public static final String CONSUME_TIME = "consume_time";

    public static final String STATUS = "status";

    public static final String QUEUE_NAME = "queue_name";

    public static final String BODY = "body";

    public static final String FAILURE_REASON = "failure_reason";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Mq{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", sentTime=" + sentTime +
        ", consumeTime=" + consumeTime +
        ", status=" + status +
        ", queueName=" + queueName +
        ", body=" + body +
        ", failureReason=" + failureReason +
        "}";
    }

    @JsonIgnore
    public boolean isConsumed() {
        return MqStatus.CONSUME.equals(status);
    }
}
