package com.jh.jsuk.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserOrderDao;
import com.jh.jsuk.dao.UserOrderGoodsDao;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.entity.UserOrderGoods;
import com.jh.jsuk.entity.vo.UserOrderVo;
import com.jh.jsuk.envm.OrderStatus;
import com.jh.jsuk.service.ShopGoodsService;
import com.jh.jsuk.service.ShopUserService;
import com.jh.jsuk.service.UserOrderService;
import com.jh.jsuk.utils.ShopJPushUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ShopGoodsService shopGoodsService;

    @Autowired
    private ShopUserService shopUserService;

    @Override
    public int statusCount(OrderStatus orderStatus, Integer shopId) {
        EntityWrapper<UserOrder> wrapper = new EntityWrapper<>();
        if (orderStatus != null) {
            wrapper.eq(UserOrder.STATUS, orderStatus.getKey());
        }
        wrapper.ne(UserOrder.IS_DEL,1);
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
        wrapper.ne(UserOrder.IS_DEL,1);
        List<UserOrderVo> list = baseMapper.findVoByPage(page, wrapper);
        return page.setRecords(list);
    }

}
