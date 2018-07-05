package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.envm.ExpressStatus;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 快递跑腿 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = {"快递跑腿相关API:"})
@RestController
@RequestMapping("/express")
public class ExpressController {

    @Autowired
    ExpressService expressService;
    @Autowired
    Session session;

    @GetMapping("/page")
    public R page(Page page, String status, String kw, String[] date) throws Exception {
        List<String> dates = null;
        if (date != null && StrUtil.isNotBlank(date[0]) && StrUtil.isNotBlank(date[1])) {
            dates = Arrays.asList(date);
        }
        ExpressStatus expressStatus = null;
        if (status != null && !"all".equals(status)) {
            expressStatus = EnumUitl.toEnum(ExpressStatus.class, status,"getShortKey");
        }
        return R.succ(expressService.listPage(page, expressStatus, dates, kw));
    }

    @GetMapping("/count")
    public R count() {
        Map<String,Object> map = new HashMap<>();
        int all = 0;
        for (ExpressStatus status : ExpressStatus.values()) {
            int cnt = expressService.statusCount(status, session.getShopId(), session.getUserType(), session.getUserId());
            all += cnt;
            map.put(status.getShortKey(),cnt);
        }
        map.put("all",all);
        return R.succ(map);
    }

    @GetMapping("/detail")
    public R detail(Integer expressId){
        return R.succ(expressService.detail(expressId));
    }

}

