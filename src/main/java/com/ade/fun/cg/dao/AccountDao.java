package com.ade.fun.cg.dao;

import com.ade.fun.cg.persistent.Account;
import com.ade.fun.cg.utils.IntegerUtils;
import com.ade.fun.cg.utils.StringUtils;
import org.apache.cayenne.Cayenne;
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

    private final static AccountDao instance = new AccountDao();

    private AccountDao() {}

    public static AccountDao getInstance() {
        return instance;
    }

    public Account getAccountByPk(ObjectContext context, Integer pk) {
        logger.debug("context:" + context + ", pk:" + pk);
        if (!IntegerUtils.isPositive(pk)) return null;
        try {
            return Cayenne.objectForPK(context, Account.class, pk);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public List<Account> getAccount(ObjectContext context, String search, Integer page) {
        logger.debug("context:" + context + ", page:" + page);
        if (null == context) return null;
        try {
            if (null == search) search = "";
            Expression expression = ExpressionFactory.likeExp(Account.ACCOUNT_DESC_PROPERTY, "%" + search + "%");
            SelectQuery selectQuery = new SelectQuery(Account.class, expression);
            if (IntegerUtils.isPositive(page)) selectQuery.setPageSize(page);
            selectQuery.addPrefetch(Account.CHARACTER_PROPERTY);
            selectQuery.addOrdering(Account.ACCOUNT_CODE_PROPERTY, SortOrder.ASCENDING);
            //noinspection unchecked
            return context.performQuery(selectQuery);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean canCreate(ObjectContext context, String accountCode) {
        if (null == context || !StringUtils.isPositive(accountCode)) return false;
        try {
            Expression expression = ExpressionFactory.matchExp(Account.ACCOUNT_CODE_PROPERTY, accountCode);
            SelectQuery selectQuery = new SelectQuery(Account.class, expression);
            selectQuery.setPageSize(10);
            List list = context.performQuery(selectQuery);
            return null == list || list.size() == 0;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean createAccount(ObjectContext context, String accountCode, String accountDesc) {
        if (null == context || !StringUtils.isPositive(accountCode)) return false;
        try {
            Account account = context.newObject(Account.class);
            account.setAccountCode(accountCode);
            account.setAccountDesc(accountDesc);
            context.commitChanges();
            logger.info("create Account: " + account);
            return true;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    public boolean editAccount(ObjectContext context, Integer pk, String accountDesc) {
        if (null == context) return false;
        try {
            Account account = getAccountByPk(context, pk);
            if (null != account) {
                account.setAccountDesc(accountDesc);
                context.commitChanges();
                logger.info("create Account: " + account);
                return true;
            } else {
                logger.info("Account:" + pk + " not exist");
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    public boolean deleteUser(ObjectContext context, Integer pk) {
        if (null == context || !IntegerUtils.isPositive(pk)) return false;
        Account account = Cayenne.objectForPK(context,Account.class, pk);
        if (null != account) {
            context.deleteObject(account);
            context.commitChanges();
            return true;
        }
        return false;
    }
}
