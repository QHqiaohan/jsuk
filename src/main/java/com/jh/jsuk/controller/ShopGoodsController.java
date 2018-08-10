package com.jh.jsuk.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.vo.AddGoodsVo;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.entity.vo.GoodsSizeVo;
import com.jh.jsuk.envm.ShopGoodsStatus;
import com.jh.jsuk.service.*;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.jh.jsuk.entity.vo.ShopRushBuySizeVo;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "商品相关API:")
@RestController
@RequestMapping("/shopGoods")
public class ShopGoodsController {

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private GoodsLabelService goodsLabelService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private StatisticsPriceService statisticsPriceService;
    @Autowired
    private ManagerUserService managerUserService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ShopGoodsSizeService shopGoodsSizeService;

//    @Autowired
//    ShopRushBuySizeService shopRushBuySizeService;

    @Autowired
    ShopRushBuyService shopRushBuyService;

    @GetMapping("/list")
    public R list(Integer shopId) {
        Wrapper<ShopGoods> wrapper = new EntityWrapper<>();
        if (shopId != null) {
            wrapper.eq(ShopGoods.SHOP_ID, shopId);
        }
        wrapper.ne(ShopGoods.IS_DEL, 1)
            .eq(ShopGoods.STATUS, ShopGoodsStatus.UPPER.getKey());
        return R.succ(shopGoodsService.selectList(wrapper));
    }

    @ApiOperation("用户端-商品id查询信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "goodsId", value = "商品id 逗号隔开", paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "/getById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getById(String goodsId) {
        Wrapper<ShopGoods> ew = new MyEntityWrapper<>();
        if (StrUtil.isBlank(goodsId))
            return R.succ();
        String[] split = goodsId.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            if (StrUtil.isNotBlank(s)) {
                list.add(s);
            }
        }
        ew.in(ShopGoods.ID, list);
        return new Result().success(shopGoodsService.selectList(ew));
    }

