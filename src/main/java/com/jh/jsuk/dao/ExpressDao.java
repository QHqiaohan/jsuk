package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Express;
import com.jh.jsuk.entity.vo.ExpressVo;
import com.jh.jsuk.entity.vo.ExpressVo2;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 快递跑腿 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface ExpressDao extends BaseMapper<Express> {

    List getExpressListBy(Page page, @Param("ew") Wrapper wrapper, @Param("status") Integer status, @Param("type") Integer type, @Param("userId")
            Integer userId);

    List<ExpressVo> getDeliverList(Page page, @Param("ew") Wrapper ew, @Param("status") Integer[] status,
                                   @Param("type") Integer type, @Param("userId") Integer userId);

    List<ExpressVo2> listPage(Page page, @Param("ew") EntityWrapper wrapper);

    ExpressVo2 detail(@Param("ew") EntityWrapper wrapper);

    int deliverRobbingOrder(@Param("userId") Integer userId,@Param("expressId") Integer expressId,@Param("payed") Integer payed
            ,@Param("waitDeliver") Integer waitDeliver);
}
