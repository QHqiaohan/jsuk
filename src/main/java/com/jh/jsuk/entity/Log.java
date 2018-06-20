package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_log")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 操作人姓名
     */
    private String userName;
    /**
     * 请求路径
     */
    private String servletPath;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 操作时间
     */
    private String operationTime;
    /**
     * 操作人ID
     */
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String SERVLET_PATH = "servlet_path";

    public static final String PARAM = "param";

    public static final String OPERATION_TIME = "operation_time";

    public static final String USER_ID = "user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Log{" +
        "id=" + id +
        ", userName=" + userName +
        ", servletPath=" + servletPath +
        ", param=" + param +
        ", operationTime=" + operationTime +
        ", userId=" + userId +
        "}";
    }
}
