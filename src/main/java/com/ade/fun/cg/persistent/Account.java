package com.ade.fun.cg.persistent;

import com.ade.fun.cg.persistent.auto._Account;
import org.apache.cayenne.Cayenne;

public class Account extends _Account {

    public Integer getPk() {
        return Cayenne.intPKForObject(this);
    }

//    public String getAccountDesc() {
//        String accountDesc = super.getAccountDesc();
//        return StringUtils.isPositive(accountDesc) ? accountDesc.replaceAll("\n", "\r\n") : "";
//    }

}
