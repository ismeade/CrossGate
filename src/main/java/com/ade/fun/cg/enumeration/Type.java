package com.ade.fun.cg.enumeration;

import org.apache.cayenne.ExtendedEnumeration;

/**
 *
 * Created by liyang on 2017/1/13.
 */
public enum Type implements ExtendedEnumeration {

    TYPE_FIGTH(1, "战斗"),
    TYPE_WAREHOUSE(4, "仓库"),
    TYPE_MAKER(2, "生产"),
    TYPE_COLLECT(3, "采集");

    private Integer code;
    private String  name;

    private Type(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Object getDatabaseValue() {
        return code;
    }

    public String getName() {
        return name;
    }
}
