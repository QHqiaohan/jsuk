package com.jh.jsuk.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:xyl
 * Date:2018/8/15 10:49
 * Description: 后台 - 意见反馈
 */
@Getter
@Setter
public class FeedBackVo implements Serializable {
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 反馈内容
     */
    private String content;
    /**
     * 创建时间
     */
    private Date publishTime;
}
