package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Activity;

import java.io.Serializable;


/**
 * 二手市场相关
 */
public class ActivitySecondVo extends Activity implements Serializable {

    private String[] imageArray;

    private String modularName;

    public String getModularName() {
        return modularName;
    }

    public void setModularName(String modularName) {
        this.modularName = modularName;
    }

    public String[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }
}
