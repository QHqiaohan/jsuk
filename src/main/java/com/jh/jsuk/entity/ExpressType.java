package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 快递物品类型
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_express_type")
public class ExpressType extends Model<ExpressType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 物品类型名称
     */
    private String name;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 创建时间
     */
    private Date publishTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String IS_DEL = "is_del";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExpressType{" +
        "id=" + id +
        ", name=" + name +
        ", isDel=" + isDel +
        ", publishTime=" + publishTime +
        "}";
    }
}
