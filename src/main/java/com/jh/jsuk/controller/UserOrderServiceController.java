package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.envm.OrderRefundStatus;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/pageMoney")
    public R pageMoney(Page page,String status) throws Exception {
        OrderRefundStatus sts = null;
        if(StrUtil.isNotBlank(status) && !"all".equals(status)){
            EnumUitl.valueOf(OrderRefundStatus.class,status);
        }
        return R.succ();
    }

}

