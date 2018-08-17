package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.entity.vo.ShopUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: xyl
 * Date:2018/7/31 15:51
 * Description:商户信息
 */
public interface ShopUserDao extends BaseMapper<ShopUser> {

    /**
     * 通过关键字以及注册时间查询
     *
     * @param page
     * @param userName  商户账号
     * @param name      商户昵称
     * @param startTime 注册时间区间开始
     * @param endTime   注册时间区间结束
     * @return
     */
    List<ShopUserVo> listShopUser(Page page, @Param("userName") String userName, @Param("name") String name,
                                  @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("cityId") Integer cityId);

    /**
     * 通过商户id查询
     *
     * @param id
     * @return
     */
    ShopUserVo getShopUserById(Integer id);
}
