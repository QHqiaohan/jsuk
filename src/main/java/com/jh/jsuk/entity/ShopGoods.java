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
 * 商品表
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
@TableName("js_shop_goods")
public class ShopGoods extends Model<ShopGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id", name = "shopId")
    private Integer shopId;
    /**
     * 属性ID
     */
    @ApiModelProperty(value = "属性ID", name = "attributeId")
    private Integer attributeId;
    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID", name = "brandId")
    private Integer brandId;
    /**
     * 模块ID
     */
    @ApiModelProperty(value = "模块ID", name = "shopModularId")
    private Integer shopModularId;
    /**
     * 是否推荐,0=不推荐,1=推荐
     */
    @ApiModelProperty(value = "是否推荐,0=不推荐,1=推荐", name = "isRecommend")
    private Integer isRecommend;
    /**
     * 商品标签id
     */
    @ApiModelProperty(value = "商品标签id", name = "goodsLabelId")
    private Integer goodsLabelId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", name = "goodsName")
    private String goodsName;
    /**
     * 商品详情图片
     */
    @ApiModelProperty(value = "商品详情图片", name = "goodsImg")
    private String goodsImg;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述", name = "goodsDesc")
    private String goodsDesc;
    /**
     * 商品状态.0-待审核 1-在售 2-下架
     */
    @ApiModelProperty(value = "商品状态.0-待审核 1-在售 2-下架", name = "status")
    private Integer status;
    /**
     * 1删除  0未删除
     */
    @ApiModelProperty(value = "1删除  0未删除", name = "isDel")
    private Integer isDel;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 商品头图
     */
    @ApiModelProperty(value = "商品头图", name = "mainImage")
    private String mainImage;
    /**
     * 商品备注
     */
    @ApiModelProperty(value = "商品备注", name = "goodsBreak")
    private String goodsBreak;
    /**
     * 销售量
     */
    @ApiModelProperty(value = "销售量", name = "saleAmont")
    private Integer saleAmont;
    /**
     * 类型,1=包邮,2=促销,3=新品
     */
    @ApiModelProperty(value = "类型,1=包邮,2=促销,3=新品", name = "goodsType")
    private Integer goodsType;
    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID", name = "categoryId")
    private Integer categoryId;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", name = "address")
    private String address;

    public static final String ID = "id";

    public static final String SHOP_ID = "shop_id";

    public static final String SHOP_MODULAR_ID = "shop_modular_id";

    public static final String BRAND_ID = "brand_id";

    public static final String IS_RECOMMEND = "is_recommend";

    public static final String GOODS_LABEL_ID = "goods_label_id";

    public static final String GOODS_NAME = "goods_name";

    public static final String GOODS_IMG = "goods_img";

    public static final String GOODS_DESC = "goods_desc";

    public static final String STATUS = "status";

    public static final String IS_DEL = "is_del";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String MAIN_IMAGE = "main_image";

    public static final String GOODS_BREAK = "goods_break";

    public static final String SALE_AMONT = "sale_amont";

    public static final String GOODS_TYPE = "goods_type";

    public static final String ATTRIBUTE_ID = "attribute_id";

    public static final String CATEGORY_ID = "category_id";

    public static final String ADDRESS = "address";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopGoods{" +
                "id=" + id +
                ", shopId=" + shopId +
                ", attributeId=" + attributeId +
                ", brandId=" + brandId +
                ", shopModularId=" + shopModularId +
                ", isRecommend=" + isRecommend +
                ", goodsLabelId=" + goodsLabelId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", status=" + status +
                ", isDel=" + isDel +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", mainImage='" + mainImage + '\'' +
                ", goodsBreak='" + goodsBreak + '\'' +
                ", saleAmont=" + saleAmont +
                ", goodsType=" + goodsType +
                ", categoryId=" + categoryId +
                ", address='" + address + '\'' +
                '}';
    }
}
