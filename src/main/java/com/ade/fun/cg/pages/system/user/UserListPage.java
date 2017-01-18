package com.ade.fun.cg.pages.system.user;

import com.ade.fun.cg.dao.SysUserDao;
import com.ade.fun.cg.pages.BorderPage;
import com.ade.fun.cg.persistent.SysUser;
import com.ade.fun.cg.utils.IntegerUtils;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.*;
import org.apache.click.dataprovider.DataProvider;
import org.apache.click.extras.control.LinkDecorator;
import org.apache.click.extras.control.TableInlinePaginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * Created by liyang on 2017/1/18.
 */

public class UserListPage extends BorderPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Table table = new Table("table");

    private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");

    public UserListPage() {

        addControl(table);
        addControl(deleteLink);

        PageLink pageLink = new PageLink("newObject", "新建", UserCreatePage.class);
        pageLink.setImageSrc("/images/table.png");
        addControl(pageLink);

        table.setClass(Table.CLASS_BLUE2);
        table.setPageSize(10);
        table.setShowBanner(true);
        table.setSortable(true);
        table.setPaginator(new TableInlinePaginator(table));
        table.setPaginatorAttachment(Table.PAGINATOR_INLINE);

        table.addColumn(new Column("pk"                         , "ID"));
        table.addColumn(new Column(SysUser.USER_ACCOUNT_PROPERTY, "账号"));
        table.addColumn(new Column(SysUser.USER_NAME_PROPERTY   , "姓名"));
//        table.addColumn(new Column(SysUser.ROLE_PROPERTY        , "角色"));

        deleteLink.setImageSrc("/images/table-delete.png");
        deleteLink.setTitle("Delete user record");
        deleteLink.setAttribute("onclick", "return window.confirm('确定删除该数据?');");

        Column column = new Column("Action", "操作");
        column.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[] {deleteLink};
        column.setDecorator(new LinkDecorator(table, links, "pk"));
        column.setSortable(false);
        table.addColumn(column);

        table.setDataProvider(new DataProvider<SysUser>() {
            public List<SysUser> getData() {
                return SysUserDao.getInstance().getSysUserAll(getSysUser().getObjectContext());
            }
        });

        table.getControlLink().setActionListener(new ActionListener() {
            public boolean onAction(Control source) {
                table.saveState(getContext());
                return true;
            }
        });

        // Restore the table sort and paging state from the session between requests
        table.restoreState(getContext());
    }

    public boolean onDeleteClick() {
        try {
            Integer pk = deleteLink.getValueInteger();
            if (IntegerUtils.isPositive(pk)) {
                ObjectContext context = getSysUser().getObjectContext();
                SysUser user = Cayenne.objectForPK(context, SysUser.class, pk);
                if (null != user) {
                    context.deleteObject(user);
                    context.commitChanges();
                    setRedirect(this.getClass());
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

}
