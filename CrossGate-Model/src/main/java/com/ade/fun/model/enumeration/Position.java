package com.ade.fun.model.enumeration;

import org.apache.cayenne.ExtendedEnumeration;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public enum Position implements ExtendedEnumeration {

    LEFT(1),
    RIGHT(2);

    private Integer code;

    private Position(Integer code) {
        this.code = code;
    }

    @Override
    public Object getDatabaseValue() {
        return code;
    }
}
