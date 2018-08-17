package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * <p>
 * 秒杀活动
 * </p>
 *
 * @author tj
 * @since 2018-07-17
 */
@Setter
@Getter
@ToString
@TableName("js_stes")
public class ShopSets extends Model<ShopSets> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer shopid;
    private Integer packagemail;//是否包邮1否2.是
    private Integer integral;//是否使用积分，1否，2是
    private Double money;//满多少包邮
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
