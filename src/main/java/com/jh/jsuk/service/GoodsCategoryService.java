package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.utils.Result;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {

    Result getChildrenParallelCategory(Integer categoryId);

    Result addCategory(String categoryName, Integer parentId);

    Result updateCategoryName(Integer categoryId, String categoryName, Integer stas);

    Result selectCategoryAndChildrenById(Integer categoryId);

    /**
     * 删除自己以及下级所有数据
     * @param id
     */
    void deleteAll(Integer id);
}
