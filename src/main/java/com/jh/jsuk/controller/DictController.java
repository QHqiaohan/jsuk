package com.jh.jsuk.controller;


import com.jh.jsuk.service.DictService;
import com.jh.jsuk.utils.R;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    DictService dictService;

    @GetMapping
    @SneakyThrows
    public R getDict(String type,String code){
        return R.succ(dictService.getDict(type,code));
    }

}

