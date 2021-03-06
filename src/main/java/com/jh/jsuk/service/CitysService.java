package com.jh.jsuk.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.Citys;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 城市 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface CitysService extends IService<Citys> {

    Page page(Page page, String kw);
    Integer getcitynumBycityname(String cityname);
}
