package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("js_news_user")
public class NewsUser extends Model<NewsUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 消息ID
     */
    private Integer newsId;
    /**
     * 接收人Id
     */
    private Integer receivedId;
    /**
     * 0;未读 1:已读
     */
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getReceivedId() {
        return receivedId;
    }

    public void setReceivedId(Integer receivedId) {
        this.receivedId = receivedId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String NEWS_ID = "news_id";

    public static final String RECEIVED_ID = "received_id";

    public static final String STATUS = "status";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "NewsUser{" +
        "id=" + id +
        ", newsId=" + newsId +
        ", receivedId=" + receivedId +
        ", status=" + status +
        "}";
    }
}
