package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopUser;
import com.jh.jsuk.entity.vo.ShopUserVo;

import java.text.ParseException;
import java.util.List;

/**
 * Author: xyl
 * Date:2018/7/31 16:27
 * Description:商户信息
 */
public interface ShopUserService extends IService<ShopUser> {

    Page list(Page page, String userName, String name, String[] sectionTime, Integer cityId) throws ParseException;

    List<ShopUserVo> excelData(Integer[] ids);
}
