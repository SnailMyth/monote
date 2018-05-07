package com.myth.annotation;

import com.myth.enums.ContentSecurityAway;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContentSecurity {

    /**
     * 内容加密方式
     * 默认DES
     * @return
     */
    ContentSecurityAway away() default ContentSecurityAway.DES;
}
