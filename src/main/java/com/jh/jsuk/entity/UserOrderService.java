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
 * 订单申请售后
 * </p>
 *
 * @author tj
 * @since 2018-07-31
 */
@Setter
@Getter
@ToString
@TableName("js_user_order_service")
public class UserOrderService extends Model<UserOrderService> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 服务单号
     */
    private String serviceCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 退款原因
     */
    private String refundReason;
    /**
     * 售后类型,1=仅退款,2=退货退款,3=换货
     */
    private Integer type;
    /**
     * 0:待处理 1：处理中 2：已完成 3：已拒绝
     */
    private Integer status;
    /**
     * 具体原因
     */
    private String content;
    /**
     * 图片
     */
    private String image;
    /**
     * 退款金额
     */
    private String price;
    private Integer goodsId;
    /**
     * 规格ID
     */
    private Integer sizeId;
    /**
     * 地址ID
     */
    private Integer addressId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 完成时间
     */
    private Date completeTime;

    public static final String ID = "id";

    public static final String SERVICE_CODE = "service_code";

    public static final String USER_NAME = "user_name";

    public static final String USER_PHONE = "user_phone";

    public static final String ORDER_ID = "order_id";

    public static final String SHOP_ID = "shop_id";

    public static final String REFUND_REASON = "refund_reason";

    public static final String TYPE = "type";

    public static final String STATUS = "status";

    public static final String CONTENT = "content";

    public static final String IMAGE = "image";

    public static final String PRICE = "price";

    public static final String GOODS_ID = "goods_id";

    public static final String SIZE_ID = "size_id";

    public static final String ADDRESS_ID = "address_id";

    public static final String CREATE_TIME = "create_time";

    public static final String COMPLETE_TIME = "complete_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
