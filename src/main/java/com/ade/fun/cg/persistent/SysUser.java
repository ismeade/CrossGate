package com.ade.fun.cg.persistent;

import com.ade.fun.cg.persistent.auto._SysUser;
import com.ade.fun.cg.utils.StringUtils;
import org.apache.cayenne.Cayenne;

public class SysUser extends _SysUser {

    public Integer getPk() {
        return Cayenne.intPKForObject(this);
    }

    public boolean check() {
        return StringUtils.isPositive(getUserName(), getUserAccount(), getUserPassword());
    }

}
