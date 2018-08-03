package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.Banner;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Author:xyl
 * Date:2018/8/3 14:48
 * Description:banner图片
 */
@Data
public class BannerVo implements Serializable {

    private List<Banner> banners;

    private String location;

    private Integer bannerLocation;
}
