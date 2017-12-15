package com.dp.common.utils.util;

import java.util.UUID;

/**
 * Created by Administrator on 2016/12/28.
 */
public class UuidUtil {

    private UuidUtil() {
    }

    public static String generator() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(UuidUtil.generator());

    }

}
