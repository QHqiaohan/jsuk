package com.jh.jsuk.controller;


import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.ExpressEvaluate;
import com.jh.jsuk.service.ExpressEvaluateService;
import com.jh.jsuk.service.ExpressService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tj
 * @since 2018-08-19
 */
@Api(tags = "快递跑腿评价")
@RestController
@RequestMapping("/expressEvaluate")
public class ExpressEvaluateController {

    @Autowired
    ExpressEvaluateService expressEvaluateService;

    @Autowired
    ExpressService expressService;

    @ApiOperation("添加评价")
    @PostMapping("/add")
    public R add(@ModelAttribute ExpressEvaluate expressEvaluate) {
        expressEvaluate.insert();
        Express entity = new Express();
        entity.setId(expressEvaluate.getExpressId());
        entity.setStatus(6);
        expressService.updateById(entity);
        return R.succ();
    }
}

