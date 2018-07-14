package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Activity;
import com.jh.jsuk.entity.ActivityTransactionArea;
import com.jh.jsuk.entity.User;

/**
 * @author xuchuruo
 * @since 2018年5月26日17:35:24
 */
public class ActivityVo extends Activity {

    private String carName;
    private User user;
    private ActivityTransactionArea activityTransactionArea;

    public ActivityTransactionArea getActivityTransactionArea() {
        return activityTransactionArea;
    }

    public void setActivityTransactionArea(ActivityTransactionArea activityTransactionArea) {
        this.activityTransactionArea = activityTransactionArea;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarName() {

        return carName;
    }

}
