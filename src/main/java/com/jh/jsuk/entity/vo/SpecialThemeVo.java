package com.jh.jsuk.entity.vo;

import com.jh.jsuk.entity.SpecialTheme;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SpecialThemeVo extends SpecialTheme {

    private ShopGoodsVo goodsInfo;

}
