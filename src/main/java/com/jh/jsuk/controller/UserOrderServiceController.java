package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserOrderService;
import com.jh.jsuk.envm.OrderRefundStatus;
import com.jh.jsuk.envm.RefundType;
import com.jh.jsuk.service.UserOrderServiceService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pageMoney")
    public R pageMoney(Page page, String status) throws Exception {
        OrderRefundStatus sts = null;
        if (StrUtil.isNotBlank(status) && !"all".equals(status)) {
            sts = EnumUitl.valueOf(OrderRefundStatus.class, status);
        }
        Wrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.eq(UserOrderService.TYPE, RefundType.RETURN_MONEY.getKey());
        if (sts != null) {
            wrapper.eq(UserOrderService.STATUS, sts.getKey());
        }
        return R.succ(userOrderServiceService.selectPage(page, wrapper));
    }

    @GetMapping("/pageMoneyCount")
    public R count() {
        Map<String, Object> map = new HashMap<>();
        int all = 0;
        OrderRefundStatus[] values = OrderRefundStatus.values();
        for (OrderRefundStatus value : values) {
            EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
            wrapper.eq(UserOrderService.STATUS,value.getKey());
            wrapper.in(UserOrderService.TYPE, new Integer[]{RefundType.RETURN_GOODS.getKey(), RefundType.CHANGE_GOODS.getKey()});
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
        OrderRefundStatus[] values = OrderRefundStatus.values();
        for (OrderRefundStatus value : values) {
            EntityWrapper<UserOrderService> wrapper = new EntityWrapper<>();
            wrapper.eq(UserOrderService.STATUS,value.getKey());
//            wrapper.in(UserOrderService.TYPE, new Integer[]{RefundType.RETURN_GOODS.getKey(), RefundType.CHANGE_GOODS.getKey()});
            wrapper.eq(UserOrderService.TYPE, RefundType.RETURN_MONEY.getKey());
            int cnt = userOrderServiceService.selectCount(wrapper);
            all += cnt;
            map.put(value.toString().toLowerCase(), cnt);
        }
        map.put("all", all);
        return R.succ(map);
    }

    @GetMapping("/page")
    public R page(Page page, String status) throws Exception {
        OrderRefundStatus sts = null;
        if (StrUtil.isNotBlank(status) && !"all".equals(status)) {
            sts = EnumUitl.valueOf(OrderRefundStatus.class, status);
        }
        Wrapper<UserOrderService> wrapper = new EntityWrapper<>();
        wrapper.in(UserOrderService.TYPE, new Integer[]{RefundType.RETURN_GOODS.getKey(), RefundType.CHANGE_GOODS.getKey()});
        if (sts != null) {
            wrapper.eq(UserOrderService.STATUS, sts.getKey());
        }
        return R.succ(userOrderServiceService.selectPage(page, wrapper));
    }

}

