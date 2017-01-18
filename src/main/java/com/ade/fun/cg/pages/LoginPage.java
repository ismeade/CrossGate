package com.ade.fun.cg.pages;

import com.ade.fun.cg.dao.SysUserDao;
import com.ade.fun.cg.persistent.SysUser;
import org.apache.click.Page;
import org.apache.click.control.FieldSet;
import org.apache.click.control.Form;
import org.apache.click.control.PasswordField;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.element.CssImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends Page {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Form form = new Form("form");
	
	/** Constructor */
	public LoginPage(){
		addControl(form);
		FieldSet fieldSet = new FieldSet("Login");
		form.add(fieldSet);
		TextField userName = new TextField("userName", "账号", true);
		userName.setMaxLength(20);
		userName.setFocus(true);
		fieldSet.add(userName);
		
		PasswordField passWord = new PasswordField("passWord", "密码", true);
		passWord.setMaxLength(20);
		fieldSet.add(passWord);
		
		fieldSet.add(new Submit("ok"    , "登录", this, "onSubmit"));
		fieldSet.add(new Submit("cancel", "还原", this, "onCancel"));
	}

    @Override
    public void onInit() {
        super.onInit();
        getHeadElements().add(new CssImport("/assets/style.css"));
    }

	public boolean onSubmit() {
		if (form.isValid()) {
			String userName = form.getFieldValue("userName");
			String passWord = form.getFieldValue("passWord");
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

}
