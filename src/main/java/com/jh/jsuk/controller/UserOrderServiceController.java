package com.jh.jsuk.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.envm.OrderServiceStatus;
import com.jh.jsuk.envm.OrderServiceType;
import com.jh.jsuk.service.ShopMoneyService;
import com.jh.jsuk.service.UserOrderServiceService;
import com.jh.jsuk.service.UserRemainderService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单申请售后 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
@RestController
@RequestMapping("/userOrderService")
public class UserOrderServiceController {

    @Autowired
    UserOrderServiceService userOrderServiceService;

    @Autowired
    com.jh.jsuk.service.UserOrderService userOrderService;

    @GetMapping("/get")
    public R serviceInfo(Integer id) {
        return R.succ(userOrderServiceService.get(id));
    }

    @PatchMapping
    public R edit(UserOrderService userOrderService) {
        userOrderServiceService.updateById(userOrderService);
        UserOrderService service = userOrderServiceService.selectById(userOrderService.getId());
        if (service != null && service.getOrderId() != null) {
            UserOrder order = new UserOrder();
            order.setId(service.getOrderId());
            order.setUpdateTime(new Date());
            order.updateById();
        }
        return R.succ();
    }

    @Autowired
    ShopMoneyService shopMoneyService;

    @Autowired
    UserRemainderService userRemainderService;


    @PostMapping("/confirm")
    @Transactional
    public R confirm(Integer id, String shopComment) throws Exception {
        R r = R.create();
        UserOrderService service = userOrderServiceService.selectById(id);
        if (service == null)
            return r.er("id {} 不存在", id);
        OrderServiceType type = EnumUitl.toEnum(OrderServiceType.class, service.getType());
        OrderServiceStatus status = EnumUitl.toEnum(OrderServiceStatus.class, service.getStatus());
        if (OrderServiceStatus.COMPLETE.equals(status) || OrderServiceStatus.REFUSED.equals(status)) {
            return r.er("订单已{}", status.getValue());
        }
        UserOrder order = userOrderService.selectById(service.getOrderId());
        if (order == null) {
            return r.er("订单不存在");
        }
        switch (type) {
            /**
             * 前两个都要退款
             */
            case RETURN_GOODS:
            case RETURN_MONEY:
//                shopMoneyService.refund()
                BigDecimal amount = new BigDecimal(service.getPrice());
                shopMoneyService.refund(order.getShopId(), amount);
                userRemainderService.gain(order.getUserId(), amount);
                break;
            case CHANGE_GOODS:
                break;
        }
        order.setUpdateTime(new Date());
        order.updateById();
        service.setStatus(OrderServiceStatus.COMPLETE.getKey());
        service.setShopComment(shopComment);
        service.updateById();
        return r.suc();
    }

    @PostMapping("/decline")
    public R decline(Integer id) {
        return R.succ();
    }

    @GetMapping("/pageMoney")
    public R pageMoney(Page page, String status, String kw, String[] date) throws Exception {
        OrderServiceStatus sts = null;
        if (StrUtil.isNotBlank(status) && !"all".equals(status)) {
            sts = EnumUitl.valueOf(OrderServiceStatus.class, status);
        }
        Wrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.eq(UserOrderService.TYPE, OrderServiceType.RETURN_MONEY.getKey());
        if (sts != null) {
            wrapper.eq(UserOrderService.STATUS, sts.getKey());
        }
        if (kw != null) {
            wrapper.like(UserOrderService.SERVICE_CODE, "%" + kw.trim() + "%");
        }
        String start = null, stop = null;
        if (date != null && date.length > 0) {
            start = date[0];
            stop = date[1];
        }
        if (StrUtil.isNotBlank(start) && StrUtil.isNotBlank(stop)) {
            wrapper.gt(UserOrderService.CREATE_TIME, DateTime.of(start, "yyyy-MM-dd"));
            wrapper.lt(UserOrderService.CREATE_TIME, DateTime.of(stop, "yyyy-MM-dd"));
        }
        wrapper.orderBy(UserOrderService.CREATE_TIME, false);
        return R.succ(userOrderServiceService.selectPage(page, wrapper));
    }

    @GetMapping("/pageMoneyCount")
    public R count() {
        Map<String, Object> map = new HashMap<>();
        int all = 0;
        OrderServiceStatus[] values = OrderServiceStatus.values();
        for (OrderServiceStatus value : values) {
            EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
            wrapper.eq(UserOrderService.STATUS, value.getKey());
            wrapper.in(UserOrderService.TYPE, new Integer[]{OrderServiceType.RETURN_GOODS.getKey(), OrderServiceType.CHANGE_GOODS.getKey()});
            int cnt = userOrderServiceService.selectCount(wrapper);
            all += cnt;
            map.put(value.toString().toLowerCase(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    @GetMapping("/pageCount")
    public R pageCount() {
        Map<String, Object> map = new HashMap<>();
        int all = 0;
        OrderServiceStatus[] values = OrderServiceStatus.values();
        for (OrderServiceStatus value : values) {
            EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
            wrapper.eq(UserOrderService.STATUS, value.getKey());
//            wrapper.in(UserOrderService.TYPE, new Integer[]{OrderServiceType.RETURN_GOODS.getKey(), OrderServiceType.CHANGE_GOODS.getKey()});
            wrapper.eq(UserOrderService.TYPE, OrderServiceType.RETURN_MONEY.getKey());
            int cnt = userOrderServiceService.selectCount(wrapper);
            all += cnt;
            map.put(value.toString().toLowerCase(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    /**
     * 退换货
     */
    @GetMapping("/page")
    public R page(Page page, String status, String kw, String[] date) throws Exception {
        OrderServiceStatus sts = null;
        if (StrUtil.isNotBlank(status) && !"all".equals(status)) {
            sts = EnumUitl.valueOf(OrderServiceStatus.class, status);
        }
        Wrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.in(UserOrderService.TYPE, new Integer[]{OrderServiceType.RETURN_GOODS.getKey(), OrderServiceType.CHANGE_GOODS.getKey()});
        if (sts != null) {
            wrapper.eq(UserOrderService.STATUS, sts.getKey());
        }
        if (kw != null) {
            wrapper.like(UserOrderService.SERVICE_CODE, "%" + kw.trim() + "%");
        }
        String start = null, stop = null;
        if (date != null && date.length > 0) {
            start = date[0];
            stop = date[1];
        }
        if (StrUtil.isNotBlank(start) && StrUtil.isNotBlank(stop)) {
            wrapper.gt(UserOrderService.CREATE_TIME, DateTime.of(start, "yyyy-MM-dd"));
            wrapper.lt(UserOrderService.CREATE_TIME, DateTime.of(stop, "yyyy-MM-dd"));
        }
        wrapper.orderBy(UserOrderService.CREATE_TIME, false);
        return R.succ(userOrderServiceService.selectPage(page, wrapper));
    }

}

