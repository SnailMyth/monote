package com.myth.base;

/**
 * 加密内容常量配置
 * 如DES请求参数key
 * ========================
 * Created with IntelliJ IDEA.
 * ========================
 */
public interface ContentSecurityConstants
{
    /**
     * 加密内容字符集
     */
    public static final String CONTENT_CHARSET = "UTF-8";
    /**
     * DES方式请求的request key
     */
    public static final String DES_PARAMETER_NAME = "desString";
    /**
     * DES解密key
     */
    public static final String DES_KEY  = "DE76E3EC39801CEEE0440025";
    /**
     * 传递的attribute前缀
     */
    public static final String ATTRIBUTE_PREFFIX = "security_";
}
