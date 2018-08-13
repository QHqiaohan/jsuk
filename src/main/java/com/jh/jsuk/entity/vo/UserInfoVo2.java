package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Author:xyl
 * Date:2018/8/13 11:37
 * Description:
 */
@Getter
@Setter
public class UserInfoVo2 extends User implements Serializable {
    /**
     * 会员等级名称
     */
    private String memberName;
}
