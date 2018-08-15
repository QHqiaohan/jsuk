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
 * 区域开通
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@Setter
@Getter
@ToString
@TableName("js_sys_weather_city_open")
public class WeatherCityOpen extends Model<WeatherCityOpen> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer weatherCityId;
    /**
     * 城市状态,0=取消开通,1=已开通
     */
    private Integer status;
    private Date createTime;

    private Integer cityId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeatherCityId() {
        return weatherCityId;
    }

    public void setWeatherCityId(Integer weatherCityId) {
        this.weatherCityId = weatherCityId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String WEATHER_CITY_ID = "weather_city_id";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WeatherCityOpen{" +
        "id=" + id +
        ", weatherCityId=" + weatherCityId +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
