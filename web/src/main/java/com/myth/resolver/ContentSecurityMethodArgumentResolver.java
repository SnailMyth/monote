package com.myth.resolver;

import com.myth.annotation.ContentSecurityAttribute;
import com.myth.base.BaseHandlerMethodArgumentResolver;
import com.myth.base.ContentSecurityConstants;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Enumeration;


public class ContentSecurityMethodArgumentResolver extends BaseHandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ContentSecurityAttribute.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String name = parameter.getParameterAnnotation(ContentSecurityAttribute.class).value();
        Object target = mavContainer.containsAttribute(name) ?
                mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, webRequest);

        WebDataBinder binder = binderFactory.createBinder(webRequest,target,name);
        if (target != null){
            bindRequestAttributes(binder, webRequest);

            validateIfApplicable(binder, parameter);
            /**
             * 存在参数绑定异常
             * 抛出异常
             */
            if (binder.getBindingResult().hasErrors()) {
                if (isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
        }

        target = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType());

        mavContainer.addAttribute(name,target);
        return target;
    }

    /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid}.
     *
     * @param binder    the DataBinder to be used
     * @param parameter the method parameter
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation annot : annotations) {
            if (annot.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = AnnotationUtils.getValue(annot);
                binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
            }
        }
    }

    /**
     * Whether to raise a {@link BindException} on bind or validation errors.
     * The default implementation returns {@code true} if the next method
     * argument is not of type {@link Errors}.
     *
     * @param binder    the data binder used to perform data binding
     * @param parameter the method argument
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

        return !hasBindingResult;
    }

    /**
     * 绑定请求参数
     * @param binder
     * @param nativeWebRequest
     * @throws Exception
     */
    protected void bindRequestAttributes(
            WebDataBinder binder,
            NativeWebRequest nativeWebRequest) throws Exception {

        /**
         * 获取返回对象实例
         */
        Object obj = binder.getTarget();
        /**
         * 获取返回值类型
         */
        Class<?> targetType = binder.getTarget().getClass();
        /**
         * 转换本地request对象为HttpServletRequest对象
         */
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        /**
         * 获取所有attributes
         */
        Enumeration attributeNames = request.getAttributeNames();
        /**
         * 遍历设置值
         */
        while(attributeNames.hasMoreElements())
        {
            //获取attribute name
            String attributeName = String.valueOf(attributeNames.nextElement());
            /**
             * 仅处理ContentSecurityConstants.ATTRIBUTE_PREFFIX开头的attribute
             */
            if(!attributeName.startsWith(ContentSecurityConstants.ATTRIBUTE_PREFFIX))
            {
                continue;
            }
            //获取字段名
            String fieldName = attributeName.replace(ContentSecurityConstants.ATTRIBUTE_PREFFIX,"");
            Field field = null;
            try {
                field = targetType.getDeclaredField(fieldName);
            }
            /**
             * 如果返回对象类型内不存在字段
             * 则从父类读取
             */
            catch (NoSuchFieldException e)
            {
                try {
                    field = targetType.getSuperclass().getDeclaredField(fieldName);
                }catch (NoSuchFieldException e2)
                {
                    continue;
                }
                /**
                 * 如果父类还不存在，则直接跳出循环
                 */
                if(StringUtils.isEmpty(field)) {
                    continue;
                }
            }
            /**
             * 设置字段的值
             */
            field.setAccessible(true);
            String fieldClassName = field.getType().getSimpleName();
            Object attributeObj = request.getAttribute(attributeName);

            System.out.println("映射安全字段："+ fieldName +"}，字段类型：{"+ fieldClassName +"}，字段内容：{"+attributeObj+"}");

            if("String".equals(fieldClassName)) {
                field.set(obj,attributeObj);
            }
            else if("Integer".equals(fieldClassName))
            {
                field.setInt(obj,Integer.valueOf(String.valueOf(attributeObj)));
            }
            else{
                field.set(obj,attributeObj);
            }
        }
        ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
        servletBinder.bind(new MockHttpServletRequest());
    }



}
