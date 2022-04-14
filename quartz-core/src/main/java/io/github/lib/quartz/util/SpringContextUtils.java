package io.github.lib.quartz.util;

import org.springframework.context.ApplicationContext;

public class SpringContextUtils {
    private static ApplicationContext applicationContext = null;

    public static void setApplicationContext(ApplicationContext ctx) {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = ctx;
        }
    }


    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }

}