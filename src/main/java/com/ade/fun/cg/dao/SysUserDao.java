package com.ade.fun.cg.dao;

import com.ade.fun.cg.persistent.SysRole;
import com.ade.fun.cg.persistent.SysRolePage;
import com.ade.fun.cg.persistent.SysUser;
import com.ade.fun.cg.utils.IntegerUtils;
import com.ade.fun.cg.utils.Md5Utils;
import com.ade.fun.cg.utils.StringUtils;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * Created by liyang on 2017/1/17.
 */
public class SysUserDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String MD5_KEY = "jiayuangongzuoshi";

    private final static SysUserDao instance = new SysUserDao();

    private SysUserDao() {}

    public static SysUserDao getInstance() {
        return instance;
    }

    public SysUser getSysUser(String userAccount, String passWord) {
        if (!StringUtils.isPositive(userAccount, passWord)) return null;
        ObjectContext context = ObjectContextFactory.getInstance().getObjectContext();
        passWord = Md5Utils.string2Md5(passWord + MD5_KEY);
        try {
            Expression expression = ExpressionFactory.matchExp(SysUser.USER_ACCOUNT_PROPERTY, userAccount)
                    .andExp(ExpressionFactory.matchExp(SysUser.USER_PASSWORD_PROPERTY, passWord));
            SelectQuery selectQuery = new SelectQuery(SysUser.class, expression);
            selectQuery.addPrefetch(SysUser.ROLE_PROPERTY);
            selectQuery.addPrefetch(SysUser.ROLE_PROPERTY + "." + SysRole.ROLE_PAGE_PROPERTY);
            selectQuery.addPrefetch(SysUser.ROLE_PROPERTY + "." + SysRole.ROLE_PAGE_PROPERTY + "." + SysRolePage.PAGE_PROPERTY);
            return (SysUser) Cayenne.objectForQuery(context, selectQuery);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public Boolean canCreate(ObjectContext context, String userAccount) {
        if (null == context || !StringUtils.isPositive(userAccount)) return false;
        try {
            Expression expression = ExpressionFactory.matchExp(SysUser.USER_ACCOUNT_PROPERTY, userAccount);
            SelectQuery selectQuery = new SelectQuery(SysUser.class, expression);
            selectQuery.setPageSize(10);
            List list = context.performQuery(selectQuery);
            return null == list || list.size() == 0;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public Boolean createSysUser(ObjectContext context, String userName, String userAccount, String passWord, Integer level) {
        if (null == context || !StringUtils.isPositive(userName, userAccount, passWord) || !IntegerUtils.isPositive(level)) return false;
        try {
            SysUser user = context.newObject(SysUser.class);
            user.setLockMark(0);
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setUserPassword(Md5Utils.string2Md5(passWord + MD5_KEY));
            context.commitChanges();
            logger.info("create SysUser: " + user);
            return true;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public List<SysUser> getSysUserAll(ObjectContext context) {
        if (null == context) return null;
        try {
            SelectQuery selectQuery = new SelectQuery(SysUser.class);
            //noinspection unchecked
            return context.performQuery(selectQuery);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public Boolean deleteUser(ObjectContext context, Integer pk) {
        if (null == context) return false;
        SysUser user = Cayenne.objectForPK(context, SysUser.class, pk);
        if (null != user) {
            context.deleteObject(user);
            context.commitChanges();
            return true;
        }
        return false;
    }

}
