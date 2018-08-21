package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.ShopUserTiXianVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;

/**
 * <p>
 * 用户提现记录 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
public interface UserTiXianService extends IService<UserTiXian> {

    void tixian(UserTiXian userTiXian) throws Exception;


    Page<ShopUserTiXianVo> selectByAdvance(Page page, String tixianId, Integer begin, Integer end, Integer status);

    Page<UserTiXianVo> selectByAdvance2(Page page, String tixianId, Integer begin, Integer end, Integer status);

    void pass(Integer id);

    void decline(Integer id);
}
