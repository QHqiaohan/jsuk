package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 便捷生活-交易区域
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_activity_transaction_area")
public class ActivityTransactionArea extends Model<ActivityTransactionArea> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 交易区域
     */
    private String transactionArea;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 维度
     */
    private String latitude;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionArea() {
        return transactionArea;
    }

    public void setTransactionArea(String transactionArea) {
        this.transactionArea = transactionArea;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public static final String ID = "id";

    public static final String TRANSACTION_AREA = "transaction_area";

    public static final String IS_DEL = "is_del";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivityTransactionArea{" +
        "id=" + id +
        ", transactionArea=" + transactionArea +
        ", isDel=" + isDel +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        "}";
    }
}
