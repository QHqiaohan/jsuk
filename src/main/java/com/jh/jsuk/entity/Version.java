package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 版本信息
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_version")
public class Version extends Model<Version> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 0普通更新  1强制更新
     */
    private Integer status;
    private String version;
    private String code;
    /**
     * 0安卓  1ios
     */
    private Integer osType;
    /**
     * 1 商家端   2 骑手端   3  用户端
     */
    private Integer appType;
    private String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final String ID = "id";

    public static final String STATUS = "status";

    public static final String VERSION = "version";

    public static final String CODE = "code";

    public static final String OS_TYPE = "os_type";

    public static final String APP_TYPE = "app_type";

    public static final String URL = "url";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Version{" +
        "id=" + id +
        ", status=" + status +
        ", version=" + version +
        ", code=" + code +
        ", osType=" + osType +
        ", appType=" + appType +
        ", url=" + url +
        "}";
    }
}
