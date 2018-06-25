package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 首页-专题精选
 * </p>
 *
 * @author lpf
 * @since 2018-06-25
 */
@TableName("js_special_theme")
public class SpecialTheme extends Model<SpecialTheme> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 首页图
     */
    private String mainImage;
    /**
     * 是否删除 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 数值越大越靠前
     */
    private Integer rank;
    /**
     * 店铺ID
     */
    private Integer shopId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String MAIN_IMAGE = "main_image";

    public static final String IS_DEL = "is_del";

    public static final String RANK = "rank";

    public static final String SHOP_ID = "shop_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SpecialTheme{" +
        "id=" + id +
        ", title=" + title +
        ", mainImage=" + mainImage +
        ", isDel=" + isDel +
        ", rank=" + rank +
        ", shopId=" + shopId +
        "}";
    }
}
