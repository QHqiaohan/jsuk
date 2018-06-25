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
    private Integer isRead;

    /**
     * 0:未推送 1：已推送
     */
    private Integer isPushed;

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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsPushed() {
        return isPushed;
    }

    public void setIsPushed(Integer isPushed) {
        this.isPushed = isPushed;
    }

    public static final String ID = "id";

    public static final String NEWS_ID = "news_id";

    public static final String RECEIVED_ID = "received_id";

    public static final String IS_READ = "is_read";

    public static final String IS_PUSHED = "is_pushed";

    @Override
    public String toString() {
        return "NewsUser{" +
                "id=" + id +
                ", newsId=" + newsId +
                ", receivedId=" + receivedId +
                ", isRead=" + isRead +
                ", isPushed=" + isPushed +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
