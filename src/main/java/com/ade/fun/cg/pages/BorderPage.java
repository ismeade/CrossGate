package com.ade.fun.cg.pages;

import com.ade.fun.cg.persistent.SysUser;
import com.ade.fun.cg.utils.StringUtils;
import org.apache.click.Page;
import org.apache.click.element.CssImport;
import org.apache.click.extras.control.Menu;
import org.apache.click.extras.control.MenuFactory;
import org.apache.click.extras.security.AccessController;
import org.apache.click.extras.security.RoleAccessController;
import org.apache.click.util.Bindable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BorderPage extends Page {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Menu rootMenu;
    
    @Bindable protected String title = "Page";

    public BorderPage() {
//        String className = getClass().getName();
//
//        String shortName = className.substring(className.lastIndexOf('.') + 1);
//        String title = ClickUtils.toLabel(shortName);
//        addModel("title", title);
//
//        String srcPath = className.replace('.', '/') + ".java";
//        addModel("srcPath", srcPath);
    }

    @Override
    public void onInit() {
        super.onInit();
        
        getHeadElements().add(new CssImport("/assets/style.css"));
        getHeadElements().add(new CssImport("/css/custom.css"));
//        getHeadElements().add(new CssImport("/css/buttons.css"));

        MenuFactory menuFactory = new MenuFactory();
        rootMenu = menuFactory.getRootMenu();

        Menu menu = createMenu(getSysUser().getUserName(), null, rootMenu.getAccessController());
        menu.add(createMenu("修改密码", "self/password/password-edit.htm", menu.getAccessController()));
        menu.add(createMenu("退出", "logout.htm", menu.getAccessController()));
        rootMenu.add(menu);

        addControl(rootMenu);
    }

    @Override
    public void onDestroy() {
        if (rootMenu != null) {
            removeControl(rootMenu);
        }
    }

    @Override
    public String getTemplate() {
        return "/border-template.htm";
    }
    
	public boolean onSecurityCheck() {
        logger.info("RequestURL:" + getContext().getRequest().getRequestURL());
        logger.info("Session:" + getContext().hasSession());
        logger.info("SysUser:" + getSysUser());
        if (getContext().hasSession() && null != getSysUser() && getSysUser().check()) {
			return true;
		} else {
			setRedirect(LoginPage.class);
			return false;
		}
	}

    private Menu createMenu(String label, String path, AccessController accessController) {
        if (StringUtils.isPositive(label)) {
            Menu menu = new Menu();
            menu.setLabel(label);
            if (StringUtils.isPositive(path)) {
                menu.setPath(path);
            }
            menu.setAccessController(null == accessController ? new RoleAccessController() : accessController);
            return menu;
        }
        return null;
    }

    protected SysUser getSysUser() {
        try {
            return (SysUser) getContext().getSessionAttribute("curr_user");
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
	
	public void setTitle(String title) {
		this.title = title;
	}

}
