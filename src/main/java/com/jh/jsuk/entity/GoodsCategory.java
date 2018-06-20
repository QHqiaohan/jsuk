package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品分类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_goods_category")
public class GoodsCategory extends Model<GoodsCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 类别Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */
    private Integer parentId;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 类别状态1-正常,2-已废弃
     */
    private Integer status;
    /**
     * 排序编号,同类展示顺序,数值相等则自然排序
     */
    private Integer sortOrder;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 图片
     */
    private String pic;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public static final String ID = "id";

    public static final String PARENT_ID = "parent_id";

    public static final String NAME = "name";

    public static final String STATUS = "status";

    public static final String SORT_ORDER = "sort_order";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String PIC = "pic";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsCategory{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", name=" + name +
        ", status=" + status +
        ", sortOrder=" + sortOrder +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", pic=" + pic +
        "}";
    }
}
