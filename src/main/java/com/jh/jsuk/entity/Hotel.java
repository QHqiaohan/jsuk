package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 乡村旅游-酒店住宿
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_hotel")
public class Hotel extends Model<Hotel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 酒店名称
     */
    private String name;
    /**
     * 酒店展示主图
     */
    private String pic;
    /**
     * 数值越大越靠前
     */
    private String rank;
    /**
     * 是否推荐 1=推荐 0=不推荐
     */
    private Integer statu;
    /**
     * 模块ID
     */
    private Integer modularId;
    private Date publishTime;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;


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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Integer getModularId() {
        return modularId;
    }

    public void setModularId(Integer modularId) {
        this.modularId = modularId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PIC = "pic";

    public static final String RANK = "rank";

    public static final String STATU = "statu";

    public static final String MODULAR_ID = "modular_id";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String IS_DEL = "is_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Hotel{" +
        "id=" + id +
        ", name=" + name +
        ", pic=" + pic +
        ", rank=" + rank +
        ", statu=" + statu +
        ", modularId=" + modularId +
        ", publishTime=" + publishTime +
        ", isDel=" + isDel +
        "}";
    }
}
