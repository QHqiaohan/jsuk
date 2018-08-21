package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.jh.jsuk.envm.ShopMoneyType;
import com.jh.jsuk.envm.UserRemainderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商家端-余额
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
@TableName("js_shop_money")
public class ShopMoney extends Model<ShopMoney> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer shopId;
    /**
     * 商家余额
     */
    private String money;
    /**
     * 类型,0=消费,1=收入
     */
    private ShopMoneyType type;
    /**
     * 操作时间
     */
    private Date publishTime;

    private UserRemainderStatus status;

    private String platformNo;

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String MONEY = "money";

    public static final String TYPE = "type";

    public static final String STATUS = "status";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String PLATFORM_NO = "platform_no";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
