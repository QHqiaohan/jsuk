package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.DistributionDetail;
import com.jh.jsuk.entity.DistributionUser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Qiao Han
 * 后台管理系统-骑手相关
 */
public class PlatformDistributionUserVo extends DistributionUser implements Serializable {

    //骑手订单集合
    private List<DistributionDetail> distributionDetailList;

    //骑手收入金额
    private BigDecimal distributionIncome;

    //骑手订单数量
    private Integer distributionTotalCount;

    public List<DistributionDetail> getDistributionDetailList() {
        return distributionDetailList;
    }

    public void setDistributionDetailList(List<DistributionDetail> distributionDetailList) {
        this.distributionDetailList = distributionDetailList;
    }

    public BigDecimal getDistributionIncome() {
        return distributionIncome;
    }

    public void setDistributionIncome(BigDecimal distributionIncome) {
        this.distributionIncome = distributionIncome;
    }

    public Integer getDistributionTotalCount() {
        return distributionTotalCount;
    }

    public void setDistributionTotalCount(Integer distributionTotalCount) {
        this.distributionTotalCount = distributionTotalCount;
    }
}
