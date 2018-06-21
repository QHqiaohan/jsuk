package com.jh.jsuk.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.UserEvaluate;
import com.jh.jsuk.entity.vo.EvaluateVo;
import com.jh.jsuk.entity.vo.EvaluateVoT;

import java.util.List;

/**
 * <p>
 * 用户评价 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface UserEvaluateService extends IService<UserEvaluate> {

    int calulateStar(String column, Wrapper wrapper);

    List<EvaluateVo> selectListByShopId(Page page, Wrapper wrapper);

    List<EvaluateVo> selectListAll(Page page, Wrapper wrapper);

    Page<EvaluateVoT> selectListAllForAdmin(Page page, Wrapper wrapper);

    Page listUser(Integer userId, Page page, Wrapper wrapper);

}
