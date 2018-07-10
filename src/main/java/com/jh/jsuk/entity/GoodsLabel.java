package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品标签
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_goods_label")
public class GoodsLabel extends Model<GoodsLabel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签
     */
    private String label;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 1删除  0未删除
     */
    private Integer isDel;
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String LABEL = "label";

    public static final String RANK = "rank";

    public static final String IS_DEL = "is_del";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsLabel{" +
        "id=" + id +
        ", label=" + label +
        ", rank=" + rank +
        ", isDel=" + isDel +
        ", createTime=" + createTime +
        "}";
    }
}
