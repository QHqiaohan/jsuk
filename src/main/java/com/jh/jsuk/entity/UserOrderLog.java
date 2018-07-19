package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单跟踪日志
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
@TableName("js_user_order_log")
public class UserOrderLog extends Model<UserOrderLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 日志时间
     */
    private Date logTime;
    /**
     * 日志内容
     */
    private String logMsg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public static final String ID = "id";

    public static final String ORDER_ID = "order_id";

    public static final String LOG_TIME = "log_time";

    public static final String LOG_MSG = "log_msg";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserOrderLog{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", logTime=" + logTime +
        ", logMsg=" + logMsg +
        "}";
    }
}
