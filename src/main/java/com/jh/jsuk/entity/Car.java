package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 车类别
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@TableName("js_car")
public class Car extends Model<Car> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类ID
     */
    private Integer classId;
    /**
     * 车辆名称
     */
    private String carName;
    /**
     * 数值越大越靠前
     */
    private String rank;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public static final String ID = "id";

    public static final String CLASS_ID = "class_id";

    public static final String CAR_NAME = "car_name";

    public static final String RANK = "rank";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Car{" +
        "id=" + id +
        ", classId=" + classId +
        ", carName=" + carName +
        ", rank=" + rank +
        "}";
    }
}
