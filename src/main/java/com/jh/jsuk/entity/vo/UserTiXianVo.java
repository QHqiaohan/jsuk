package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.UserTiXian;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 平台-用户提现
 */
@Setter
@Getter
@ToString
public class UserTiXianVo extends UserTiXian implements Serializable {

    private String userName;

}
