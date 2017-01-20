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
public class AccountCreatePage extends BorderPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Form form = new Form("form");

    public AccountCreatePage() {
        addControl(form);
        FieldSet fieldSet = new FieldSet("新增账号");
        form.add(fieldSet);

        TextField codeField = new TextField(Account.ACCOUNT_CODE_PROPERTY, "账号", true);
        codeField.setMaxLength(20);
        codeField.setFocus(true);
        codeField.addStyleClass("w250");
        fieldSet.add(codeField);

        TextArea descArea = new TextArea(Account.ACCOUNT_DESC_PROPERTY, "备注");
        descArea.setMaxLength(2000);
        descArea.addStyleClass("w250");
        descArea.addStyleClass("h200");
        fieldSet.add(descArea);

        Submit submitOk = new Submit("ok", "保存", this, "onOkClicked");
        fieldSet.add(submitOk);
        Submit submitCancel = new Submit("cancel", "返回", this, "onCancelClicked");
        fieldSet.add(submitCancel);
    }

    /**
     * Handle the OK button click event.
     *
     * @return true
     */
    public boolean onOkClicked() {
        try {
            if (form.isValid()) {
                String accountCode = form.getFieldValue(Account.ACCOUNT_CODE_PROPERTY);
                String accountDesc = form.getFieldValue(Account.ACCOUNT_DESC_PROPERTY);
                if (AccountDao.getInstance().canCreate(getSysUser().getObjectContext(), accountCode)) {
                    if (AccountDao.getInstance().createAccount(getSysUser().getObjectContext(), accountCode, accountDesc)) {
                        setRedirect(AccountListPage.class);
                    } else {
                        addModel("msg", "失败");
                    }
                } else {
                    form.getField(Account.ACCOUNT_CODE_PROPERTY).setFocus(true);
                    addModel("msg", "账号已存在");
                }
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
