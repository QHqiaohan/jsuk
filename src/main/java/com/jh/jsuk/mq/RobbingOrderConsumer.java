//package com.jh.jsuk.mq;
//
//
//import com.jh.jsuk.entity.Express;
//import com.jh.jsuk.entity.dto.RobbingExpressDTO;
//import com.jh.jsuk.service.ExpressService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import static com.jh.jsuk.conf.QueueConfig.QUEUE_ROBBING_ORDER;
//
//@Slf4j
//@Component
//@RabbitListener(queues = QUEUE_ROBBING_ORDER)
//public class RobbingOrderConsumer {
//
//    @Autowired
//    private ExpressService expressService;
//
//    @RabbitHandler
//    public void process(RobbingExpressDTO data) {
//        Integer expressId = data.getExpressId();
//        Integer userId = data.getUserId();
//        Express express = expressService.selectById(expressId);
//        synchronized (this) {
//            if (express != null && express.getDistributionUserId() == null && express.getStatus() == 2) {
//                express.setDistributionUserId(userId);
//                express.setStatus(3);
//                express.updateById();
//                log.info("抢单成功:{} ========= {}", expressId, userId);
//                DisJPushUtils.pushMsg(userId + "", "抢单成功！", "", null);
//                //发送成功推送
//            } else {
//                log.info("很抱歉慢了一步，订单已被其他骑手抢走:{} ========= {}", expressId, userId);
//                DisJPushUtils.pushMsg(userId + "", "很抱歉慢了一步，订单已被其他骑手抢走", "", null);
//                //发送失败推送
//            }
//        }
//    }
//
//}
