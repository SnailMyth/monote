package com.myth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.myth.dao.LoggerJPA;
import com.myth.domain.LoggerEntity;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggerIntercepter implements HandlerInterceptor {

    //请求开始时间标识
    private static final String LOGGER_SEND_TIME = "_send_time";
    //请求日志实体标识
    private static final String LOGGER_ENTITY = "_logger_entity";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LoggerEntity loggerEntity = new LoggerEntity();
        String sessionId = request.getRequestedSessionId();

        String url = request.getRequestURI();

        String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);

        loggerEntity.setClientIp(LoggerUtils.getCliectIp(request));

        loggerEntity.setMethod(request.getMethod());
        loggerEntity.setType(LoggerUtils.getRequestType(request));
        loggerEntity.setParamData(paramData);
        loggerEntity.setUri(url);
        loggerEntity.setSessionId(sessionId);
        request.setAttribute(LOGGER_SEND_TIME, System.currentTimeMillis());
        //设置请求实体到request内，方面afterCompletion方法调用
        request.setAttribute(LOGGER_ENTITY, loggerEntity);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        int status = response.getStatus();
        long currentTime = System.currentTimeMillis();
        long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
        LoggerEntity logger = (LoggerEntity) request.getAttribute(LOGGER_ENTITY);
        logger.setTimeConsuming(Integer.valueOf((currentTime - time) + ""));
        logger.setReturnTime(currentTime + "");

        logger.setHttpStatusCode(status + "");
        logger.setReturnData(JSON.toJSONString(request.getAttribute(LoggerUtils.LOGGER_RETURN),
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue));

        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        LoggerJPA dao = factory.getBean(LoggerJPA.class);
        dao.save(logger);

    }
}
