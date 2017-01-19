package com.ade.fun.cg.pages;

import com.ade.fun.cg.dao.SysUserDao;
import com.ade.fun.cg.persistent.SysUser;
import org.apache.click.Page;
import org.apache.click.control.*;
import org.apache.click.element.CssImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends Page {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Form form = new Form("form");
	
	/** Constructor */
	public LoginPage(){

        form.setErrorsPosition(Form.POSITION_BOTTOM);

		addControl(form);
		FieldSet fieldSet = new FieldSet("Login");
		form.add(fieldSet);
		TextField userAccount = new TextField("userAccount", "账号", true);
		userAccount.setMaxLength(20);
		userAccount.setFocus(true);
		fieldSet.add(userAccount);
		
		PasswordField passwordField = new PasswordField("password", "密码", true);
		passwordField.setMaxLength(20);
        passwordField.addStyleClass("wh100");
		fieldSet.add(passwordField);



        Submit submitOk = new Submit("ok", "登录", this, "onSubmit");
		fieldSet.add(submitOk);

        Reset reset = new Reset("还原");
        fieldSet.add(reset);
	}

    @Override
    public void onInit() {
        super.onInit();
        getHeadElements().add(new CssImport("/assets/style.css"));
    }

	public boolean onSubmit() {
		if (form.isValid()) {
			String userName = form.getFieldValue("userAccount");
			String passWord = form.getFieldValue("password");
            logger.info("userName:" + userName + " passWord:" + passWord);
            SysUser sysUser = SysUserDao.getInstance().getSysUser(userName, passWord);
            if (null == sysUser) {
                addModel("message", "账号或密码错误");
            } else if (null == sysUser.getLockMark() || 0 < sysUser.getLockMark()) {
                addModel("message", "账号被锁定");
            } else {
                getContext().setSessionAttribute("curr_user", sysUser);
                setRedirect(IndexPage.class);
            }
//			form.clearValues();
		}
		return true;
	}
	
	public boolean onCancel() {
		setRedirect(LoginPage.class);
		return false;
	}

//    private SysUser getSysUser() {
//        try {
//            return (SysUser) getContext().getSessionAttribute("curr_user");
//        } catch (Exception e) {
//            logger.error(e.getLocalizedMessage(), e);
//        }
//        return null;
//    }
}
