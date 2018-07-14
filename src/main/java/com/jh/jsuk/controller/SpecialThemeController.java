package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.SpecialTheme;
import com.jh.jsuk.service.SpecialThemeService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页-专题精选 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-26
 */
@RestController
@RequestMapping("/specialTheme")
public class SpecialThemeController {

    @Autowired
    SpecialThemeService specialThemeService;

    @PostMapping("/getList")
    public R getList(){
        EntityWrapper<SpecialTheme> wrapper = new EntityWrapper<>();
        wrapper.ne(SpecialTheme.IS_DEL,1)
                .orderBy(SpecialTheme.RANK,false)
                .last("limit 10");
        return R.succ(specialThemeService.getVoByList(wrapper));
    }

}

