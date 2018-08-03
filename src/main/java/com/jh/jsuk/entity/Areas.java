package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 区域
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_areas")
public class Areas extends Model<Areas> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 城市ID
     */
    private Integer cityId;
    /**
     * 地区名称
     */
    private String areaName;
    /**
     * 别名
     */
    private String alias;
    private String pinyin;
    /**
     * 字母首拼
     */
    private String abbr;
    private Integer zip;
    /**
     * 是否删除
     */
    private Integer isDel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public static final String CITY_ID = "city_id";

    public static final String AREA_NAME = "area_name";

    public static final String ALIAS = "alias";

    public static final String PINYIN = "pinyin";

    public static final String ABBR = "abbr";

    public static final String ZIP = "zip";

    public static final String IS_DEL = "is_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Areas{" +
            "id=" + id +
            ", cityId=" + cityId +
            ", areaName=" + areaName +
            ", alias=" + alias +
            ", pinyin=" + pinyin +
            ", abbr=" + abbr +
            ", zip=" + zip +
            "}";
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
