package com.jh.jsuk.conf;

/**
 * redis key 统一管理
 */
public class RedisKeys {

    /**
     * 配置
     */
    public static final String SHOP_ORDER_CONFIG = "shop_order_config";

    /**
     * 库存
     */
    public static final String SHOP_GOODS_SIZE_STOCK = "shop_goods_size_stock";

    /**
     * 库存 二级缓存
     */
    public static final String SHOP_GOODS_SIZE_STOCK_LV2 = "shop_goods_size_stock_lv2";

    /**
     * 库存没有了
     */
    public static final String SHOP_GOODS_SIZE_NONE = "shop_goods_size_none";

    /**
     * 规格的秒杀
     */
    public static final String SHOP_GOODS_SIZE_RUSH_BUY = "shop_goods_size_rush_buy";

    /**
     * 规格的秒杀为空
     */
    public static final String SHOP_GOODS_SIZE_RUSH_BUY_NULL = "shop_goods_size_rush_buy_null";

    /**
     * 订单号
     */
    public static final String SHOP_GOODS_ORDER_NUM = "shop_goods_order_num";

    public static String subKey(String redisKey, String key) {
        return redisKey + ":" + key;
    }
}
