package com.ade.fun.cg.pages.self.password;

import com.ade.fun.cg.dao.SysUserDao;
import com.ade.fun.cg.pages.BorderPage;
import com.ade.fun.cg.pages.LogoutPage;
import com.ade.fun.cg.persistent.SysUser;
import org.apache.click.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by liyang on 2017/1/19.
 */
public class PasswordEditPage extends BorderPage {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Form form = new Form("form");

    public PasswordEditPage() {
        addControl(form);
        FieldSet fieldSet = new FieldSet("修改密码");
        form.add(fieldSet);

        PasswordField passwordField = new PasswordField(SysUser.USER_PASSWORD_PROPERTY, "密码", true);
        passwordField.setMaxLength(20);
        passwordField.setFocus(true);
        fieldSet.add(passwordField);

        PasswordField cPasswordField = new PasswordField("cPassWord", "重复密码", true);
        cPasswordField.setMaxLength(20);
        fieldSet.add(cPasswordField);

        Submit submitOk = new Submit("ok", "确定", this, "onOkClicked");
        fieldSet.add(submitOk);

        Reset reset = new Reset("还原");
        fieldSet.add(reset);
    }

    /**
     * Handle the OK button click event.
     *
     * @return true
     */
    public boolean onOkClicked() {
        try {
            if (form.isValid()) {
                String passWord  = form.getFieldValue(SysUser.USER_PASSWORD_PROPERTY);
                String cPassWord = form.getFieldValue("cPassWord");
                if (!passWord.equals(cPassWord)) {
                    form.getField(SysUser.USER_PASSWORD_PROPERTY).setValue("");
                    form.getField("cPassWord").setValue("");
                    form.getField(SysUser.USER_PASSWORD_PROPERTY).setFocus(true);
                    addModel("msg", "重复密码不一致");
                } else {
                    if (SysUserDao.getInstance().editPassword(getSysUser(), passWord)) {
                        setRedirect(LogoutPage.class);
                    } else {
                        addModel("msg", "失败");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return true;
    }

}
