//package com.jh.jsuk.controller;
//
//
//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.jh.jsuk.entity.Banner;
//import com.jh.jsuk.entity.ShopGoods;
//import com.jh.jsuk.service.BannerService;
//import com.jh.jsuk.service.GoodsCategoryService;
//import com.jh.jsuk.service.ShopGoodsService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.util.*;
//
///**
// * <p>
// * 商品分类 前端控制器
// * </p>
// *
// * @author lpf
// * @since 2018-06-20
// */
//@Api(tags = "商品分类相关API:")
//@RestController
//@RequestMapping("/goodsCategory")
//public class GoodsCategoryController {
//
//    @Autowired
//    private BannerService bannerService;
//    @Autowired
//    private GoodsCategoryService goodsCategoryService;
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private ShopGoodsService shopGoodsService;
//
//    @ApiOperation("根据模块分类查询商品/店铺列表")
//    @PostMapping("/getByModular")
//    public Result getByModular(@ApiParam(value = "模块ID", required = true) Integer modularId) {
//        // 封装数据map
//        Map<String, Object> map = new HashMap<>();
//        /**
//         * 商品推荐
//         */
//        Page<ShopGoods> shopGoodsPage = shopGoodsService.selectPage(
//                new Page<>(1, 4),
//                new EntityWrapper<ShopGoods>()
//                        .eq(ShopGoods.STATUS, 1)
//                        .eq(ShopGoods.IS_RECOMMEND, 1)
//                        .eq(ShopGoods.SHOP_MODULAR_ID, modularId)
//                        .orderBy(ShopGoods.SALE_AMONT, false));
//        map.put("shopGoods", shopGoodsPage.getRecords());
//        /**
//         * 店铺列表
//         */
//        Page<Shop> shopPage = shopService.selectPage(
//                new Page<>(1, 4),
//                new EntityWrapper<Shop>()
//                        .eq(Shop.CAN_USE, 1)
//                        .eq(Shop.IS_RECOMMEND, 1)
//                        .eq(Shop.MODULAR_ID, modularId)
//                        .orderBy(Shop.TOTAL_VOLUME, false));
//        map.put("shop", shopPage.getRecords());
//        return new Result().success();
//    }
//
//    public List<Map<String, Object>> getData() {
//        return null;
//    }
//
//    @GetMapping("/get_category")
//    public Result getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        //查询子节点的category信息,并且不递归,保持平级
//        return categoryService.getChildrenParallelCategory(categoryId);
//    }
//
//    @PostMapping("add_category")
//    public Result addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
//        //增加我们处理分类的逻辑
//        return categoryService.addCategory(categoryName, parentId);
//    }
//
//    @PostMapping("set_category_name")
//    public Result setCategoryName(HttpSession session, Integer categoryId, String categoryName, String status) {
//        Integer stas = null;
//        if (StrUtil.equals(status, "0")) {
//            stas = 2;
//        } else if (StrUtil.equals(status, "1")) {
//            stas = 1;
//        }
//        //更新categoryName
//        return categoryService.updateCategoryName(categoryId, categoryName, stas);
//
//    }
//
//
//    @GetMapping("get_deep_category")
//    public Result getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        //查询当前节点的id和递归子节点的id
//        //0->10000->100000
//        return categoryService.selectCategoryAndChildrenById(categoryId);
//    }
//
//    @GetMapping("get_category_list")
//    public Result getCategoryList(Integer size, Integer current) {
//        Page selectPage = categoryService.selectPage(new Page<>(current, size), new EntityWrapper<Category>().orderBy(Category.PARENT_ID, true));
//        return new Result().success(selectPage);
//    }
//
//    /**
//     * 后台新增分类
//     *
//     * @param categoryName
//     * @param parentId
//     * @param status
//     * @param pic
//     * @return
//     */
//    @PostMapping("/ui/add_category")
//    public Result addCategoryBySys(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId, @RequestParam(value = "status",
//            defaultValue = "1") String status, String pic) {
//        Category category = new Category();
//        category.setStatus(Integer.valueOf(status));
//        category.setParentId(parentId);
//        category.setName(categoryName);
//        category.setCreateTime(new Date());
//        category.setPic(pic);
//        return categoryService.insert(category) ? new Result().success("成功") : new Result().erro("错误");
//    }
//
//    /**
//     * 后台新增分类
//     *
//     * @param categoryName
//     * @param parentId
//     * @param status
//     * @param pic
//     * @return
//     */
//    @PostMapping("/ui/edit_category")
//    public Result editCategoryBySys(int id, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId, @RequestParam(value = "status", defaultValue = "1") String status, String pic) {
//        Category category = new Category();
//        category.setId(id);
//        category.setStatus(Integer.valueOf(status));
//        category.setParentId(parentId);
//        category.setName(categoryName);
//        category.setUpdateTime(new Date());
//        category.setPic(pic);
//        return categoryService.updateById(category) ? new Result().success("成功") : new Result().erro("错误");
//    }
//
//    @ApiOperation("获取所有分类")
//    @RequestMapping(value = "/getClassifyList", method = {RequestMethod.POST, RequestMethod.GET})
//    public Result getClassifyList() {
//        // 原始的数据
//        List<Category> categorys = categoryService.selectList(new MyEntityWrapper<Category>());
//        List<Menu> rootMenu = Lists.newArrayList();
//        categorys.forEach(category -> {
//            Menu menu = new Menu();
//            menu.setId(String.valueOf(category.getId()));
//            menu.setIcon(category.getPic());
//            menu.setName(category.getName());
//            menu.setOrder(category.getSortOrder());
//            menu.setParentId(String.valueOf(category.getParentId()));
//            rootMenu.add(menu);
//        });
//
//        // 最后的结果
//        List<Menu> menuList = Lists.newArrayList();
//        // 先找到所有的一级菜单
//        for (int i = 0; i < rootMenu.size(); i++) {
//            // 一级菜单 parentId=0
//            if (StrUtil.equals(rootMenu.get(i).getParentId(), "0")) {
//                menuList.add(rootMenu.get(i));
//            }
//        }
//        // 为一级菜单设置子菜单，getChild是递归调用的
//        for (Menu menu : menuList) {
//            menu.setChildMenus(getChild(menu.getId(), rootMenu));
//        }
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("classifys", menuList);
//        /**
//         * 获取banner
//         */
//        List<Banner> bannerList = bannerService.selectList(new EntityWrapper<Banner>()
//                .eq(Banner.BANNER_LOCATION, 7)// 7=分类页banner
//                .eq(Banner.BANNER_TYPE, 0)
//                .eq(Banner.IS_VALID, 1)
//                .orderBy(Banner.RANK, false));
//        map.put("banners", bannerList);
//        return new Result().success("", map);
//
//    }
//
//    /**
//     * 递归查找子菜单
//     *
//     * @param id       当前菜单id
//     * @param rootMenu 要查找的列表
//     * @return
//     */
//    private List<Menu> getChild(String id, List<Menu> rootMenu) {
//        // 子菜单
//        List<Menu> childList = new ArrayList<>();
//        for (Menu menu : rootMenu) {
//            // 遍历所有节点，将父菜单id与传过来的id比较
//            if (StrUtil.isNotBlank(menu.getParentId())) {
//                if (menu.getParentId().equals(id)) {
//                    childList.add(menu);
//                }
//            }
//        }
//        // 把子菜单的子菜单再循环一遍
//        for (Menu menu : childList) {// 没有url子菜单还有子菜单
//            // 递归
//            menu.setChildMenus(getChild(menu.getId(), rootMenu));
//        } // 递归退出条件
//        if (childList.size() == 0) {
//            return null;
//        }
//        return childList;
//    }
//}
//
