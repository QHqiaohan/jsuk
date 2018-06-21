package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.ModularPortal;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/14 17:26
 */
public class ModularPortalVo extends ModularPortal {

    private List<ModularPortalVo> modularPortalVoList;

    public List<ModularPortalVo> getModularPortalVoList() {
        return modularPortalVoList;
    }

    public void setModularPortalVoList(List<ModularPortalVo> modularPortalVoList) {
        this.modularPortalVoList = modularPortalVoList;
    }
}
