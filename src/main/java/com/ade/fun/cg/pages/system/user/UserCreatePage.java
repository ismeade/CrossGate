package com.ade.fun.cg.pages.system.user;

import com.ade.fun.cg.dao.SysUserDao;
import com.ade.fun.cg.pages.BorderPage;
import com.ade.fun.cg.persistent.SysRole;
import com.ade.fun.cg.persistent.SysUser;
import org.apache.click.control.*;
import org.apache.click.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by liyang on 2017/1/18.
 */
public class UserCreatePage extends BorderPage {

    private Form form = new Form("form");

    public UserCreatePage() {

        addControl(form);
        FieldSet fieldSet = new FieldSet("新增用户");
        form.add(fieldSet);

        TextField nameField = new TextField(SysUser.USER_NAME_PROPERTY, "姓名", true);
        nameField.setMaxLength(20);
        nameField.setFocus(true);
        fieldSet.add(nameField);

        TextField accountField = new TextField(SysUser.USER_ACCOUNT_PROPERTY, "账号", true);
        nameField.setMaxLength(20);
        nameField.setFocus(true);
        fieldSet.add(accountField);

        PasswordField passField = new PasswordField(SysUser.USER_PASSWORD_PROPERTY, "密码", true);
        passField.setMaxLength(20);
        fieldSet.add(passField);

        PasswordField cPassField = new PasswordField("cPassWord", "重复密码", true);
        cPassField.setMaxLength(20);
        fieldSet.add(cPassField);

//        Select investmentSelect = new Select(SysUser.ROLE_PROPERTY, "角色");
//        fieldSet.add(investmentSelect);
//        investmentSelect.setDefaultOption(Option.EMPTY_OPTION);
//        investmentSelect.setDataProvider(new DataProvider<Option>() {

//            public List<Option> getData() {
//                List<Option> options = new ArrayList<>();
//                List<SysRole> roles = null;
//                for (SysRole role : roles) {
//                    options.add(new Option(role.getPk(), role.getRoleName()));
//                }
//                return options;
//            }
//        });

        form.add(new Submit("ok", "确定", this, "onOkClicked"));
        form.add(new Submit("cancel", this, "onCancelClicked"));

    }

    /**
     * Handle the OK button click event.
     *
     * @return true
     */
    public boolean onOkClicked() {
        if (form.isValid()) {
            String userName    = form.getFieldValue(SysUser.USER_NAME_PROPERTY);
            String userAccount = form.getFieldValue(SysUser.USER_ACCOUNT_PROPERTY);
            String passWord    = form.getFieldValue(SysUser.USER_PASSWORD_PROPERTY);
            String cPassWord   = form.getFieldValue("cPassWord");
//            String roleId      = form.getFieldValue(SysUser.ROLE_PROPERTY);
            String msg = null;
            if (!passWord.equals(cPassWord)) {
                msg = "重复密码不一致ͬ";
                form.getField(SysUser.USER_PASSWORD_PROPERTY).setValue("");
                form.getField("cPassWord").setValue("");
                form.getField(SysUser.USER_PASSWORD_PROPERTY).setFocus(true);
                addModel("msg", msg);
            } else if (SysUserDao.getInstance().canCreate(getSysUser().getObjectContext(), userAccount)) {
                SysUserDao.getInstance().createSysUser(getSysUser().getObjectContext(), userName, userAccount, passWord, 9);
                setRedirect(UserListPage.class);
            } else {
                msg = "账号已存在ͬ";
                form.getField(SysUser.USER_ACCOUNT_PROPERTY).setFocus(true);
                addModel("msg", msg);
            }
        }
        return true;
    }

    /**
     * Handle the Cancel button click event.
     *
     * @return false
     */
    public boolean onCancelClicked() {
        setRedirect(UserListPage.class);
        return false;
    }

}
