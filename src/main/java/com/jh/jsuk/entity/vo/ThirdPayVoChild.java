package com.jh.jsuk.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/11 14:04
 * Description:
 */
@Getter
@Setter
public class ThirdPayVoChild implements Serializable {
    private ThirdPayVo payVo;
    private Integer userRemainderId;
    private Integer userRechargeRecordId;

}
