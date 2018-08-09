package com.jh.jsuk.controller;

import com.jh.jsuk.entity.RunningFee;
import com.jh.jsuk.service.RunningFeeService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:xyl
 * Date:2018/8/9 11:38
 * Description:跑腿费计算规则
 */
@Api(tags = "跑腿费计算规则")
@RestController
@RequestMapping("/runningFee")
public class RunningFeeController {
    @Autowired
    private RunningFeeService runningFeeService;

    @GetMapping
    public R getRunningFee() {
        return R.succ(runningFeeService.selectById(1));
    }

    @PostMapping
    public R updateFee(RunningFee runningFee) {
        runningFee.updateById();
        return R.succ();
    }

}
