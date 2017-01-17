package com.ade.fun.service.impl;

import com.ade.fun.model.context.ObjectContextFactory;
import com.ade.fun.model.enumeration.Position;
import com.ade.fun.model.enumeration.Rank;
import com.ade.fun.model.enumeration.Type;
import com.ade.fun.model.persistent.*;
import com.ade.fun.model.persistent.Character;
import com.ade.fun.model.utils.IntegerUtils;
import com.ade.fun.service.AccountService;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
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

    public int createAccount(ObjectContext context, String accountCode, String accountDesc) {
        logger.debug("context:" + context + ", accountCode:" + accountCode + ", accountDesc:" + accountDesc);
        if (null == context) return -1;
        try {
            if (isExist(context, accountCode)) {
                return 2;
            } else {
                Account account = context.newObject(Account.class);
                account.setAccountCode(accountCode);
                account.setAccountDesc(accountDesc);
                context.commitChanges();
                return 1;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return -2;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ObjectContext context = ObjectContextFactory.getInstance().getObjectContext();

        SelectQuery selectQuery = new SelectQuery(Account.class);
        List list = context.performQuery(selectQuery);
        if (null != list) {
            context.deleteObjects(list);
        }

        for (int i = 1; i <= 20; i++) {
            Account account = context.newObject(Account.class);
            account.setAccountCode("ismeade" + i);
            account.setAccountType(Type.TYPE_FIGTH);
            account.setAccountDesc("测试数据");
            Character character1 = context.newObject(Character.class);
            character1.setAccount(account);
            character1.setCharacterLevel(120);
            character1.setCharacterPosition(Position.LEFT);
            character1.setCharacterRank(Rank.RANK_IIII);
            character1.setCharacterName("阿德");
            Character character2 = context.newObject(Character.class);
            character2.setAccount(account);
            character2.setCharacterLevel(25);
            character2.setCharacterPosition(Position.RIGHT);
            character2.setCharacterRank(Rank.RANK_II);
            character2.setCharacterName("噗叽");
        }
        context.commitChanges();
    }

}
