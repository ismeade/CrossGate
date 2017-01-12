package com.ade.fun.service.impl;

import com.ade.fun.model.utils.StringUtils;
import com.ade.fun.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Created by liyang on 2017/1/12.
 */
public class LoginServiceImpl implements LoginService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean login(String password) {
        logger.debug("password:" + password);
        return StringUtils.isPositive(password) && "123123".equals(password);
    }

}
