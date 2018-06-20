package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 首页2个小活动表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_activity_small")
public class ActivitySmall extends Model<ActivitySmall> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 小标题
     */
    private String tag;
    /**
     * 1=未删除,0=删除
     */
    private Integer isDel;
    /**
     * 图片
     */
    private String pic;
    /**
     * 1=进行中,0=结束
     */
    private Integer status;
    /**
     * 数值越大越靠前
     */
    private String rank;


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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String TAG = "tag";

    public static final String IS_DEL = "is_del";

    public static final String PIC = "pic";

    public static final String STATUS = "status";

    public static final String RANK = "rank";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ActivitySmall{" +
        "id=" + id +
        ", title=" + title +
        ", tag=" + tag +
        ", isDel=" + isDel +
        ", pic=" + pic +
        ", status=" + status +
        ", rank=" + rank +
        "}";
    }
}
