package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Qa;
import com.jh.jsuk.service.QaService;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 问答Q&A表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-29
 */
@Api(tags = {"问答Q&A"})
@RestController
@RequestMapping("/qa")
public class QaController {

    @Autowired
    private QaService qaService;

    @ApiOperation("问答列表")
    @RequestMapping(value = "/qaList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result qaList() {
        List<Qa> qaList = qaService.selectList(new EntityWrapper<Qa>()
                .orderBy(Qa.SORT, false));
        return new Result().success(qaList);
    }

}

