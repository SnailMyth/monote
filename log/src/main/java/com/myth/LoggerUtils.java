package com.myth;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class LoggerUtils {

    public static final String LOGGER_RETURN = "";
    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(ip)){
                ip = str;
                break;
            }
        }
        return  ip;
    }

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }
}
