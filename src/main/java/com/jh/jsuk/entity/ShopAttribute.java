package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 店铺内部的商品分类-属性
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@TableName("js_shop_attribute")
public class ShopAttribute extends Model<ShopAttribute> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 属性名称
     */
    private String name;
    /**
     * 排序,数值越大越靠前
     */
    private Integer sortOrder;


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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String SORT_ORDER = "sort_order";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopAttribute{" +
        "id=" + id +
        ", name=" + name +
        ", sortOrder=" + sortOrder +
        "}";
    }
}
