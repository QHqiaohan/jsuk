package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jh.jsuk.entity.UserOrderGoods;
import com.jh.jsuk.entity.vo.UserOrderGoodsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 订单商品关联 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-28
 */
public interface UserOrderGoodsDao extends BaseMapper<UserOrderGoods> {

    @Select({
            "select * from js_user_order_goods where order_id = #{orderId}"
    })
//    @ResultMap("com.jh.jsuk.dao.OrderGoodsDao.OrderGoodsVo")
    List<UserOrderGoodsVo> findByOrderId(Integer orderId);

}
