package com.jh.jsuk.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.Shop;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.service.ShopService;
import com.jh.jsuk.service.ShopUserService;
import com.jh.jsuk.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * Author: xyl
 * Date:2018/7/31 15:27
 * Description:商户信息
 */
@Controller
@RequestMapping("/shopUser")
public class ShopUserController {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private Session session;

    /**
     * @param page
     * @param userName
     * @param name
     * @param sectionTime 创建时间区间
     * @return
     */
    @ResponseBody
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
    @ResponseBody
    @PostMapping("/del")
    public R del(Integer id) {
        ShopUser shopUser = new ShopUser();
        shopUser.setId(id);
        shopUser.setIsDel(1);
        shopUser.updateById();
        return R.succ();
    }

    @ResponseBody
    @PostMapping("/review")
    public R review(Integer id, Integer flag) {
        //只有平台才能修改
        if (session.getUserType().getKey() == 4) {
            ShopUser shopUser = shopUserService.selectById(id);
            //只能修改待审核状态的商户
            if (shopUser.getIsCheck() == 0) {
                shopUser.setIsCheck(flag == 1 ? 1 : -1);
                shopUser.updateById();
                Shop shop = shopService.selectById(shopUser.getShopId());
                shop.setCanUse(1);
                shop.updateById();
                return R.succ("操作成功");
            }
            return R.err("该商户已经审核过了");
        }
        return R.err("权限不足");
    }

    @Resource
    @Qualifier("shopUserExcelView")
    private View shopUserExcelView;

    /**
     * 导出商户列表
     */
    @PostMapping("/export")
    public ModelAndView exportUsers(Integer[] ids) {
        ModelAndView view = new ModelAndView();
        view.setView(shopUserExcelView);
        view.addObject("shopUserVos", shopUserService.excelData(ids));
        return view;
    }

}

