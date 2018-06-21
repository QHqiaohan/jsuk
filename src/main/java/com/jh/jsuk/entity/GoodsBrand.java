package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 类型的详细品牌
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@TableName("js_goods_brand")
public class GoodsBrand extends Model<GoodsBrand> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 二级分类ID
     */
    private Integer categoryId;
    /**
     * 品牌名字
     */
    private String name;
    /**
     * 状态, 1=启用;-1=废弃
     */
    private Integer status;
    /**
     * 排序编号,同类展示顺序,数值相等则自然排序
     */
    private Integer sortOrder;
    /**
     * 创建时间
     */
    private Date publishTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static final String ID = "id";

    public static final String CATEGORY_ID = "category_id";

    public static final String NAME = "name";

    public static final String STATUS = "status";

    public static final String SORT_ORDER = "sort_order";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsBrand{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", status=" + status +
        ", sortOrder=" + sortOrder +
        ", publishTime=" + publishTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
