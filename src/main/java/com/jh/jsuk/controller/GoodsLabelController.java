package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.GoodsLabel;
import com.jh.jsuk.service.GoodsLabelService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品标签 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@RestController
@RequestMapping("/goodsLabel")
public class GoodsLabelController {

    @Autowired
    GoodsLabelService goodsLabelService;

    @GetMapping("/list")
    public R list() {
        Wrapper<GoodsLabel> wrapper = new EntityWrapper<>();
        wrapper.ne(GoodsLabel.IS_DEL, 1).orderBy(GoodsLabel.RANK, false);
        return R.succ(goodsLabelService.selectList(wrapper));
    }

}

