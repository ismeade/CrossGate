package com.ade.fun.cg.persistent;

import com.ade.fun.cg.persistent.auto._SysUser;
import com.ade.fun.cg.utils.StringUtils;
import org.apache.cayenne.Cayenne;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SysUser extends _SysUser {

    public Integer getPk() {
        return Cayenne.intPKForObject(this);
    }

    public boolean check() {
        return StringUtils.isPositive(getUserName(), getUserAccount(), getUserPassword());
    }

    public String getLastLoginTime() {
        Date date = getLastLogin();
        return null == date ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

}
