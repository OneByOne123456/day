package com.dp.common.utils.util;

public class UserAgentUtil {

    private UserAgentUtil() {

    }

    /// 根据 Agent 判断是否是智能手机
    public static boolean fromPhone(String userAgent) {
        boolean flag = false;
        String[] keywords = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };
        //排除 Windows 桌面系统
        if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
            //排除 苹果桌面系统
            if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
                for(String item : keywords) {
                    if (userAgent.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }


}
