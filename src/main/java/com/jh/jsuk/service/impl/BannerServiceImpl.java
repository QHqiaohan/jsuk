package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jh.jsuk.entity.Banner;
import com.jh.jsuk.dao.BannerDao;
import com.jh.jsuk.entity.vo.BannerVo;
import com.jh.jsuk.service.BannerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Banner 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerDao, Banner> implements BannerService {

    @Override
    public List<BannerVo> listBanners() {
        List<BannerVo> bannerVos = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            BannerVo bannerVo = new BannerVo();
            String location = null;
            List<Banner> banners = selectList(new EntityWrapper<Banner>()
                .eq(Banner.BANNER_LOCATION, i)
                .where("is_valid = 1"));
            switch (i) {
                case 0:
                    location = "首页banner";
                    break;
                case 1:
                    location = "城乡优选banner";
                    break;
                case 2:
                    location = "特色家乡banner";
                    break;
                case 3:
                    location = "会员商城banner";
                    break;
                case 4:
                    location = "乡村旅游banner";
                    break;
                case 5:
                    location = "二手市场banner";
                    break;
                case 6:
                    location = "便捷生活banner";
                    break;
                case 7:
                    location = "分类banner";
                    break;
                case 8:
                    location = "聚鲜U客banner";
                    break;
                case 9:
                    location = "本地商城banner";
                    break;
                case 10:
                    location = "直销平台banner";
                    break;
                case 11:
                    location = "快递跑腿banner";
                    break;
            }
            bannerVo.setLocation(location);
            bannerVo.setBanners(banners);
            bannerVo.setBannerLocation(i);
            bannerVos.add(bannerVo);
        }
        return bannerVos;
    }
}
