package com.jh.jsuk.entity.rules;


import com.jh.jsuk.entity.DistributionDetail;
import com.jh.jsuk.entity.DistributionUser;

import java.io.Serializable;

public class AccountRule implements Serializable {
    private DistributionUser user;
    private DistributionDetail detail;

    public AccountRule() {
    }

    public AccountRule(DistributionUser user, DistributionDetail detail) {
        this.user = user;
        this.detail = detail;
    }

    public DistributionUser getUser() {
        return user;
    }

    public void setUser(DistributionUser user) {
        this.user = user;
    }

    public DistributionDetail getDetail() {
        return detail;
    }

    public void setDetail(DistributionDetail detail) {
        this.detail = detail;
    }

    public void updateUserAndInsertDetail() {
        //取消，在規則引擎中修改
        // user.setAccount(user.getAccount().add(detail.getMoney()));
        user.updateById();
        detail.insert();
    }
}
