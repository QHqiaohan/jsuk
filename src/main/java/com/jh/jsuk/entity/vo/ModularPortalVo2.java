package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ModularPortal;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Author:xyl
 * Date:2018/8/3 17:29
 * Description:后台 - 专题列表
 */
@Getter
@Setter
public class ModularPortalVo2 implements Serializable {

    private Integer parentId;

    private String parentName;
    /**
     * 子专题
     */
    private List<ModularPortal> modularPortals;
}
