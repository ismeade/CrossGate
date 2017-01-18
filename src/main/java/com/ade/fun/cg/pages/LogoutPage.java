package com.ade.fun.cg.pages;

import org.apache.click.Page;

/**
 *
 * Created by liyang on 2017/1/18.
 */
public class LogoutPage extends Page {

    @Override
    public void onInit() {
        getContext().getSession().invalidate();
        setRedirect(LoginPage.class);
    }

}
