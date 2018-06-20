package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * Banner
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_banner")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String image;
    /**
     * 内容
     */
    private String content;
    /**
     * 排序 数字越大越靠前
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 
0:首页
 1:城乡优选 
2:特色家乡 
3:会员商城
 4:乡村旅游 
5:二手市场 6:便捷生活 7:分类 8=聚鲜U客 9=本地商城 10=直销平台 11=快递跑腿
     */
    private Integer bannerLocation;
    /**
     * 操作员
     */
    private Integer operatorId;
    /**
     * 是否有效 0:无效 1:有效
     */
    private Integer isValid;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBannerLocation() {
        return bannerLocation;
    }

    public void setBannerLocation(Integer bannerLocation) {
        this.bannerLocation = bannerLocation;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String IMAGE = "image";

    public static final String CONTENT = "content";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    public static final String BANNER_LOCATION = "banner_location";

    public static final String OPERATOR_ID = "operator_id";

    public static final String IS_VALID = "is_valid";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Banner{" +
        "id=" + id +
        ", title=" + title +
        ", image=" + image +
        ", content=" + content +
        ", sort=" + sort +
        ", createTime=" + createTime +
        ", bannerLocation=" + bannerLocation +
        ", operatorId=" + operatorId +
        ", isValid=" + isValid +
        "}";
    }
}
