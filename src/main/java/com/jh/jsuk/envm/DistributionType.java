package com.jh.jsuk.envm;

import com.github.tj123.common.enum2md.Envm;
import lombok.Getter;

//0：快递 1： 同城配送 2：到店自提
@Getter
@Envm(name = "配送类型", description = "订单的配送类型")
public enum DistributionType {

    EXPRESS(0, "快递"),

    CITY_DISTRIBUTION(1, "同城配送"),

    SELF_CLAIM(2, "到店自提");


    private final Integer key;

    private final String value;

    DistributionType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
