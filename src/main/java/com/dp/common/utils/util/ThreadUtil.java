package com.dp.common.utils.util;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    public static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(5);

    private ThreadUtil() {

    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

}
