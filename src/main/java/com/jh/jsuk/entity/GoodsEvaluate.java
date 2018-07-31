package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
@TableName("js_goods_evaluate")
public class GoodsEvaluate extends Model<GoodsEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty(name = "用户ID", value = "userId")
    private Integer userId;
    /**
     * 用户ID
     */
    private String userName;
    /**
     * 用户姓名
     */
    @ApiModelProperty(name = "用户姓名", value = "name")
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 商品Id
     */
    @ApiModelProperty(name = "商品Id", required = true, value = "goodsId")
    private Integer goodsId;
    /**
     * 星级
     */
    private Integer starNumber;
    /**
     * 内容
     */
    @ApiModelProperty(name = "内容", required = true, value = "content")
    private String content;
    /**
     * 图片
     */
    @ApiModelProperty(name = "图片", required = true, value = "image")
    private String image;
    /**
     * 评价等级
     */
    @ApiModelProperty(name = "1=好评,2=中评,3=差评", value = "type")
    private Integer type;
    /**
     * 0:否 1:是
     */
    private Integer isDel;

    /**
     * 0:否 1:是
     */
    private Integer isShow;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String NAME = "name";

    public static final String CREATE_TIME = "create_time";

    public static final String GOODS_ID = "goods_id";

    public static final String STAR_NUMBER = "star_number";

    public static final String IS_SHOW = "is_show";

    public static final String CONTENT = "content";

    public static final String IMAGE = "image";

    public static final String IS_DEL = "is_del";

    public static final String TYPE = "type";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
