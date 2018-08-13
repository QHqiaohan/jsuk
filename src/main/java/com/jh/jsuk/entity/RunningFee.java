package com.jh.jsuk.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/9 11:29
 * Description:跑腿费计算规则
 */
@Data
@TableName("js_running_fee")
public class RunningFee extends Model<RunningFee> {

    private static final int DEFAULT_START_DISTANCE = 5;

    private static final int DEFAULT_START_FEE = 10;

    private static final int DEFAULT_ADD_FEE = 5;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 起步距离
     */
    private Integer startDistance;
    /**
     * 起步价
     */
    private Integer startFee;
    /**
     * 每公里增加价格
     */
    private Integer addFee;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public RunningFee defaultVal() {
        if (startFee == null) {
            startFee = DEFAULT_START_FEE;
        }
        if (startDistance == null) {
            startDistance = DEFAULT_START_DISTANCE;
        }
        if (addFee == null) {
            addFee = DEFAULT_ADD_FEE;
        }
        return this;
    }
}
