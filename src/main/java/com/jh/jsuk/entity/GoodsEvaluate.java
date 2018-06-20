package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_goods_evaluate")
public class GoodsEvaluate extends Model<GoodsEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 商品Id
     */
    private Integer goodsId;
    /**
     * 星级
     */
    private Integer starNumber;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片
     */
    private String image;
    /**
     * 0:否 1:是
     */
    private Integer idDel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(Integer starNumber) {
        this.starNumber = starNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdDel() {
        return idDel;
    }

    public void setIdDel(Integer idDel) {
        this.idDel = idDel;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String CREATE_TIME = "create_time";

    public static final String GOODS_ID = "goods_id";

    public static final String STAR_NUMBER = "star_number";

    public static final String CONTENT = "content";

    public static final String IMAGE = "image";

    public static final String ID_DEL = "id_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsEvaluate{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", createTime=" + createTime +
        ", goodsId=" + goodsId +
        ", starNumber=" + starNumber +
        ", content=" + content +
        ", image=" + image +
        ", idDel=" + idDel +
        "}";
    }
}
