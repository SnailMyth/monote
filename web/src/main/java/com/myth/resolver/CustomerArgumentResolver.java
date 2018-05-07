package com.myth.resolver;

import com.myth.annotation.ParameterModel;
import com.myth.base.BaseHandlerMethodArgumentResolver;
import com.myth.util.ConverterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomerArgumentResolver extends BaseHandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ParameterModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String parameterName = parameter.getParameterName();
        System.out.println("参数名称：{ " + parameterName + " }");

        Object target = mavContainer.containsAttribute(parameterName) ?
                mavContainer.getModel().get(parameterName) : createAttribute(parameterName, parameter, binderFactory, webRequest);
        return target;
    }








}
