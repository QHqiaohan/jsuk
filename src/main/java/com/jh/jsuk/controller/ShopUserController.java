package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.service.ShopUserService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Author: xyl
 * Date:2018/7/31 15:27
 * Description:商户信息
 */
@RestController
@RequestMapping("/shopUser")
public class ShopUserController {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private Session session;

    /**
     * @param page
     * @param userName
     * @param name
     * @param sectionTime 创建时间区间
     * @return
     */
    @GetMapping("/list")
    public R list(Page page, String userName, String name, String[] sectionTime) throws ParseException {
        return R.succ(shopUserService.list(page, userName, name, sectionTime));
    }

    /**
     * 删除商户信息
     *
     * @param id
     * @return
     */
    @PostMapping("/del")
    public R del(Integer id) {
        ShopUser shopUser = new ShopUser();
        shopUser.setId(id);
        shopUser.setIsDel(1);
        shopUser.updateById();
        return R.succ();
    }

    @PostMapping("/review")
    public R review(Integer id, Integer flag) {
        //只有平台才能修改
//        if (session.getUserType().getKey() == 4) {
            ShopUser shopUser = shopUserService.selectById(id);
            //只能修改待审核状态的商户
            if (shopUser.getIsCheck() == 0) {
                shopUser.setIsCheck(flag == 1 ? 1 : -1);
                shopUser.updateById();
                return R.succ("操作成功");
            }
            return R.err("该商户已经审核过了");
        }
//        return R.err("权限不足");
//    }
}

