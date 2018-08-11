package com.jh.jsuk.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/11 14:04
 * Description:
 */
@Data
public class ThirdPayVoChild implements Serializable {

    private ThirdPayVo payVo;

    private Integer userRemainderId;
    
    private Integer userRechargeRecordId;

}
