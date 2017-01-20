package com.ade.fun.cg.pages.note.account;

import com.ade.fun.cg.dao.AccountDao;
import com.ade.fun.cg.pages.BorderPage;
import com.ade.fun.cg.persistent.Account;
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
public class AccountListPage extends BorderPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Table table = new Table("table");

    private ActionLink deleteLink = new ActionLink("Delete", this, "onDeleteClick");

    private TextField searchField = new TextField("searchField", "查询");

    private Form form = new Form("form");

    public AccountListPage() {
        form.setButtonAlign(Form.ALIGN_CENTER);

        addControl(form);
        addControl(table);
        PageLink editLink = new PageLink("Edit", AccountEditPage.class);
        addControl(editLink);
        addControl(deleteLink);

        searchField.setMaxLength(20);
        searchField.setFocus(true);


        Submit submitOk = new Submit("search", "查询", this, "onSearchClicked");
        Submit submitCancel = new Submit("cancel", "重置", this, "onCancelClicked");

        form.add(searchField);
        form.add(submitOk);
        form.add(submitCancel);

        PageLink pageLink = new PageLink("newObject", "新建", AccountCreatePage.class);
        pageLink.setImageSrc("/images/table.png");
        addControl(pageLink);

        table.setClass(Table.CLASS_BLUE2);
        table.setPageSize(30);
        table.setShowBanner(true);
        table.setSortable(true);
        table.setPaginator(new TableInlinePaginator(table));
        table.setPaginatorAttachment(Table.PAGINATOR_INLINE);
//        table.addColumn(new Column("pk", "ID"));

        Column columnCode = new Column(Account.ACCOUNT_CODE_PROPERTY, "账号");
        columnCode.setDataClass("w100");
        table.addColumn(columnCode);

        Column columnDesc = new Column(Account.ACCOUNT_DESC_PROPERTY, "说明");
        columnDesc.setDataClass("w300");
        table.addColumn(columnDesc);

        editLink.setImageSrc("/images/table-edit.png");
        editLink.setTitle("Edit");

        deleteLink.setImageSrc("/images/table-delete.png");
        deleteLink.setTitle("Delete");
        deleteLink.setAttribute("onclick", "return window.confirm('确定删除该数据?');");

        Column columnAction = new Column("Action", "操作");
        columnAction.setDataClass("w50");
        columnAction.setTextAlign("center");
        AbstractLink[] links = new AbstractLink[] {editLink, deleteLink};
        columnAction.setDecorator(new LinkDecorator(table, links, "pk"));
        columnAction.setSortable(false);

        table.addColumn(columnAction);

        table.setDataProvider(new DataProvider<Account>() {
            public List<Account> getData() {
                return AccountDao.getInstance().getAccount(getSysUser().getObjectContext(), searchField.getValue(), 30);
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

    public boolean onSearchClicked() {
        form.saveState(getContext());
        return true;
    }

    public boolean onCancelClicked() {
        form.clearErrors();
        form.clearValues();
        setRedirect(this.getClass());
        return true;
    }

    public boolean onDeleteClick() {
        try {
            Integer pk = deleteLink.getValueInteger();
            if (IntegerUtils.isPositive(pk)) {
                ObjectContext context = getSysUser().getObjectContext();
                Account account = Cayenne.objectForPK(context, Account.class, pk);
                if (null != account) {
                    context.deleteObject(account);
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
