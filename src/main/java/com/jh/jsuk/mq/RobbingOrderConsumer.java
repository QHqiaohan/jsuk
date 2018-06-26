package com.jh.jsuk.mq;


import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.dto.UserOrderDTO;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.DisJPushUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jh.jsuk.conf.QueueConfig.QUEUE_ROBBING_ORDER;

@Slf4j
@Component
@RabbitListener(queues = QUEUE_ROBBING_ORDER)
public class RobbingOrderConsumer {

    @Autowired
    private UserOrderService orderService;

    @RabbitHandler
    public void process(UserOrderDTO data) {
        Integer orderId = data.getOrderId();
        UserOrder order = orderService.selectById(orderId);
        Integer userId = data.getUserId();
        synchronized (this) {
            if (order != null && order.getDistributionUserId() == null && order.getStatus() == 2) {
                order.setDistributionUserId(userId);
                order.setStatus(3);
                order.updateById();
                log.info("抢单成功:{} ========= {}", orderId, userId);
                DisJPushUtils.pushMsg(userId + "", "抢单成功！", "", null);
                //发送成功推送
            } else {
                log.info("很抱歉慢了一步，订单已被其他骑手抢走:{} ========= {}", orderId, userId);
                DisJPushUtils.pushMsg(userId + "", "很抱歉慢了一步，订单已被其他骑手抢走", "", null);
                //发送失败推送
            }
        }
    }

}
