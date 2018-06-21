package com.jh.jsuk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.UserEvaluateDao;
import com.jh.jsuk.entity.UserEvaluate;
import com.jh.jsuk.service.UserEvaluateService;
import com.jh.jsuk.entity.vo.EvaluateVo;
import com.jh.jsuk.entity.vo.EvaluateVoT;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评价 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class UserEvaluateServiceImpl extends ServiceImpl<UserEvaluateDao, UserEvaluate> implements UserEvaluateService {


    @Override
    public int calulateStar(String column, Wrapper wrapper) {
        return baseMapper.calulateStar(column, wrapper);
    }

    @Override
    public List<EvaluateVo> selectListByShopId(Page page, Wrapper wrapper) {
        return baseMapper.selectListByShopId(page, wrapper);
    }

    @Override
    public List<EvaluateVo> selectListAll(Page page, Wrapper wrapper) {
        return baseMapper.selectListAll(page, wrapper);
    }

    @Override
    public Page<EvaluateVoT> selectListAllForAdmin(Page page, Wrapper wrapper) {
        return page.setRecords(baseMapper.selectListAllForAdmin(page, wrapper));
    }

    @Override
    public Page listUser(Integer userId, Page page, Wrapper wrapper) {
        List<Map<String, Object>> list = baseMapper.listUserEvaluate(page, userId, wrapper);
        for (Map<String, Object> map : list) {
            Object goodsName = map.get("goodsName");
            map.put("goodsName", goodsName == null ? "" : goodsName);
            String goodsImg = (String) map.get("goodsImg");
            map.put("goodsImg", StrUtil.isBlank(goodsImg) ? new String[]{} : goodsImg.split(","));
        }
        page.setRecords(list);
        return page;
    }

}
