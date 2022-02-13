package com.egs.atmservice.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


public class HttpUtil {
    public static String getRemoteAddress() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = attribs != null ? ((ServletRequestAttributes) attribs).getRequest() : null;
            return Objects.requireNonNull(request).getRemoteAddr();
        }
        return null;
    }

    public static String getUserAgent() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = attribs != null ? ((ServletRequestAttributes) attribs).getRequest() : null;
            return Objects.requireNonNull(request).getHeader("User-Agent");
        }
        return "unknown";
    }
}
