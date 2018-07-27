package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 订单设置
 * </p>
 *
 * @author tj
 * @since 2018-07-18
 */
@Setter
@Getter
@ToString
@TableName("js_shop_order_config")
public class ShopOrderConfig extends Model<ShopOrderConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商城id
     */
    private Integer shopId;
    /**
     * 秒杀订单超时自动关闭(分钟)
     */
    private Integer rushBuyTimeout;
    /**
     * 超过时间分钟后未付款，订单自动关闭
     */
    private Integer payTimeout;
    /**
     * 发货超过天数自动关闭
     */
    private Integer confirmReceived;
    /**
     * 超过天数自动结束交易
     */
    private Integer autoComplete;
    /**
     * 自动五星好评 天数
     */
    private Integer autoComment;


    private static final Integer DEFAULT_RUSH_BUY_TIMEOUT = 40;

    private static final Integer DEFAULT_PAY_TIMEOUT = 60;

    private static final Integer DEFAULT_CONFIRM_RECEIVED = 15;

    private static final Integer DEFAULT_AUTO_COMMENT = 7;

    private static final Integer DEFAULT_AUTO_COMPLETE = 7;

    /**
     * 获取默认配置
     * @return
     */
    public ShopOrderConfig defaultConfig() {
        ShopOrderConfig config = new ShopOrderConfig();
        config.setRushBuyTimeout(rushBuyTimeout != null ? rushBuyTimeout : DEFAULT_RUSH_BUY_TIMEOUT);
        config.setPayTimeout(payTimeout != null ? payTimeout : DEFAULT_PAY_TIMEOUT);
        config.setConfirmReceived(confirmReceived != null ? confirmReceived : DEFAULT_CONFIRM_RECEIVED);
        config.setAutoComment(autoComment != null ? autoComment : DEFAULT_AUTO_COMMENT);
        config.setAutoComplete(autoComplete != null ? autoComplete : DEFAULT_AUTO_COMPLETE);
        return config;
    }


    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String RUSH_BY_TIMEOUT = "rush_buy_timeout";

    public static final String PAY_TIMEOUT = "pay_timeout";

    public static final String CONFIRM_RECEIVED = "confirm_received";

    public static final String AUTO_COMPLETE = "auto_complete";

    public static final String AUTO_COMMENT = "auto_comment";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
