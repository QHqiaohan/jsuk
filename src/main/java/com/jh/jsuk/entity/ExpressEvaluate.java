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

/**
 * <p>
 * 
 * </p>
 *
 * @author tj
 * @since 2018-08-19
 */
@Setter
@Getter
@ToString
@TableName("js_express_evaluate")
public class ExpressEvaluate extends Model<ExpressEvaluate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id", name = "expressId")
    private Integer expressId;
    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容", name = "content")
    private String content;
    /**
     * 0 差评 1中评  2 好评
     */
    @ApiModelProperty(value = "0 差评 1中评  2 好评", name = "evaluateType")
    private Integer evaluateType;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", name = "image")
    private String image;


    public static final String ID = "id";

    public static final String EXPRESS_ID = "express_id";

    public static final String CONTENT = "content";

    public static final String EVALUATE_TYPE = "evaluate_type";

    public static final String IMAGE = "image";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
