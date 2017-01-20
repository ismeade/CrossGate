package com.ade.fun.cg.pages.note.account;

import com.ade.fun.cg.dao.AccountDao;
import com.ade.fun.cg.pages.BorderPage;
import com.ade.fun.cg.persistent.Account;
import org.apache.click.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by liyang on 2017/1/18.
 */
public class AccountEditPage extends BorderPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Form form = new Form("form");

    public AccountEditPage(){
        try {
            addControl(form);
            String id = getContext().getRequest().getParameter("pk");
            HiddenField hiddenField = new HiddenField("pk", id);
            form.add(hiddenField);
            Account account = AccountDao.getInstance().getAccountByPk(getSysUser().getObjectContext(), Integer.parseInt(id));

            FieldSet fieldSet = new FieldSet("编辑");
            form.add(fieldSet);

            TextField codeField = new TextField(Account.ACCOUNT_CODE_PROPERTY, "账号", true);
            codeField.setMaxLength(20);
            codeField.setReadonly(true);
            codeField.addStyleClass("w250");
            codeField.setValue(account.getAccountCode());
            fieldSet.add(codeField);

            TextArea descArea = new TextArea(Account.ACCOUNT_DESC_PROPERTY, "备注");
            descArea.setMaxLength(2000);
            descArea.addStyleClass("w250");
            descArea.addStyleClass("h200");
            descArea.setValue(account.getAccountDesc());
            fieldSet.add(descArea);

            Submit submitOk = new Submit("ok", "保存", this, "onOkClicked");
            fieldSet.add(submitOk);
            Submit submitCancel = new Submit("cancel", "返回", this, "onCancelClicked");
            fieldSet.add(submitCancel);

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Handle the OK button click event.
     *
     * @return true
     */
    public boolean onOkClicked() {
        try {
            if (form.isValid()) {
                String id          = form.getFieldValue("pk");
                String accountDesc = form.getFieldValue(Account.ACCOUNT_DESC_PROPERTY);
                AccountDao.getInstance().editAccount(getSysUser().getObjectContext(), Integer.parseInt(id), accountDesc);
                setRedirect(AccountListPage.class);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

    /**
     * Handle the Cancel button click event.
     *
     * @return false
     */
    public boolean onCancelClicked() {
        setRedirect(AccountListPage.class);
        return false;
    }

}
