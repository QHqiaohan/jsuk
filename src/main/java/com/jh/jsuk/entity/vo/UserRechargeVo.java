package com.jh.jsuk.entity.vo;

import com.pingplusplus.model.Charge;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/10 19:52
 * Description:会员充值
 */
@Data
public class UserRechargeVo implements Serializable {

    private Charge charge;

    private Integer remainderId;

    private Integer rechargeRecordId;
}
