package com.jh.jsuk.service;

import com.baomidou.mybatisplus.service.IService;
import com.jh.jsuk.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lpf
 * @since 2018-06-20
 */
public interface DictService extends IService<Dict> {


    List<Map<String,Object>> getDict(String type,String code) throws Exception;

}
