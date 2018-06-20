package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 模块分类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_modular_portal")
public class ModularPortal extends Model<ModularPortal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 模块名字
     */
    private String name;
    /**
     * 图片地址
     */
    private String logo;
    /**
     * 父模块ID,当id=0时说明是根模块
     */
    private Integer parentId;
    /**
     * 排序编号,展示顺序,越大越靠前
     */
    private String sortOrder;
    /**
     * 1=有效,0=无效
     */
    private Integer status;
    /**
     * 类型,暂未使用
     */
    private Integer type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public static final String NAME = "name";

    public static final String LOGO = "logo";

    public static final String PARENT_ID = "parent_id";

    public static final String SORT_ORDER = "sort_order";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ModularPortal{" +
        "id=" + id +
        ", name=" + name +
        ", logo=" + logo +
        ", parentId=" + parentId +
        ", sortOrder=" + sortOrder +
        ", status=" + status +
        ", type=" + type +
        ", publishTime=" + publishTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
