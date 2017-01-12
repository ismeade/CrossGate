package com.ade.fun.service;

import org.apache.cayenne.ObjectContext;

import java.util.List;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public interface AccountService {

    List getAccount(ObjectContext context, Integer page);

}
