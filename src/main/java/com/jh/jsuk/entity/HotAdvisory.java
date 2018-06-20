package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 热点资讯
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_hot_advisory")
public class HotAdvisory extends Model<HotAdvisory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String img;
    private String content;
    private Integer rank;
    private Date publishTime;


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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String IMG = "img";

    public static final String CONTENT = "content";

    public static final String RANK = "rank";

    public static final String PUBLISH_TIME = "publish_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HotAdvisory{" +
        "id=" + id +
        ", title=" + title +
        ", img=" + img +
        ", content=" + content +
        ", rank=" + rank +
        ", publishTime=" + publishTime +
        "}";
    }
}
