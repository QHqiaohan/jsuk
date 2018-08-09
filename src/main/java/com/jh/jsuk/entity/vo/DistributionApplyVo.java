package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.DistributionApply;
import com.jh.jsuk.entity.DistributionUser;

import java.io.Serializable;

/**
 * 骑手端提现
 */
public class DistributionApplyVo extends DistributionApply implements Serializable {

    private DistributionUser distributionUser;

    public DistributionUser getDistributionUser() {
        return distributionUser;
    }

    public void setDistributionUser(DistributionUser distributionUser) {
        this.distributionUser = distributionUser;
    }
}
