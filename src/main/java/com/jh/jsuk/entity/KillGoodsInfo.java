package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 秒杀商品信息
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_kill_goods_info")
public class KillGoodsInfo extends Model<KillGoodsInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 秒杀ID
     */
    private Integer rushId;
    /**
     * 商品ID
     */
    private Integer goodsId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 秒杀规格Id
     */
    private Integer goodsSizeId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRushId() {
        return rushId;
    }

    public void setRushId(Integer rushId) {
        this.rushId = rushId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsSizeId() {
        return goodsSizeId;
    }

    public void setGoodsSizeId(Integer goodsSizeId) {
        this.goodsSizeId = goodsSizeId;
    }

    public static final String ID = "id";

    public static final String RUSH_ID = "rush_id";

    public static final String GOODS_ID = "goods_id";

    public static final String CREATE_TIME = "create_time";

    public static final String GOODS_SIZE_ID = "goods_size_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "KillGoodsInfo{" +
        "id=" + id +
        ", rushId=" + rushId +
        ", goodsId=" + goodsId +
        ", createTime=" + createTime +
        ", goodsSizeId=" + goodsSizeId +
        "}";
    }
}
