/*
package com.jh.jsuk.task;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jushang.entity.Coupon;
import com.jushang.entity.Message;
import com.jushang.entity.Order;
import com.jushang.entity.vo.OrderVo;
import com.jushang.service.CouponService;
import com.jushang.service.OrderService;
import com.jushang.utils.AliPayUtil;
import com.jushang.utils.JPushUtils;
import com.jushang.utils.MSGUtil;
import com.jushang.utils.MyEntityWrapper;
import com.jushang.utils.wx.WxPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

*/
/**
 * @author xieshihao
 * @date 2018-04-18 15:07
 *//*

@Component
public class PayRefundTask {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponService couponService;

    */
/**
     * 交易金额退款
     *//*

    //@Scheduled(cron = " 0 0/10 * * * ? " )
    //@Scheduled(cron = " 0/10 * * * * ?  " )
    public void aliRefund() {
        Date now = new Date();
        Date now_15 = new Date(now.getTime() - 900000); //15分钟前的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String nowTime_15 = dateFormat.format(now_15);
        List<Order> orders = orderService.selectList(new MyEntityWrapper<Order>().where("pay_time<{0} and is_unsubscribe=0 and(status=1 or status=7)", nowTime_15));
        for (Order o : orders) {
            System.out.println("订单信息：" + o);
            if (o.getPayType() == 0) {
                System.out.println("支付宝退款！");
                Order order = orderService.selectById(o.getId());
                OrderVo orderVo = orderService.findVoById(o.getId());
                try {
                    AlipayTradeRefundResponse response = AliPayUtil.getRefundResponse(orderVo);
                    if (response.isSuccess()) {
                        order.setIsUnsubscribe(1);
                        order.updateById();
                        orderService.returnStock(o.getId());
                        Message message = new Message();
                        message.setMsgType(4);
                        message.setUserId(order.getUserId());
                        message.setRelationId(order.getId() + "");
                        message.insert();
                        JPushUtils.pushMsg(order.getUserId() + "", "您的订单：" + order.getOrderNum() + "已退款成功", "", null);
                        System.out.println("退款成功");
                    } else {
                        System.out.println("退款失败，请重试");
                    }
                } catch (AlipayApiException e) {
                    System.out.println("网络异常");
                }
            } else if (o.getPayType() == 1) {
                System.out.println("微信退款！");
                Order order = orderService.selectById(o.getId());
                OrderVo orderVo = orderService.findVoById(o.getId());
                //String url="";
                Coupon coupon = orderVo.getCoupon();//优惠券实体
                BigDecimal orderPrice = order.getOrderPrice();//订单价格
                BigDecimal distributionFee = order.getDistributionFee();//配送费价格
                BigDecimal realOrderPrice;
                if (coupon != null) {
                    realOrderPrice = orderPrice.subtract(coupon.getDiscount()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    if (realOrderPrice.intValue() < 0) {
                        realOrderPrice = new BigDecimal(0);
                    }
                    realOrderPrice = realOrderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    realOrderPrice = orderPrice.add(distributionFee).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                String orderNum = order.getOrderNum();
                double op = realOrderPrice.doubleValue();
                MSGUtil msgUtil = null;
                try {
                    msgUtil = WxPay.wxPayRefund(op, orderNum);
                    if (msgUtil.getState() == 500) {
                        System.out.println("退款失败，请重试");
                    } else {
                        order.setIsUnsubscribe(1);
                        order.updateById();
                        orderService.returnStock(o.getId());
                        Message message = new Message();
                        message.setMsgType(4);
                        message.setUserId(order.getUserId());
                        message.setRelationId(order.getId() + "");
                        message.insert();
                        JPushUtils.pushMsg(order.getUserId() + "", "您的订单：" + order.getOrderNum() + "已退款成功", "", null);
                        System.out.println("退款成功");
                    }
                } catch (UnsupportedEncodingException e) {
                    System.out.println("网络异常");
                }
            }
        }
    }

}
*/
