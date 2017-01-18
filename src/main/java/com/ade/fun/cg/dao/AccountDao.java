package com.ade.fun.cg.dao;

import com.ade.fun.cg.persistent.Account;
import com.ade.fun.cg.utils.IntegerUtils;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class AccountDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public List getAccount(ObjectContext context, Integer page) {
        logger.debug("context:" + context + ", page:" + page);
        if (null == context) return null;
        try {
            SelectQuery selectQuery = new SelectQuery(Account.class);
            if (IntegerUtils.isPositive(page)) selectQuery.setPageSize(page);
            selectQuery.addPrefetch(Account.CHARACTER_PROPERTY);
            selectQuery.addOrdering(Account.ACCOUNT_TYPE_PROPERTY, SortOrder.ASCENDING);
            selectQuery.addOrdering(Account.ACCOUNT_CODE_PROPERTY, SortOrder.ASCENDING);
            return context.performQuery(selectQuery);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    private boolean isExist(ObjectContext context, String accountCode) {
        try {
            Expression expression = ExpressionFactory.matchExp(Account.ACCOUNT_CODE_PROPERTY, accountCode);
            SelectQuery selectQuery = new SelectQuery(Account.class, expression);
            selectQuery.setPageSize(10);
            List list = context.performQuery(selectQuery);
            return null != list && list.size() > 0;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

}
