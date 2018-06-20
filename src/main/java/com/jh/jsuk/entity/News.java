package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户消息表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 消息类型 1:系统消息 2:二手市场消息
     */
    private Integer newsType;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 商品Id
     */
    private Integer goodsId;
    /**
     * 图片
     */
    private String image;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发送人ID
     */
    private String sendUserId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public static final String ID = "id";

    public static final String NEWS_TYPE = "news_type";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String GOODS_ID = "goods_id";

    public static final String IMAGE = "image";

    public static final String CREATE_TIME = "create_time";

    public static final String SEND_USER_ID = "send_user_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "News{" +
        "id=" + id +
        ", newsType=" + newsType +
        ", title=" + title +
        ", content=" + content +
        ", goodsId=" + goodsId +
        ", image=" + image +
        ", createTime=" + createTime +
        ", sendUserId=" + sendUserId +
        "}";
    }
}
