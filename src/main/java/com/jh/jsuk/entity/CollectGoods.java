package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户商品收藏
 * </p>
 *
 * @author tj123
 * @since 2018-06-25
 */
@Setter
@Getter
@ToString
//@Accessors(chain = true)
@TableName("js_collect_goods")
public class CollectGoods extends Model<CollectGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer goodsId;
    private Integer userId;
    private Date publishTime;


    public static final String ID = "id";
    public static final String GOODS_ID = "goods_id";
    public static final String USER_ID = "user_id";
    public static final String PUBLISH_TIME = "publish_time";


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
