package com.ade.fun.cg.persistent;

import com.ade.fun.cg.persistent.auto._SysRole;
import org.apache.cayenne.Cayenne;

public class SysRole extends _SysRole {

    public Integer getPk() {
        return Cayenne.intPKForObject(this);
    }

}
