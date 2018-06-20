package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 省份
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_provinces")
public class Provinces extends Model<Provinces> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 省份名称
     */
    private String provinceName;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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

    public static final String PROVINCE_NAME = "province_name";

    public static final String ALIAS = "alias";

    public static final String PINYIN = "pinyin";

    public static final String ABBR = "abbr";

    public static final String ZIP = "zip";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Provinces{" +
        "id=" + id +
        ", provinceName=" + provinceName +
        ", alias=" + alias +
        ", pinyin=" + pinyin +
        ", abbr=" + abbr +
        ", zip=" + zip +
        "}";
    }
}
