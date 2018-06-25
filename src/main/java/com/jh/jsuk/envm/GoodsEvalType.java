package com.jh.jsuk.envm;

/**
 * 评价类型对应评价星级关系
 */
public enum GoodsEvalType {

    /**
     * 好评
     */
    GOOD("gd", "好评", 5),

    /**
     * 中评
     */
    MEDIUM("mdm", "中评", 2, 3, 4),

    /**
     * 差评
     */
    NEGATIVE("ngt", "差评", 1);

    private final Integer[] stars;

    private final String value;

    private final String key;

    GoodsEvalType(String key, String value, Integer... stars) {
        this.key = key;
        this.stars = stars;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public Integer[] getStars() {
        return stars;
    }
}
