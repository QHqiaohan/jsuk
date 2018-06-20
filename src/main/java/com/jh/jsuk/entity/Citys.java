package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_citys")
public class Citys extends Model<Citys> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 别名
     */
    private String alias;
    /**
     * 省份ID
     */
    private Integer provinceId;
    private String pinyin;
    /**
     * 字母首拼
     */
    private String abbr;
    private Integer zip;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public static final String ID = "id";

    public static final String CITY_NAME = "city_name";

    public static final String ALIAS = "alias";

    public static final String PROVINCE_ID = "province_id";

    public static final String PINYIN = "pinyin";

    public static final String ABBR = "abbr";

    public static final String ZIP = "zip";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Citys{" +
        "id=" + id +
        ", cityName=" + cityName +
        ", alias=" + alias +
        ", provinceId=" + provinceId +
        ", pinyin=" + pinyin +
        ", abbr=" + abbr +
        ", zip=" + zip +
        "}";
    }
}