    @ApiOperation("用户端-根据店铺内部的分类-属性查询商品")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "attributeId", value = "属性ID,不传=全部商品", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "shopId", value = "店铺ID", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopGoodsByAttributeId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsByAttributeId(Integer attributeId, Page page, Integer shopId) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.shopGoodsListByAttributeId(page, ew, attributeId, shopId);
        return new Result().success(goodsPage);
    }


    //首页-分类-点击第三级分类-查看商品列表,按照价格、筛选(暂不确定) 排序
    //shopId在后台没有用,前台随便传一个数字即可
    @ApiOperation(value = "用户端-店铺内部的全部商品-根据综合/价格/销量/新品查询商品")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "type", value = "1=价格降序/-1升序,2=销量,3=新品,不传=默认综合", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "shopId", required = true, value = "店铺ID", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopGoodsBy", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsBy(Page page, Integer type, Integer shopId) {
        // 按价格查询
        if (type != null && (type.equals(1) || (type.equals(-1)))) {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsOrderBySalesPrice(page, ew, type, shopId);
            return new Result().success(goodsPage);
        } else {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsBy(page, ew, type, shopId);
            return new Result().success(goodsPage);
        }
    }


    //首页-分类-点击第三级分类-查看商品列表
    //按照分类查询商品列表的时候已经按照销量降序排序，因此综合和销量都调这个接口
    @ApiOperation(value = "用户端-根据商品类型获取商品列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopGoodsByCategoryId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsByCategoryId(Page page, Integer categoryId) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByCategoryId(page, ew, categoryId);
        return new Result().success(goodsPage);
    }

   /* @ApiOperation(value = "用户端-商品类型里的-根据综合/价格/销量查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer"),
    })
    @RequestMapping(value = "/getShopGoodsOnCategoryBy", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsOnCategoryBy(Page page, Integer categoryId, @ApiParam("1=价格降序/-1升序,2=销量") Integer type) {
        MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsOnCategoryBy(page, ew, type, categoryId);
        return new Result().success(goodsPage);
    }

    @ApiOperation(value = "用户端-根据筛选条件查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "goodsType", value = "1=包邮,2=促销,3=新品", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "lowPrice", value = "最低价格", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "highPrice", value = "最高价格", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "类型ID", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopGoodsByServiceOrPrice", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsByServiceOrPrice(Page page, Integer goodsType, String lowPrice, String highPrice, Integer categoryId) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        Page goodsPage = shopGoodsService.getShopGoodsByServiceOrPrice(page, ew, goodsType, lowPrice, highPrice, categoryId);
        // 每价格搜索一次,添加价格区间统计
        if (lowPrice != null && highPrice != null) {
            StatisticsPrice price = statisticsPriceService.selectOne(new EntityWrapper<StatisticsPrice>()
                    .eq(StatisticsPrice.LOW_PRICE, lowPrice)
                    .eq(StatisticsPrice.HIGH_PRICE, highPrice));
            if (price != null) {
                StatisticsPrice statisticsPrice = new StatisticsPrice();
                statisticsPrice.setLowPrice(lowPrice);
                statisticsPrice.setLowPrice(highPrice);
                statisticsPrice.setId(price.getId());
                Integer count = price.getCount();
                count++;
                statisticsPrice.setCount(count);
                statisticsPrice.updateById();
            } else {
                StatisticsPrice statisticsPrice = new StatisticsPrice();
                statisticsPrice.setLowPrice(lowPrice);
                statisticsPrice.setLowPrice(highPrice);
                statisticsPrice.setPercentage("1%");
                statisticsPrice.insert();
            }
        }
        return new Result().success(goodsPage);
    }*/


    //用户端-首页-王阳明全集
    //首页-分类-点击三级分类-查看商品详情
    @ApiOperation("用户端-根据商品ID查看商品信息")
    @RequestMapping(value = "/getShopGoodsById", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopGoodsById(@ApiParam(value = "商品ID", required = true) @RequestParam Integer id) {
        Result result = new Result();
        // 封装结果map
        Map<String, Object> map = MapUtil.newHashMap();
        GoodsSizeVo goodsSizeVo = shopGoodsService.getShopGoodsById(id);
        if (goodsSizeVo == null) {
            return result.erro("商品不存在");
        }

        map.put("shopGoods", goodsSizeVo);
       // map.put("shop_phone",managerUser.getPhone());
//        List<ShopGoodsSize> list = goodsSizeVo.getShopGoodsSize();
//        if(list != null && !list.isEmpty()){
//            for (ShopGoodsSize goodsSize : list) {
//                Integer sizeId = goodsSize.getId();
//                EntityWrapper<ShopRushBuySize> wrapper = new EntityWrapper<>();
//                wrapper.eq(ShopRushBuySize.GOODS_SIZE_ID,sizeId)
//                        .eq(ShopRushBuySize.IS_USE,1)
//                        .ne(ShopRushBuySize.IS_DEL,1);
//                List<ShopRushBuySize> shopRushBuySizes = shopRushBuySizeService.selectList(wrapper);
//                if( shopRushBuySizes == null || shopRushBuySizes.isEmpty())
//                    continue;
//                for (ShopRushBuySize shopRushBuySize : shopRushBuySizes) {
//                    EntityWrapper<ShopRushBuy> wrapper1 = new EntityWrapper<>();
//                    wrapper1.eq(ShopRushBuy.ID,shopRushBuySize.getRushBuyId());
//                    wrapper1.ne(ShopRushBuy.IS_DEL,1);
//                    ShopRushBuy shopRushBuy = shopRushBuyService.selectOne(wrapper1);
//                    ShopRushBuySizeVo vo = new ShopRushBuySizeVo();
//
//                }
//            }
//        }
        // 获取标签ID
        Integer labelId = goodsSizeVo.getGoodsLabelId();
        List<GoodsLabel> goodsLabelList = goodsLabelService.selectList(new EntityWrapper<GoodsLabel>()
            .eq(GoodsLabel.ID, labelId)
            .ne(GoodsLabel.IS_DEL, 1)
            .orderBy(GoodsLabel.RANK, false));
        map.put("goodsLabelList", goodsLabelList);
        return result.success(map);
    }

   /* @ApiOperation("用户端-商品搜索&店铺搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "type", value = "1=商品,2=店铺,3=两者", required = true, paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "name", value = "关键字", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "attributeId", value = "属性ID", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "modularId", value = "模块ID", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopListByLike", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopListByLike(Page page, Integer type, String name, Integer modularId, Integer attributeId) {
        if (type == 2) {
            // 店铺模糊查询
            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                    .eq(Shop.CAN_USE, 1)
                    .eq(Shop.MODULAR_ID, modularId)
                    .like(Shop.SHOP_NAME, name)
                    .orderBy(Shop.TOTAL_VOLUME, false));
            return new Result().success(shopPage);
        } else if (type == 1) {
            // 商品模糊查询
            MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsByLikeName(page, ew, type, name, attributeId);
            return new Result().success(goodsPage);
        } else {
            // 店铺&商品
            Map<String, Object> map = MapUtil.newHashMap();

            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                    .eq(Shop.CAN_USE, 1)
                    .eq(Shop.MODULAR_ID, modularId)
                    .like(Shop.SHOP_NAME, name)
                    .orderBy(Shop.TOTAL_VOLUME, false));

            MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopGoodsByLikeName(page, ew, type, name, attributeId);
            map.put("shop", shopPage);
            map.put("shopGoods", goodsPage);

            return new Result().success(map);
        }
    }*/


    //用户端-首页-商品(王阳明全集...)详情-根据商品id查询该商品对应的优惠券列表
    @ApiOperation("用户端-首页-商品详情-优惠券列表")
    @RequestMapping(value = "/getCouponListByGoodsId", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getCouponListByGoodsId(Integer goodsId) {
        /**
         * 根据商品id查询商品对应的店铺
         */
        ShopGoods shopgoods = shopGoodsService.selectById(goodsId);
        Integer shopId = shopgoods.getShopId();
        List<Coupon> list = couponService.selectCouponList(goodsId, shopId);

        if (list == null || list.size() == 0) {
            return new Result().erro("没有优惠券");
        }
        return new Result().success(list);
    }

    //用户端-首页-商品(王阳明全集...)详情-根据商品id查询该商品对应的优惠券列表-领取优惠券
    @ApiOperation("用户端-首页-商品详情-优惠券列表-领取")
    @RequestMapping("/getCoupon")
    public Result getCoupon(String price, Integer userId, @RequestBody Coupon coupon) {
        double goods_price = Double.parseDouble(price);
        /**
         * 判断商品价格是否满足优惠券门槛价格
         */
        if (new BigDecimal(goods_price).doubleValue() >= coupon.getFullPrice().doubleValue()) {   //满足门槛价格
            UserCoupon uc = new UserCoupon();
            uc.setUserId(userId);
            uc.setCouponId(coupon.getId());

//            if(new Date().before(coupon.getStartTime())){  //未开始
//                uc.setStatus(2);
//            }else if(new Date().after(coupon.getEndTime())){     //时间已经结束
//                uc.setStatus(3);
//            }else{
//                uc.setStatus(1);    //未使用
//            }
            uc.insert();
            return new Result().success("领取优惠券成功");

        } else {
            return new Result().erro("商品价格不满足优惠券领取价格");
        }

    }


    //首页-分类-点击三级分类-商品搜索
    @ApiOperation("首页-分类-点击三级分类-商品搜索")
    @RequestMapping(value = "/searchGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result searchGoods(String keywords) {
        List<GoodsSizeVo> goodsSizeVoList = shopGoodsService.getShopGoodsByKeywords(keywords);
        if (goodsSizeVoList == null || goodsSizeVoList.size() == 0) {
            return new Result().erro("没有搜索到相关商品");
        }
        return new Result().success(goodsSizeVoList);
    }


    @ApiOperation("用户端&商家端-通用商品搜索&店铺搜索")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "status", value = "1=商品,2=店铺,3=两者", required = true, paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "type", value = "1=价格降序/0升序,2=销量", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "name", value = "关键字", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attributeId", value = "属性ID", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "shopModularId", value = "模块ID", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "categoryId", value = "类型ID", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "brandId", value = "品牌ID", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "address", value = "区域地址", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "goodsType", value = "1=包邮,2=促销,3=新品", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "lowPrice", value = "最低价格", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "highPrice", value = "最高价格", paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "userId", value = "商家端-用户ID", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/getShopList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result getShopList(Page page, Integer status, Integer type, String name, Integer shopModularId, Integer attributeId, Integer categoryId,
                              Integer brandId, String address, Integer goodsType, String lowPrice, String highPrice, Integer userId) {
        // 获取店铺ID
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        Integer shopId = null;
        if (managerUser != null) {
            shopId = managerUser.getShopId();
        }
        // 商品模糊搜索
        if (status == 1) {
            MyEntityWrapper<ShopGoodsSize> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopList(page, ew, type, attributeId, name, shopModularId, categoryId, brandId,
                address, goodsType, lowPrice, highPrice, shopId);
            return new Result().success(goodsPage);
        } else if (status == 2) {
            // 店铺模糊查询
            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                .eq(Shop.CAN_USE, 1)
                .eq(Shop.MODULAR_ID, shopModularId)
                .like(Shop.SHOP_NAME, name)
                .like(Shop.ADDRESS, address)
                .orderBy(Shop.TOTAL_VOLUME, false));
            return new Result().success(shopPage);
        } else {
            // 店铺&商品
            Map<String, Object> map = MapUtil.newHashMap();

            Page shopPage = shopService.selectPage(page, new EntityWrapper<Shop>()
                .eq(Shop.CAN_USE, 1)
                .eq(Shop.MODULAR_ID, shopModularId)
                .like(Shop.SHOP_NAME, name)
                .like(Shop.ADDRESS, address)
                .orderBy(Shop.TOTAL_VOLUME, false));

            MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
            Page goodsPage = shopGoodsService.getShopList(page, ew, type, attributeId, name, shopModularId, categoryId, brandId, address, goodsType,
                lowPrice, highPrice, shopId);
            map.put("shop", shopPage.getRecords());
            map.put("shopGoods", goodsPage);

            return new Result().success(map);
        }
    }

    @ApiOperation("商家端-添加商品列表-查询自己店铺的所有商品")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
        @ApiImplicitParam(name = "userId", value = "商家id", paramType = "query", dataType = "integer")
    })
    @RequestMapping(value = "/addShopGoodsList", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addShopGoodsList(@RequestParam Integer userId, Integer current, Integer size) {
        current = current == null ? 1 : current;
        size = size == null ? 10 : size;
        Page page = new Page(current, size);
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if (managerUser == null) {
            return new Result().erro("系统错误,请稍后再试");
        }

        Integer shopId = managerUser.getShopId();
        MyEntityWrapper<ShopGoods> ew = new MyEntityWrapper<>();
        Page shopGoodsPage = shopGoodsService.findShopGoodsAndGoodsSizeByShopId(page, ew, shopId);
        return new Result().success(shopGoodsPage);
    }

    @ApiOperation("商家端-删除自己店铺的商品")
    @RequestMapping(value = "/delGoodsByShopId", method = {RequestMethod.POST, RequestMethod.GET})
    public Result delGoodsByShopId(@ApiParam(value = "商家id") Integer userId,
                                   @ApiParam(value = "商品id") Integer goodsId) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        Integer shopId = managerUser.getShopId();

        ShopGoods shopGoods = shopGoodsService.selectOne(new EntityWrapper<ShopGoods>()
            .eq(ShopGoods.SHOP_ID, shopId)
            .eq(ShopGoods.ID, goodsId)
        );
        if (shopGoods == null) {
            return new Result().erro("系统错误");
        }
        shopGoods.setIsDel(1);   //删除商品
        shopGoods.updateById();
        List<ShopGoodsSize> goodsSizeList = shopGoodsSizeService.selectList(new EntityWrapper<ShopGoodsSize>()
            .eq(ShopGoodsSize.SHOP_GOODS_ID, goodsId)
            .eq(ShopGoodsSize.IS_DEL, 0)
        );
        if (goodsSizeList != null && goodsSizeList.size() > 0) {
            for (ShopGoodsSize shopGoodsSize : goodsSizeList) {
                shopGoodsSize.setIsDel(1);
                shopGoodsSize.updateById();
            }
        }

        return new Result().success("删除成功");
    }

    @ApiOperation("商家端-添加商品")
    @RequestMapping(value = "/addShopGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result addShopGoods(@ModelAttribute AddGoodsVo addGoodsVo, Integer userId) {
        ManagerUser managerUser = managerUserService.selectOne(new EntityWrapper<ManagerUser>()
            .eq(ManagerUser.ID, userId));
        if (managerUser == null) {
            return new Result().erro("该商家不存在");
        }
        Integer shopId = managerUser.getShopId();

        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setShopId(shopId);
        shopGoods.setAttributeId(addGoodsVo.getAttributeId());
        shopGoods.setBrandId(addGoodsVo.getBrandId());
        shopGoods.setShopModularId(addGoodsVo.getShopModularId());
        shopGoods.setIsRecommend(addGoodsVo.getIsRecommend());
        shopGoods.setGoodsLabelId(addGoodsVo.getGoodsLabelId());
        shopGoods.setGoodsName(addGoodsVo.getGoodsName());
        shopGoods.setGoodsImg(addGoodsVo.getGoodsImg());
        shopGoods.setGoodsDesc(addGoodsVo.getGoodsDesc());
        shopGoods.setStatus(0);
        shopGoods.setIsDel(0);
        shopGoods.setCreateTime(new Date());
        shopGoods.setUpdateTime(new Date());
        shopGoods.setMainImage(addGoodsVo.getMainImage());
        shopGoods.setGoodsBreak(addGoodsVo.getGoodsBreak());
        shopGoods.setSaleAmont(addGoodsVo.getSaleAmont());
        shopGoods.setGoodsType(addGoodsVo.getGoodsType());
        shopGoods.setCategoryId(addGoodsVo.getCategoryId());
        shopGoods.setAddress(addGoodsVo.getAddress());

        try {
            shopGoods.insert();
            // 商品ID
            Integer id = shopGoods.getId();
/*            System.out.println("goodsId:"+id);

            String goodsSizeListJSON=addGoodsVo.getShopGoodsSizeList().toString();
            List<ShopGoodsSize> sizeList = JSON.parseArray(goodsSizeListJSON, ShopGoodsSize.class);
            for (ShopGoodsSize shopGoodsSize : sizeList) {
                System.out.println(shopGoodsSize.toString());
            }*/

            if (addGoodsVo.getShopGoodsSizeList() != null && addGoodsVo.getShopGoodsSizeList().size() > 0) {
                for (ShopGoodsSize shopGoodsSize : addGoodsVo.getShopGoodsSizeList()) {
                    shopGoodsSize.setShopGoodsId(id);
                    shopGoodsSize.setIsDel(0);
                    shopGoodsSize.insert();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result().erro("添加商品失败");
        }
        return new Result().success("添加成功");
    }

    @ApiOperation("商家端-修改商品")
    @RequestMapping(value = "/updateShopGoods", method = {RequestMethod.POST, RequestMethod.GET})
    public Result updateShopGoods(@ModelAttribute ShopGoods shopGoods, @ModelAttribute ShopGoodsSize shopGoodsSize,
                                  Integer shopGoodsId, Integer sizeId) {
        shopGoods.setId(shopGoodsId);
        shopGoods.updateById();
        // 规格id
        shopGoodsSize.setId(sizeId);
        shopGoodsSize.updateById();
        return new Result().success();
    }

    @ApiOperation("用户端 - 猜你喜欢")
    @GetMapping(value = "/guessYourLike")
    public Result guessYourLike() {
        return new Result().success(shopGoodsService.guessYourLike());
    }
}