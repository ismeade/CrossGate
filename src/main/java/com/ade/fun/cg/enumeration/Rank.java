package com.ade.fun.cg.enumeration;

import org.apache.cayenne.ExtendedEnumeration;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public enum Rank implements ExtendedEnumeration {

    RANK_I(1, "见习"),
    RANK_II(2, "正职"),
    RANK_III(3, "二转"),
    RANK_IIII(4, "三转");

    private Integer code;
    private String  name;

    private Rank(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Object getDatabaseValue() {
        return code;
    }
}
