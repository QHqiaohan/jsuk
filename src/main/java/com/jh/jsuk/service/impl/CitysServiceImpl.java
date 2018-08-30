package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.CitysDao;
import com.jh.jsuk.entity.Citys;
import com.jh.jsuk.service.CitysService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class CitysServiceImpl extends ServiceImpl<CitysDao, Citys> implements CitysService {

    @Override
    public Page page(Page page, String kw) {
        List<Map<String, Object>> list = baseMapper.page(page, kw);
        return page.setRecords(list);
    }

    @Override
    public Integer getcitynumBycityname(String cityname) {
        return baseMapper.getcitynumBycityname(cityname);
    }
}
