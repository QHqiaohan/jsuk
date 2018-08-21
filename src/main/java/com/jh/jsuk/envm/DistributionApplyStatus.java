package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;

//0待审核  1通过  2拒绝
@Getter
@Envm(name = "骑手提现状态")
public enum DistributionApplyStatus implements BaseEnum {

    APPLYING(1, "申请中"),

    PASSED(1, "通过"),

    REFUSED(2, "拒绝");

    private final Integer key;
    private final String value;

    DistributionApplyStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
