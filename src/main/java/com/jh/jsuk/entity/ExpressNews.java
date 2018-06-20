package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 快报
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_express_news")
public class ExpressNews extends Model<ExpressNews> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 1=首页,2=城乡优购 3=聚鲜U客 4=本地商城
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 图片
     */
    private String image;
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 创建时间
     */
    private Date publishTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String TYPE = "type";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final String IS_DEL = "is_del";

    public static final String IMAGE = "image";

    public static final String USER_ID = "user_id";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExpressNews{" +
        "id=" + id +
        ", type=" + type +
        ", title=" + title +
        ", content=" + content +
        ", isDel=" + isDel +
        ", image=" + image +
        ", userId=" + userId +
        ", publishTime=" + publishTime +
        "}";
    }
}
