package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.User;
import lombok.Data;

import java.io.Serializable;


/**
 * 二手市场相关
 */
@Data
public class ActivitySecondVo extends Activity implements Serializable {

    private String[] imageArray;

    private User user;

    private String modularName;

}
