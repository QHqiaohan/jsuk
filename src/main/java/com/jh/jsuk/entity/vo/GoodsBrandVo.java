package com.jh.jsuk.entity.vo;


import com.jh.jsuk.entity.GoodsBrand;
import com.jh.jsuk.entity.GoodsCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Created with IntelliJ IDEA.
 *
 * @author: XuChuRuo
 * @date: 2018/6/13 12:45
 */
@Getter
@Setter
@ToString
public class GoodsBrandVo extends GoodsBrand {

    private GoodsCategory categoryInfo;

}
