package com.jh.jsuk.entity.vo;

import com.jh.jsuk.envm.ShopRushBuyStatus;
import com.jh.jsuk.utils.Date2;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class RushBuyVo {

    private Date startTime;

    private Date endTime;

    private Integer goodsSizeId;

    private ShopRushBuyStatus status;

    public void checkStatus() {
        Date2 date2 = new Date2();
        date2.setYear2(1970);
        date2.setMonth2(0);
        date2.setDay2(1);
        Date2 end = new Date2(endTime);
        Date2 start = new Date2(startTime);
        if (date2.isBefore(start)) {
            status = ShopRushBuyStatus.NOT_STARTED;
        } else if (date2.isAfter(start) && date2.isBefore(end)) {
            status = ShopRushBuyStatus.ON_GOING;
        } else if (date2.isAfter(end)) {
            status = ShopRushBuyStatus.OVER;
        }
    }

}
