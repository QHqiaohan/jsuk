/*
package com.jh.jsuk.task;

import com.jushang.entity.Order;
import com.jushang.entity.UserCoupon;
import com.jushang.service.CouponService;
import com.jushang.service.OrderService;
import com.jushang.utils.MyEntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

*/
/**
 * @author xieshihao   优惠券退款
 * @date 2018-05-02 15:42
 *//*

@Component
public class CouponRefundTask {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;

    // @Scheduled(cron = " 0 0/10 * * * ? " )
    //@Scheduled(cron = " 0/10 * * * * ?  " )
    public void couponRefund() {
        Date now = new Date();
        Date now_15 = new Date(now.getTime() - 900000); //15分钟前的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String nowTime_15 = dateFormat.format(now_15);
        List<Order> orders = orderService.selectList(new MyEntityWrapper<Order>().where("creat_time<{0} and(status=0) and coupon_id IS NOT NULL and coupon_id<>0 and user_coupon_id IS NOT NULL and user_coupon_id<>0", nowTime_15));
        for (Order o : orders) {
            Order to = new Order();
            to.setId(o.getId());
            to.setCouponId(0);
            to.setUserCouponId(0);
            to.updateById();
            UserCoupon uc = new UserCoupon();
            uc.setId(o.getUserCouponId());
            uc.setStatus(1);
            uc.updateById();
        }
    }
}
*/
