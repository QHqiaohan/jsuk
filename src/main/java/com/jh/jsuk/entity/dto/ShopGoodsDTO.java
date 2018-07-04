package com.jh.jsuk.entity.dto;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.ShopGoodsSize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class ShopGoodsDTO extends ShopGoods implements Serializable {

    List<ShopGoodsSize> sizes;

}
