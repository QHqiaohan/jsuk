package com.jh.jsuk.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jh.jsuk.dao.GoodsEvaluateDao;
import com.jh.jsuk.entity.GoodsEvaluate;
import com.jh.jsuk.entity.vo.GoodsEvaluateVo;
import com.jh.jsuk.envm.GoodsEvalType;
import com.jh.jsuk.service.GoodsEvaluateService;
import com.jh.jsuk.utils.EnumUitl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class GoodsEvaluateServiceImpl extends ServiceImpl<GoodsEvaluateDao, GoodsEvaluate> implements GoodsEvaluateService {

    @Override
    public List<GoodsEvaluate> get(Integer goodsId, Integer count) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0)
                .orderBy(GoodsEvaluate.CREATE_TIME, false);
        return baseMapper.list(wrapper, count);
    }

    @Override
    public Integer count(Integer goodsId) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0);
        return selectCount(wrapper);
    }

    @Override
    public Page listPage(Integer goodsId, String type, Page page) throws Exception {
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0)
                .eq(GoodsEvaluate.IS_SHOW,1)
                .orderBy(GoodsEvaluate.CREATE_TIME, false);
        GoodsEvalType evalType = null;
        if (!"all".equalsIgnoreCase(type)) {
            evalType = EnumUitl.toEnum(GoodsEvalType.class, type);
        }
        if (evalType != null) {
            wrapper.in(GoodsEvaluate.STAR_NUMBER, evalType.getStars());
        }
        return selectPage(page, wrapper);
    }

    @Override
    public Map<String, Object> counts(Integer goodsId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Wrapper<GoodsEvaluate> wrapper = new EntityWrapper<>();
        wrapper.eq(GoodsEvaluate.GOODS_ID, goodsId)
                .eq(GoodsEvaluate.IS_DEL, 0);
        map.put("all",selectCount(wrapper));
        for (GoodsEvalType evalType : GoodsEvalType.values()) {
            wrapper.in(GoodsEvaluate.STAR_NUMBER, evalType.getStars());
            map.put(evalType.getKey(),selectCount(wrapper));
        }
        return map;
    }

    @Override
    public Page listEvaluate(Page page, String categoryId, String keyWord, String brandId, Integer shopId, String nickName) {
        if (keyWord != null)
            keyWord = "%" + keyWord.trim() + "%";
        if(nickName != null)
            nickName = "%" + nickName.trim() + "%";
        List<GoodsEvaluateVo> list = baseMapper.listEvaluate(page, categoryId, keyWord, brandId,shopId,nickName);
        return page.setRecords(list);
    }

    @Override
    public Page listUser(Integer userId, Page page, Wrapper wrapper) {
        List<Map<String, Object>> list = baseMapper.listUserEvaluate(page, userId, wrapper);
        for (Map<String, Object> map : list) {
            Object goodsName = map.get("goodsName");
            map.put("goodsName", goodsName == null ? "" : goodsName);
            String goodsImg = (String) map.get("goodsImg");
            map.put("goodsImg", StrUtil.isBlank(goodsImg) ? new Integer[]{} : goodsImg.split(","));
        }
        page.setRecords(list);
        return page;
    }
}
