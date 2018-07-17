package com.jh.jsuk.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jh.jsuk.conf.Menu;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.service.BannerService;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.R;
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

        /**
         * 遍历商品分类集合goodsCategories
         * 分类信息添加到rootMenu集合
         */
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


    /**
     * 查询二级分类下面的子级分类
     * @param session   ?
     * @param categoryId    前端传当前(二级)分类id
     * @return
     */
    @ApiIgnore
    @RequestMapping("/get_category")
    public Result getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //查询子节点的category信息,并且不递归,保持平级
        return goodsCategoryService.getChildrenParallelCategory(categoryId);
    }

    //客户(用户)端不应该有添加商品分类的功能
    @ApiIgnore
    @PostMapping("add_category")
    public Result addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        //增加我们处理分类的逻辑
        return goodsCategoryService.addCategory(categoryName, parentId);
    }

    //更新商品分类 ?
    @ApiIgnore
    @PostMapping("set_category_name")
    public Result setCategoryName(HttpSession session, Integer categoryId, String categoryName, String status) {
        Integer stas = null;
        if (StrUtil.equals(status, "0")) {
            stas = 2;
        } else if (StrUtil.equals(status, "1")) {
            stas = 1;
        }
        //更新categoryName
        return goodsCategoryService.updateCategoryName(categoryId, categoryName, stas);
    }

    @ApiIgnore
    @RequestMapping("get_deep_category")
    public Result getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer
            categoryId) {
        //查询当前节点的id和递归子节点的id
        //0->10000->100000
        return goodsCategoryService.selectCategoryAndChildrenById(categoryId);
    }

    @ApiIgnore
    @RequestMapping("get_category_list")
    public Result getCategoryList(Integer size, Integer current) {
        Page selectPage = goodsCategoryService.selectPage(
                new Page<>(current, size),
                new EntityWrapper<GoodsCategory>()
                        .orderBy(GoodsCategory.PARENT_ID, true));
        return new Result().success(selectPage);
    }

    /**
     * 后台新增分类
     *
     * @param categoryName
     * @param parentId
     * @param status
     * @param pic
     * @return
     */
    @ApiIgnore
    @PostMapping("/ui/add_category")
    public Result addCategoryBySys(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId, @RequestParam(value =
            "status",
            defaultValue = "1") String status, String pic) {
        GoodsCategory category = new GoodsCategory();
        category.setStatus(Integer.valueOf(status));
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setCreateTime(new Date());
        category.setPic(pic);
        return goodsCategoryService.insert(category) ? new Result().success("成功") : new Result().erro("错误");
    }

    /**
     * 后台新增分类
     *
     * @param categoryName
     * @param parentId
     * @param status
     * @param pic
     * @return
     */
    @ApiIgnore
    @PostMapping("/ui/edit_category")
    public Result editCategoryBySys(int id, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId, @RequestParam
            (value = "status", defaultValue = "1") String status, String pic) {
        GoodsCategory category = new GoodsCategory();
        category.setId(id);
        category.setStatus(Integer.valueOf(status));
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setUpdateTime(new Date());
        category.setPic(pic);
        return goodsCategoryService.updateById(category) ? new Result().success("成功") : new Result().erro("错误");
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<Menu> getChild(String id, List<Menu> rootMenu) {
        // 子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StrUtil.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Menu menu : childList) {// 没有url子菜单还有子菜单
            // 递归
            menu.setChildMenus(getChild(menu.getId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}

