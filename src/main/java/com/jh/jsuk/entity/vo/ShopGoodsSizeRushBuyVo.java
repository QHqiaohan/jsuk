package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class ShopGoodsSizeRushBuyVo extends ShopGoodsSize {

    private List<RushBuyVo> rushBuyInfo = new ArrayList<>();

    public void addVo(RushBuyVo vo){
        if(vo == null)
            return;
        vo.checkStatus();
        rushBuyInfo.add(vo);
    }

}
