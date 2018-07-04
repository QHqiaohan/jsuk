package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.GoodsBrandDao;
import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.entity.vo.GoodsBrandVo;
import com.jh.jsuk.service.GoodsBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 类型的详细品牌 服务实现类
 * </p>
 *
 * @author xcr
 * @since 2018-06-21
 */
@Service
public class GoodsBrandServiceImpl extends ServiceImpl<GoodsBrandDao, GoodsBrand> implements GoodsBrandService {

    @Override
    public Page selectMPage(Page page, String kw, Wrapper<GoodsBrand> wrapper) {
        if(kw != null){
            wrapper.like(GoodsBrand.NAME,"%"+ kw + "%");
        }
        List<GoodsBrandVo> list = baseMapper.selectMPage(page,wrapper);
        return page.setRecords(list);
    }
}
