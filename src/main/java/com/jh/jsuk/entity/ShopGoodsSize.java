package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 商品规格
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_shop_goods_size")
public class ShopGoodsSize extends Model<ShopGoodsSize> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", name = "shopGoodsId")
    private Integer shopGoodsId;
    /**
     * 规格名称
     */
    @ApiModelProperty(value = "规格名称", name = "sizeName")
    private String sizeName;
    /**
     * 库存
     */
    @ApiModelProperty(value = "库存", name = "stock")
    private Integer stock;
    /**
     * 原价
     */
    @ApiModelProperty(value = "原价", name = "originalPrice")
    private String originalPrice;
    /**
     * 销售价格
     */
    @ApiModelProperty(value = "销售价格", name = "salesPrice")
    private String salesPrice;
    /**
     * 重量
     */
    @ApiModelProperty(value = "重量", name = "weight")
    private String weight;
    /**
     * 体积
     */
    @ApiModelProperty(value = "体积", name = "volume")
    private String volume;
    /**
     * 运费 0=包邮
     */
    @ApiModelProperty(value = "运费 0=包邮", name = "freight")
    private String freight;
    /**
     * 满多少包邮
     */
    @ApiModelProperty(value = "满多少包邮", name = "fullFreight")
    private String fullFreight;
    /**
     * 状态,备用
     */
    @ApiModelProperty(value = "预留字段", name = "status")
    private Integer status;
    /**
     * 是否满减,1=是,0=否
     */
    @ApiModelProperty(value = "是否满减,1=是,0=否", name = "type")
    private Integer type;
    /**
     * 是否删除,0=未删除,1=删除
     */
    @ApiModelProperty(value = "是否删除,0=未删除,1=删除", name = "isDel")
    private Integer isDel;
    /**
     * 图片
     */
    @ApiModelProperty(value = "图片", name = "img")
    private String img;
    /**
     * 商品品牌
     */
    @ApiModelProperty(value = "商品品牌", name = "brand")
    private String brand;
    /**
     * 赠送积分
     */
    @ApiModelProperty(value = "赠送积分", name = "sendJf")
    private String sendJf;
    /**
     * 抵扣积分
     */
    @ApiModelProperty(value = "抵扣积分", name = "deductibleJf")
    private String deductibleJf;
    /**
     * 商品类别
     */
    @ApiModelProperty(value = "商品类别", name = "goodsTypeId")
    private Integer goodsTypeId;
    /**
     * 货号
     */
    @ApiModelProperty(value = "货号", name = "goodsNo")
    private String goodsNo;
    /**
     * 秒杀价
     */
    @ApiModelProperty(value = "秒杀价", name = "killPrice")
    private String killPrice;
    /**
     * 秒杀库存
     */
    @ApiModelProperty(value = "秒杀库存", name = "killStock")
    private Integer killStock;

    @ApiModelProperty(value = "商品详情", name = "detailInfo")
    private String detailInfo;

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopGoodsId() {
        return shopGoodsId;
    }

    public void setShopGoodsId(Integer shopGoodsId) {
        this.shopGoodsId = shopGoodsId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }



    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getFullFreight() {
        return fullFreight;
    }

    public void setFullFreight(String fullFreight) {
        this.fullFreight = fullFreight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSendJf() {
        return sendJf;
    }

    public void setSendJf(String sendJf) {
        this.sendJf = sendJf;
    }

    public String getDeductibleJf() {
        return deductibleJf;
    }

    public void setDeductibleJf(String deductibleJf) {
        this.deductibleJf = deductibleJf;
    }

    public Integer getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(Integer goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(String killPrice) {
        this.killPrice = killPrice;
    }

    public Integer getKillStock() {
        return killStock;
    }

    public void setKillStock(Integer killStock) {
        this.killStock = killStock;
    }

    public static final String ID = "id";

    public static final String SHOP_GOODS_ID = "shop_goods_id";

    public static final String SIZE_NAME = "size_name";

    public static final String STOCK = "stock";

    public static final String ORIGINAL_PRICE = "original_price";

    public static final String SALES_PRICE = "sales_price";

    public static final String WEIGHT = "weight";

    public static final String VOLUME = "volume";

    public static final String FREIGHT = "freight";

    public static final String FULL_FREIGHT = "full_freight";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String IS_DEL = "is_del";

    public static final String IMG = "img";

    public static final String BRAND = "brand";

    public static final String SEND_JF = "send_jf";

    public static final String DEDUCTIBLE_JF = "deductible_jf";

    public static final String GOODS_TYPE_ID = "goods_type_id";

    public static final String GOODS_NO = "goods_no";

    public static final String KILL_PRICE = "kill_price";

    public static final String KILL_STOCK = "kill_stock";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopGoodsSize{" +
                "id=" + id +
                ", shopGoodsId=" + shopGoodsId +
                ", sizeName='" + sizeName + '\'' +
                ", stock=" + stock +
                ", originalPrice='" + originalPrice + '\'' +
                ", salesPrice='" + salesPrice + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                ", freight='" + freight + '\'' +
                ", full_freight='" + fullFreight + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", isDel=" + isDel +
                ", img='" + img + '\'' +
                ", brand='" + brand + '\'' +
                ", sendJf='" + sendJf + '\'' +
                ", deductibleJf='" + deductibleJf + '\'' +
                ", goodsTypeId=" + goodsTypeId +
                ", goodsNo='" + goodsNo + '\'' +
                ", killPrice='" + killPrice + '\'' +
                ", killStock=" + killStock +
                '}';
    }
}
