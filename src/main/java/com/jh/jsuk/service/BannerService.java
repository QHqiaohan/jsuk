package com.jh.jsuk.service;

import com.jh.jsuk.entity.Banner;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.vo.BannerVo;

import java.util.List;

/**
 * <p>
 * Banner 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface BannerService extends IService<Banner> {
    /**
     * 获取所有banner
     */
    List<BannerVo> listBanners();
}
