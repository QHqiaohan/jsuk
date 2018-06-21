package com.jh.jsuk.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jh.jsuk.entity.UserEvaluate;
import com.jh.jsuk.entity.vo.EvaluateVo;
import com.jh.jsuk.entity.vo.EvaluateVoT;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评价 Mapper 接口
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserEvaluateDao extends BaseMapper<UserEvaluate> {

    int calulateStar(@Param("column") String column, @Param("ew") Wrapper wrapper);

    List<EvaluateVo> selectListByShopId(Page page, @Param("ew") Wrapper wrapper);

    List<EvaluateVo> selectListAll(Page page, @Param("ew") Wrapper wrapper);

    List<EvaluateVoT> selectListAllForAdmin(Page page, @Param("ew") Wrapper wrapper);

    List<Map<String,Object>> listUserEvaluate(Page page, @Param("userId") Integer userId, @Param("ew") Wrapper wrapper);

}
