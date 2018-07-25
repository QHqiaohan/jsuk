package com.jh.jsuk;

import cn.hutool.core.util.RandomUtil;
import com.jh.jsuk.entity.ShopGoodsSize;
import com.jh.jsuk.entity.dto.ShopSubmitOrderDto;
import com.jh.jsuk.entity.dto.ShopSubmitOrderGoodsDto;
import com.jh.jsuk.entity.dto.SubmitOrderDto;
import com.jh.jsuk.service.ShopGoodsSizeService;
import com.jh.jsuk.service.UserOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    UserOrderService orderService;

    @Autowired
    ShopGoodsSizeService goodsSizeService;


    public SubmitOrderDto create(){
        SubmitOrderDto dto = new SubmitOrderDto();
        dto.setOrderType(0);
        ArrayList<ShopSubmitOrderDto> shops = new ArrayList<>();

        ShopSubmitOrderDto dddd = new ShopSubmitOrderDto();
        ArrayList<ShopSubmitOrderGoodsDto> goods = new ArrayList<>();
        ShopSubmitOrderGoodsDto dfe = new ShopSubmitOrderGoodsDto();
        dfe.setGoodsSizeId(2);
        dfe.setNum(RandomUtil.randomInt(1,10));
        goods.add(dfe);

        ShopSubmitOrderGoodsDto df2 = new ShopSubmitOrderGoodsDto();
        df2.setGoodsSizeId(4);
        df2.setNum(RandomUtil.randomInt(1,10));
        goods.add(df2);
        dddd.setGoods(goods);
        shops.add(dddd);

        dto.setShops(shops);
        return dto;
    }

    @Test
    public void testOrder() throws Exception {

//        orderService.submit(create(),12);
    }

    final static int COUNT = 4000;

    /**
     * 测试 sql 语句
     *
     * delete from js_user_order where id > 716;
     *
     * delete from js_user_order_goods where order_id > 716 ;
     *
     * update js_shop_goods_size s set s.stock = 500 where s.id = 2;
     *
     * update js_shop_goods_size s set s.stock = 1478 where s.id = 4;
     *
     * select sum(num) from js_user_order_goods where order_id > 716;
     *
     *
     * @throws Exception
     */

    @Test
    public void catchOrder() throws Exception {
        CountDownLatch latch = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; i++) {
            new Thread(()->{

                try {
                    latch.countDown();
                    latch.await();
//                    orderService.submit(create(),RandomUtil.randomInt(900));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
        latch.await();
        Thread.sleep(60000);
    }







    @Test
    public void recoverCount(){
        ShopGoodsSize e = new ShopGoodsSize();
        e.setId(2);
        e.setStock(12);
        e.updateById();
        ShopGoodsSize e2 = new ShopGoodsSize();
        e2.setId(4);
        e2.setStock(15);
        e2.updateById();
    }

}
