package com.ade.fun.web.utils;

/**
 *
 * Created by liyang on 2017/1/13.
 */
public enum  Css {

    GRID_TABLE("gridtable", "<style type=\"text/css\">table.gridtable{font-family:verdana,arial,sans-serif;font-size:11px;color:#333;border-width:1px;border-color:#666;border-collapse:collapse}table.gridtable th{border-width:1px;padding:8px;border-style:solid;border-color:#666;background-color:#dedede}table.gridtable td{border-width:1px;padding:8px;border-style:solid;border-color:#666;background-color:#fff}</style>");

    private String name;
    private String value;

    private Css(String name, String value) {
        this.name  = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
