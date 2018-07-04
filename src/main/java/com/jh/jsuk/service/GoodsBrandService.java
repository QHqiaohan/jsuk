package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.GoodsBrand;

/**
 * <p>
 * 类型的详细品牌 服务类
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
public interface GoodsBrandService extends IService<GoodsBrand> {

    Page selectMPage(Page page, String kw, Wrapper<GoodsBrand> wrapper);
}
