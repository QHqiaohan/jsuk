package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Express;
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
}
