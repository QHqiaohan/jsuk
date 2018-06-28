package com.jh.jsuk.entity.vo;

/**
 * @author xieshihao
 * @date 2018-04-12 16:46
 */
public class DistributionUserVo {
    private Integer lastMonthNum;//上月订单数量
    private Integer nowMonthNum;//本月订单数量

    public Integer getLastMonthNum() {
        return lastMonthNum;
    }

    public void setLastMonthNum(Integer lastMonthNum) {
        this.lastMonthNum = lastMonthNum;
    }

    public Integer getNowMonthNum() {
        return nowMonthNum;
    }

    public void setNowMonthNum(Integer nowMonthNum) {
        this.nowMonthNum = nowMonthNum;
    }
}
