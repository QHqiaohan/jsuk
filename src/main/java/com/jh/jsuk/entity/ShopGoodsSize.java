package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

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
    private Integer shopGoodsId;
    /**
     * 规格名称
     */
    private String sizeName;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 原价
     */
    private String originalPrice;
    /**
     * 销售价格
     */
    private String salesPrice;
    /**
     * 重量
     */
    private String weight;
    /**
     * 体积
     */
    private String volume;
    /**
     * 运费 0=包邮
     */
    private String freight;
    /**
     * 满多少包邮
     */
    private String fullFreight;
    /**
     * 状态,备用
     */
    private Integer status;
    /**
     * 类型,备用
     */
    private Integer type;
    /**
     * 是否删除,0=未删除,1=删除
     */
    private Integer isDel;
    /**
     * 图片
     */
    private String img;
    /**
     * 商品品牌
     */
    private String brand;
    /**
     * 赠送积分
     */
    private String sendJf;
    /**
     * 抵扣积分
     */
    private String deductibleJf;
    /**
     * 商品类别
     */
    private Integer goodsTypeId;
    /**
     * 货号
     */
    private String goodsNo;
    /**
     * 秒杀价
     */
    private String killPrice;
    /**
     * 秒杀库存
     */
    private Integer killStock;


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
