package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.ShopUser;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

/**
 * Author: xyl
 * Date:2018/7/31 16:27
 * Description:商户信息
 */
public interface ShopUserService extends IService<ShopUser> {

    Page list(Page page, String userName, String name, String[] sectionTime) throws ParseException;
}
