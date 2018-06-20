package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 城市天气
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_sys_weather_city")
public class WeatherCity extends Model<WeatherCity> {

    private static final long serialVersionUID = 1L;

    /**
     * 城市ID
     */
    private Integer areaId;
    /**
     * 城市名称
     */
    private String countyName;


    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public static final String AREA_ID = "area_id";

    public static final String COUNTY_NAME = "county_name";

    @Override
    protected Serializable pkVal() {
        return this.areaId;
    }

    @Override
    public String toString() {
        return "WeatherCity{" +
        "areaId=" + areaId +
        ", countyName=" + countyName +
        "}";
    }
}
