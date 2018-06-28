package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.DistributionUser;
import com.jh.jsuk.entity.UserBank;

public class UserApplyVo extends DistributionApply {
    private DistributionUser user;
    private UserBank bank;

    public DistributionUser getUser() {
        return user;
    }

    public void setUser(DistributionUser user) {
        this.user = user;
    }

    public UserBank getBank() {
        return bank;
    }

    public void setBank(UserBank bank) {
        this.bank = bank;
    }
}
