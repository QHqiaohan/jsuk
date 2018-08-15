package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 城市天气
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Setter
@Getter
@ToString
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

    public static final String AREA_ID = "area_id";

    public static final String COUNTY_NAME = "county_name";

    @Override
    protected Serializable pkVal() {
        return this.areaId;
    }

}
