package com.jh.jsuk.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jh.jsuk.dao.GoodsCategoryDao;
import com.jh.jsuk.entity.GoodsCategory;
import com.jh.jsuk.service.GoodsCategoryService;
import com.jh.jsuk.utils.MyEntityWrapper;
import com.jh.jsuk.utils.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryDao, GoodsCategory> implements GoodsCategoryService {

    @Autowired
    GoodsCategoryDao goodsCategoryDao;
    private Logger logger = LoggerFactory.getLogger(GoodsCategoryServiceImpl.class);

    @Override
    public Result getChildrenParallelCategory(Integer categoryId) {
        List<GoodsCategory> categoryList = goodsCategoryDao.selectList(new MyEntityWrapper<GoodsCategory>().eq(GoodsCategory.PARENT_ID, categoryId));
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return new Result().success(categoryList);
    }

    @Override
    public Result addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return new Result().erro("添加品类参数错误");
        }
        GoodsCategory category = new GoodsCategory();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setCreateTime(new Date());
        category.setStatus(1);//这个分类是可用的

        int rowCount = goodsCategoryDao.insert(category);
        if (rowCount > 0) {
            return new Result().success("添加品类成功");
        }
        return new Result().erro("添加品类失败");
    }

    @Override
    public Result updateCategoryName(Integer categoryId, String categoryName, Integer status) {
        if (categoryId == null) {
            return new Result().erro("添加品类参数错误");
        }
        GoodsCategory category = new GoodsCategory();
        category.setId(categoryId);
        if (StringUtils.isNotBlank(categoryName)) {
            category.setName(categoryName);
        }
        if (status != null) {
            category.setStatus(status);
        }
        int rowCount = goodsCategoryDao.updateById(category);
        if (rowCount > 0) {
            return new Result().success("修改品类成功");
        }
        return new Result().erro("修改品类失败");
    }

    @Override
    public Result selectCategoryAndChildrenById(Integer categoryId) {
        Set<GoodsCategory> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (GoodsCategory categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return new Result().success(categoryIdList);
    }

    //递归算法,算出子节点
    private Set<GoodsCategory> findChildCategory(Set<GoodsCategory> categorySet, Integer categoryId) {
        GoodsCategory category = goodsCategoryDao.selectById(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<GoodsCategory> categoryList = goodsCategoryDao.selectList(new MyEntityWrapper<GoodsCategory>().eq(GoodsCategory.PARENT_ID, categoryId));
        for (GoodsCategory categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
