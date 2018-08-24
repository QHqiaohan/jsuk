package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;

//0待审核  1通过  2拒绝
@Getter
@Envm(name = "骑手收入类型")
public enum DistributionApplyType implements BaseEnum {

    DISTP_COMPLETE(1, "完成配送"),

    CASH(2, "提现");

    private final Integer key;
    private final String value;

    DistributionApplyType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
