package com.dp.common.utils.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.dp.common.Constant;
import com.dp.demo.sys.pojo.User;

public class UserUtil {


    private UserUtil() {

    }

    /**
     * @Desc 获取当前的登陆用户
     */
    public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null || !subject.isAuthenticated())
            return null;
        User user = (User) subject.getSession().getAttribute(Constant.SESSION_CURRENT_USER);
        return user;
    }

}
