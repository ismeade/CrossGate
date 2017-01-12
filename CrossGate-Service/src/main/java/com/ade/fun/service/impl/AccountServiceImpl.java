package com.ade.fun.service.impl;

import com.ade.fun.model.persistent.Account;
import com.ade.fun.model.utils.IntegerUtils;
import com.ade.fun.service.AccountService;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List getAccount(ObjectContext context, Integer page) {
        logger.debug("context:" + context + ", page:" + page);
        if (null == context) return null;
        try {
            SelectQuery selectQuery = new SelectQuery(Account.class);
            if (IntegerUtils.isPositive(page))
                selectQuery.setPageSize(page);
            selectQuery.addPrefetch(Account.CHARACTER_PROPERTY);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

}
