package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 专题精选商品
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_special_goods")
public class SpecialGoods extends Model<SpecialGoods> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 商品ID
     */
    private Integer goodsId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public static final String ID = "id";

    public static final String GOODS_ID = "goods_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SpecialGoods{" +
        "id=" + id +
        ", goodsId=" + goodsId +
        "}";
    }
}
