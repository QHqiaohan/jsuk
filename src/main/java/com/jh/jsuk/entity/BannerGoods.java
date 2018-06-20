package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * Banner商品表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_banner_goods")
public class BannerGoods extends Model<BannerGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer bannerId;
    private Integer goodsId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public static final String ID = "id";

    public static final String BANNER_ID = "banner_id";

    public static final String GOODS_ID = "goods_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BannerGoods{" +
        "id=" + id +
        ", bannerId=" + bannerId +
        ", goodsId=" + goodsId +
        "}";
    }
}
