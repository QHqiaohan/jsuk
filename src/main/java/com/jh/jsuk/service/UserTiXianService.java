package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserTiXian;
import com.jh.jsuk.entity.vo.ShopUserTiXianVo;
import com.jh.jsuk.entity.vo.UserTiXianVo;
import com.jh.jsuk.utils.Result;

/**
 * <p>
 * 用户提现记录 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-07-01
 */
public interface UserTiXianService extends IService<UserTiXian> {

    Result tixian(UserTiXian userTiXian, Integer type, Integer userId);


    Page<ShopUserTiXianVo> selectByAdvance(Page page, Integer tixianId, Integer begin, Integer end, Integer status);

    Page<UserTiXianVo> selectByAdvance2(Page page, Integer tixianId, Integer begin, Integer end, Integer status);
}
