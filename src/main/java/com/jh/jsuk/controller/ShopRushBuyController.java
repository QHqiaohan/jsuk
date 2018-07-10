package com.jh.jsuk.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopRushBuy;
import com.jh.jsuk.entity.vo.GoodsSalesPriceVo;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopRushBuyService;
//import com.jh.jsuk.service.ShopRushBuySizeService;
import com.jh.jsuk.utils.DatecConvertUtils;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.R;
import com.jh.jsuk.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * 秒杀信息配置 前端控制器
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Api(tags = "限时秒杀相关:")
@RestController
@RequestMapping(value = "/shopRushBuy", method = {RequestMethod.POST, RequestMethod.GET})
public class ShopRushBuyController {

    @Autowired
    private ShopGoodsService shopGoodsService;
    @Autowired
    private ShopRushBuyService shopRushBuyService;
//    @Autowired
//    private ShopRushBuySizeService shopRushBuySizeService;

    @GetMapping("/page")
    public R listPage(Page page){
        Wrapper<ShopRushBuy> wrapper = new EntityWrapper<>();
        wrapper.ne(ShopRushBuy.IS_DEL,1);
        return R.succ(shopRushBuyService.selectPage(page, wrapper));
    }

    @ApiOperation("秒杀时间列表")
    @RequestMapping("/getKillTime")
    public Result getKillTime() {
        List<ShopRushBuy> shopRushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                .orderBy(ShopRushBuy.START_TIME));
        if (CollectionUtils.isEmpty(shopRushBuyList)) {
            return new Result().success("秒杀配置还未设置", null);
        } else {
            return new Result().success(shopRushBuyList);
        }
    }

    @ApiOperation(value = "根据时间查询秒杀商品", notes = "status:0=未开始,1=进行中,2=已结束")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", dataType = "integer"),
            @ApiImplicitParam(name = "id", value = "秒杀时间ID", required = true, paramType = "query", dataType = "integer")
    })
    @RequestMapping("/findKillShopGoods")
    public Result findKillShopGoods(Page page, Integer id) {
        if (id == null) {
            return new Result().erro("参数错误");
        }
        ShopRushBuy shopRushBuy = shopRushBuyService.selectById(id);
        String formatDateTime = DateUtil.formatDateTime(shopRushBuy.getStartTime());
        LocalTime killT = DatecConvertUtils.StrToDate(formatDateTime);
        // 获取当前时间
        LocalTime localTime = LocalTime.now();
        if (localTime.isAfter(killT)) {
            // 当前时间在秒杀之后,已结束
            if (shopRushBuy == null) {
                return new Result().success("秒杀配置还未设置", null);
            }
            Page goodsPage = getRushBuyList(page, shopRushBuy);
            shopRushBuy.setStatus(2);
            return new Result().success(goodsPage);
        } else if (localTime.isAfter(killT) == false) {
            // 当前时间没到秒杀时间,未开始
            List<ShopRushBuy> rushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                    .between(ShopRushBuy.START_TIME, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date()))
                    .orderBy(ShopRushBuy.START_TIME));
            if (CollectionUtils.isEmpty(rushBuyList))
                return new Result().success("秒杀配置还未设置", null);
            Page goodsPage = getRushBuyList(page, shopRushBuy);
            shopRushBuy.setStatus(0);

            return new Result().success(goodsPage);
        } else {
            // 秒杀进行中
            List<ShopRushBuy> rushBuyList = shopRushBuyService.selectList(new EntityWrapper<ShopRushBuy>()
                    .between(ShopRushBuy.START_TIME, DateUtil.beginOfDay(DateUtil.date()), DateUtil.endOfDay(DateUtil.date()))
                    .orderBy(ShopRushBuy.START_TIME));
            if (CollectionUtils.isEmpty(rushBuyList))
                return new Result().success("秒杀配置还未设置", null);
            Page goodsPage = getRushBuyList(page, shopRushBuy);
            shopRushBuy.setStatus(1);
            return new Result().success(goodsPage);
        }
    }

    private Page getRushBuyList(Page page, ShopRushBuy shopRushBuy) {
        MyEntityWrapper<GoodsSalesPriceVo> ew = new MyEntityWrapper<>();
        return shopRushBuyService.getShopRushBuyList(page, ew, shopRushBuy.getStartTime(), shopRushBuy.getEndTime());
    }

}

