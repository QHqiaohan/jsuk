package com.jh.jsuk.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.tj123.common.RedisUtils;
import com.jh.jsuk.conf.RedisKeys;
import com.jh.jsuk.dao.UserOrderDao;
import com.jh.jsuk.dao.UserOrderGoodsDao;
import com.jh.jsuk.entity.*;
import com.jh.jsuk.entity.dto.ShopSubmitOrderDto;
import com.jh.jsuk.entity.dto.ShopSubmitOrderGoodsDto;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.entity.vo.OrderResponse;
import com.jh.jsuk.entity.vo.UserOrderDetailVo;
import com.jh.jsuk.entity.vo.UserOrderVo;
import com.jh.jsuk.envm.OrderResponseStatus;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.envm.OrderType;
import com.jh.jsuk.service.*;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.ShopJPushUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<UserOrderDao, UserOrder> implements UserOrderService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserOrderGoodsDao orderGoodsDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopGoodsService shopGoodsService;

    @Autowired
    ShopGoodsSizeService shopGoodsSizeService;

    @Autowired
    private ShopUserService shopUserService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    private CouponService couponService;


    @Override
    public int statusCount(OrderStatus orderStatus, Integer shopId) {
        EntityWrapper<UserOrder> wrapper = new EntityWrapper<>();
        if (orderStatus != null) {
            wrapper.eq(UserOrder.STATUS, orderStatus.getKey());
        }
        wrapper.ne(UserOrder.IS_USER_DEL, 1);
        return selectCount(wrapper);
    }

    @Override
    public List<UserOrderVo> findVoByPage(Page page, Wrapper wrapper) {
        return baseMapper.findVoByPage(page, wrapper);
    }

    @Override
    public Page<UserOrderVo> findVoPage(Page page, Wrapper wrapper) {
        SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(this.baseMapper.findVoByPage(page, wrapper));
        return page;
    }

    @Override
    public UserOrderVo findVoById(Integer id) {
        return baseMapper.findVoById(id);
    }

    @Override
    public void returnStock(Integer orderId) {
        try {
            List<UserOrderGoods> goodsList = orderGoodsDao
                    .selectList(new EntityWrapper<UserOrderGoods>()
                            .eq("order_id", orderId));
            for (UserOrderGoods goods : goodsList) {
                shopGoodsService.returnStock(goods.getGoodsId(), goods.getNum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void remindingOrderTaking() {
        List<UserOrder> orders = this.selectList(new EntityWrapper<UserOrder>()
                .eq("status", 1)
                .eq("is_unsubscribe", 0));
        for (UserOrder order : orders) {
            try {
                Integer shopId = order.getShopId();
                ShopUser shopUser = shopUserService.selectOne(new EntityWrapper<ShopUser>().eq("shop_id", shopId));
                ShopJPushUtils.pushMsgMusic(shopUser.getId() + "", "您有新的订单请注意接单", "", null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public Page getOrderByUserId(Page page, Wrapper wrapper, Integer userId, Integer status, String goodsName) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getOrderByUserId(page, wrapper, userId, status, goodsName));
        return page;
    }

    @Override
    public Page getShopOrderByUserId(Page page, Wrapper wrapper, Integer shopId, Integer status, String goodsName) {
        wrapper = SqlHelper.fillWrapper(page, wrapper);
        page.setRecords(baseMapper.getShopOrderByUserId(page, wrapper, shopId, status, goodsName));
        return page;
    }

    @Override
    public Page listPage(Page page, List<String> date, String kw, OrderStatus orderStatus) {
        String start = null, stop = null;
        if (date != null && !date.isEmpty()) {
            start = date.get(0);
            stop = date.get(1);
        }
        if (kw != null) {
            kw = "%" + kw.trim() + "%";
        }
        EntityWrapper wrapper = new EntityWrapper();
        if (StrUtil.isNotBlank(kw)) {
            wrapper.eq(UserOrder.ORDER_NUM, kw);
        }
        if (StrUtil.isNotBlank(start) && StrUtil.isNotBlank(stop)) {
            wrapper.gt(UserOrder.CREAT_TIME, DateTime.of(start, "yyyy-MM-dd"));
            wrapper.lt(UserOrder.CREAT_TIME, DateTime.of(stop, "yyyy-MM-dd"));
        }
        if (orderStatus != null) {
            wrapper.eq(UserOrder.STATUS, orderStatus.getKey());
        }
        wrapper.ne(UserOrder.IS_USER_DEL, 1);
        List<UserOrderVo> list = baseMapper.findVoByPage(page, wrapper);
        return page.setRecords(list);
    }

    @Override
    public UserOrderDetailVo userOrderDetail(Integer orderId) {
        return baseMapper.userOrderDetail(orderId);
    }

    @Override
    public Integer orderCount(Integer userId) {
        EntityWrapper<UserOrder> wrapper = new EntityWrapper<>();
        wrapper.ne(UserOrder.IS_USER_DEL, 1)
                .eq(UserOrder.USER_ID, userId);
        return selectCount(wrapper);
    }

    @Override
    public Page userOrder(Page page, Integer id) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq(UserOrder.USER_ID, id);
        wrapper.ne(UserOrder.IS_USER_DEL, 1);
        List<UserOrderVo> list = baseMapper.findVoByPage(page, wrapper);
        return page.setRecords(list);
    }

    @Override
    public String pushAPush(Integer orderId) {
        //获取订单详情
        UserOrderDetailVo orderDetail = userOrderDetail(orderId);
        //获取商家信息
        ShopUser shopUser = shopUserService.selectOne(new EntityWrapper<ShopUser>().eq("shop_id", orderDetail.getShopId()));
        //获取买家信息
        User user = userService.selectOne(new EntityWrapper<User>().eq("id", orderDetail.getUserId()));
        return ShopJPushUtils.pushMsg(shopUser.getId() + "",
                "订单(" + orderId + ")请尽快发货！催单人：" + user.getNickName() + "",
                "用户催单", null) ? "催单成功" : "催单失败";
    }

    /**
     * 生成订单号
     *
     * @return
     */
    public synchronized String createOrderNum() throws Exception {
        String key = RedisKeys.SHOP_GOODS_ORDER_NUM;
        Long count = null;
        if (redisUtils.hasKey(key)) {
            count = redisUtils.autoIncrement(key);
        }
        if (count == null) {
            count = (long) selectCount(null);
            redisUtils.setStr(key, String.valueOf(count));
        }
        return RandomUtil.randomNumbers(6) + String.format("%06d", count);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public OrderResponse createOrder(SubmitOrderDto orderDto, ShopSubmitOrderDto orderGoods,
                                     OrderType orderType, Integer userId) throws Exception {
        OrderResponse response = new OrderResponse();
        UserOrder o = new UserOrder();
        Date createTime = new Date();
        List<ShopSubmitOrderGoodsDto> goods = orderGoods.getGoods();
        List<UserOrderGoods> gs = new ArrayList<>();
        List<ShopSubmitOrderGoodsDto> failedGoods = new ArrayList<>();
        Iterator<ShopSubmitOrderGoodsDto> iterator = goods.iterator();
        while (iterator.hasNext()) {
            int column;
            ShopSubmitOrderGoodsDto good = iterator.next();
            if (OrderType.NORMAL.equals(orderType)) {
                column = baseMapper.updateStock(good.getGoodsSizeId(), good.getNum());
            } else {
                column = baseMapper.updateKillStock(good.getGoodsSizeId(), good.getNum());
            }
            //秒杀成功
            if (column > 0) {
                UserOrderGoods g = new UserOrderGoods();
                g.setGoodsId(good.getGoodsId());
                g.setGoodsSizeId(good.getGoodsSizeId());
                g.setNum(good.getNum());
                g.setGoodsPrice(good.getGoodsPrice());
                g.setPublishTime(createTime);
                gs.add(g);
                // 秒杀不成功
            } else {
                failedGoods.add(good);
                iterator.remove();
            }
        }
        if (goods.size() > 0) {
            o.setOrderNum(createOrderNum());
            o.setDistributionTime(orderDto.getDistributionTime());
            o.setDistributionType(orderDto.getDistributionType());
            o.setCreatTime(createTime);
            o.setStatus(OrderStatus.DUE_PAY.getKey());
            o.setIsUserDel(0);
            o.setIsShopDel(0);
            o.setIsClosed(0);
            o.setShopId(orderGoods.getShopId());
            o.setAddressId(orderDto.getAddressId());
            o.setUserId(userId);
            o.setCouponId(orderGoods.getUserCouponId());
            o.setOrderType(orderDto.getOrderType());
            o.setIntegralRuleId(orderGoods.getIntegralRuleId());
            o.setFullReduceId(orderGoods.getFullReduceId());
            o.setOrderPrice(orderPrice(orderGoods, orderType, userId));
            o.insert();
            Integer orderId = o.getId();
            response.setOrderId(orderId);
            response.setOrderNum(o.getOrderNum());
            for (UserOrderGoods g : gs) {
                g.setOrderId(orderId);
                g.insert();
            }
            response.setStatus(OrderResponseStatus.SUCCESS);
        } else {
            response.setStatus(OrderResponseStatus.FAILED);
        }
        if (failedGoods.size() > 0) {
            response.setStatus(OrderResponseStatus.PARTLY_SUCCESS);
        }
        return response;
    }

    @Override
    public List<OrderResponse> submit(SubmitOrderDto orderDto, Integer userId) throws Exception {
        List<OrderResponse> list = new ArrayList<>();
        List<ShopSubmitOrderDto> shops = orderDto.getShops();
        OrderType orderType = EnumUitl.toEnum(OrderType.class, orderDto.getOrderType());
        for (ShopSubmitOrderDto shop : shops) {
            if (shop.getGoods().size() == 0) {
                continue;
            }
            list.add(createOrder(orderDto, shop, orderType, userId));
        }
        return list;
    }

    @Override
    public BigDecimal orderPrice(ShopSubmitOrderDto orderDto, OrderType orderType, Integer userId) throws Exception {
/**
 *      用户-购物车-去结算
 *      * 金额计算（折扣、优惠券、积分来计算订单价格）
 *      * 计算订单金额
 *      * 更新用户积分总数
 */
        //先计算没有使用任何优惠的订单原价
        double totalPriceWithOutDiscount = 0;
        ArrayList<ShopSubmitOrderGoodsDto> goodsList = orderDto.getGoods();
        for (ShopSubmitOrderGoodsDto goodsDto : goodsList) {
            //订单项的价格
            double orderItemPrice = goodsDto.getGoodsPrice().doubleValue() * goodsDto.getNum();
            totalPriceWithOutDiscount += orderItemPrice;
        }

        //店铺id
        Integer shopId = orderDto.getShopId();
        //优惠券id
        Integer userCouponId = orderDto.getUserCouponId();
        //根据优惠券id和shopId查询对应的优惠券
        Coupon coupon = couponService.selectOne(new EntityWrapper<Coupon>()
                .eq(Coupon.ID, userCouponId)
                .eq(Coupon.SHOP_ID, shopId)
        );
        double discount = 0;
        //优惠券开始时间和结束时间判断
        if (new Date().after(coupon.getStartTime()) && new Date().before(coupon.getEndTime()) && totalPriceWithOutDiscount >= coupon.getFullPrice().doubleValue()) {
            //可以使用优惠券
            //获取优惠券折扣
            discount = coupon.getDiscount().doubleValue();
        }
        totalPriceWithOutDiscount = totalPriceWithOutDiscount - discount;   //减去优惠券的折扣

        //计算积分抵扣


        return new BigDecimal("0.0");
    }

}
