package com.jh.jsuk.entity;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品类型
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_goods_type")
public class ShopGoodsType extends Model<ShopGoodsType> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 商户ID
     */
    private Integer managerId;
    /**
     * 类型名称
     */
    private String name;
    /**
     * 规格ID
     */
    private Integer goodsSizeId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 0: 否 1:是
     */
    private Integer idDel;
    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(Integer goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIdDel() {
        return idDel;
    }

    public void setIdDel(Integer idDel) {
        this.idDel = idDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String MANAGER_ID = "manager_id";

    public static final String NAME = "name";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    public static final String SORT = "sort";

    public static final String ID_DEL = "id_del";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopGoodsType{" +
        "id=" + id +
        ", managerId=" + managerId +
        ", name=" + name +
        ", goodsSizeId=" + goodsSizeId +
        ", sort=" + sort +
        ", idDel=" + idDel +
        ", createTime=" + createTime +
        "}";
    }
}
