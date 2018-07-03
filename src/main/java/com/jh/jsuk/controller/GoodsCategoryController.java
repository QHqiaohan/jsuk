package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jh.jsuk.conf.Menu;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品类型相关API:")
@RestController
@RequestMapping(value = "/goodsCategory", method = {RequestMethod.POST, RequestMethod.GET})
public class GoodsCategoryController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @GetMapping("/list")
    public R list() {
        Wrapper<GoodsCategory> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsCategory.STATUS, 1);
        return R.succ(goodsCategoryService.selectList(wrapper));
    }

    @ApiOperation("用户端-获取商品所有类型")
    @RequestMapping(value = "/getAllCategory")
    public Result getAllCategory() {
        /**
         * 商品类型
         */
        List<GoodsCategory> goodsCategories = goodsCategoryService.selectList(new MyEntityWrapper<GoodsCategory>()
                .orderBy(GoodsCategory.SORT_ORDER, false));
        List<Menu> rootMenu = Lists.newArrayList();
        goodsCategories.forEach(category -> {
            Menu menu = new Menu();
            menu.setId(String.valueOf(category.getId()));
            menu.setIcon(category.getPic());
            menu.setName(category.getName());
            menu.setOrder(category.getSortOrder());
            menu.setParentId(String.valueOf(category.getParentId()));
            rootMenu.add(menu);
        });
        // 最后的结果
        List<Menu> menuList = Lists.newArrayList();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单 parentId=0
            if (StrUtil.equals(rootMenu.get(i).getParentId(), "0")) {
                menuList.add(rootMenu.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Menu menu : menuList) {
            menu.setChildMenus(getChild(menu.getId(), rootMenu));
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("goodsCategory", menuList);
        /**
         * 获取banner
         */
        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
                // 7=分类页banner
                .eq(Banner.BANNER_LOCATION, 7)
                .eq(Banner.IS_VALID, 1)
                .orderBy(Banner.CREATE_TIME, false));
        if (CollectionUtils.isEmpty(bannerList)) {
            map.put("banner", null);
        } else {
            map.put("banner", bannerList);
        }
        return new Result().success(map);
    }

}

