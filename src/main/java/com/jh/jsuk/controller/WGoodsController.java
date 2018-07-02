package com.jh.jsuk.controller;


import com.jh.jsuk.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/wgoods")
public class WGoodsController {

    @GetMapping("/list")
    public R list() throws Exception{
        return null;
    }


}
