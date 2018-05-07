package com.myth.base;

import com.myth.util.ConverterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Field;

import java.util.*;

public abstract class BaseHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{

    /**
     * 获取PathVariables集合
     * @param request 请求对象
     * @return
     */
    protected final Map<String, String> getUrlTemplateVariables(NativeWebRequest request) {
        Map<String, String> variables =
                (Map<String, String>) request.getAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        return (variables != null) ? variables : Collections.emptyMap();
    }

    /**
     * 获取指定前缀的参数：包括uri varaibles 和 parameters
     * @param namePrefix
     * @param request
     * @return
     * @subPrefix 是否截取掉namePrefix的前缀
     */
    protected Map<String, String[]> getPrefixParameterMap(String namePrefix, NativeWebRequest request, boolean subPrefix) {
        Map<String, String[]> result = new HashMap();
        //获取URL的参数集合
        Map<String, String> variables = getUrlTemplateVariables(request);

        int namePrefixLength = namePrefix.length();
        for (String name : variables.keySet()) {
            if (name.startsWith(namePrefix)) {

                //page.pn  则截取 pn
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    //如果下一个字符不是 数字 . _  则不可能是查询 只是前缀类似
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), new String[]{variables.get(name)});
                } else {
                    result.put(name, new String[]{variables.get(name)});
                }
            }
        }

        /**
         * 从request parameterMap集合内获取该前缀的参数列表
         */
        Iterator<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasNext()) {
            String name = parameterNames.next();
            if (name.startsWith(namePrefix)) {
                if (subPrefix) {
                    char ch = name.charAt(namePrefix.length());
                    if (illegalChar(ch)) {
                        continue;
                    }
                    result.put(name.substring(namePrefixLength + 1), request.getParameterValues(name));
                } else {
                    result.put(name, request.getParameterValues(name));
                }
            }
        }

        return result;
    }

    /**
     * 验证参数前缀是否合法
     * @param ch
     * @return
     */
    private boolean illegalChar(char ch) {
        return ch != '.' && ch != '_' && !(ch >= '0' && ch <= '9');
    }

    /**
     * Obtain a value from the request that may be used to instantiate the
     * model attribute through type conversion from String to the target type.
     * <p>The default implementation looks for the attribute name to match
     * a URI variable first and then a request parameter.
     *
     * @param attributeName the model attribute name
     * @param request       the current request
     * @return the request value to try to convert or {@code null}
     */
    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        Map<String, String> variables = getUrlTemplateVariables(request);
        if (StringUtils.hasText(variables.get(attributeName))) {
            return variables.get(attributeName);
        } else if (StringUtils.hasText(request.getParameter(attributeName))) {
            return request.getParameter(attributeName);
        } else {
            return null;
        }
    }

    /**
     * Create a model attribute from a String request value (e.g. URI template
     * variable, request parameter) using type conversion.
     * <p>The default implementation converts only if there a registered
     * {@link org.springframework.core.convert.converter.Converter} that can perform the conversion.
     *
     * @param sourceValue   the source value to create the model attribute from
     * @param attributeName the name of the attribute, never {@code null}
     * @param parameter     the method parameter
     * @param binderFactory for creating WebDataBinder instance
     * @param request       the current request
     * @return the created model attribute, or {@code null}
     * @throws Exception
     */
    protected Object createAttributeFromRequestValue(String sourceValue,
                                                     String attributeName,
                                                     MethodParameter parameter,
                                                     WebDataBinderFactory binderFactory,
                                                     NativeWebRequest request) throws Exception {
        /**
         * 获取类型转换业务逻辑实现类
         */
        DataBinder binder = binderFactory.createBinder(request, null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            //源类型描述
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            //根据目标参数对象获取目标参数类型描述
            TypeDescriptor target = new TypeDescriptor(parameter);
            //验证是否可以进行转换
            if (conversionService.canConvert(source, target)) {
                return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
            }
        }
        return null;
    }

    /**
     * Extension point to create the model attribute if not found in the model.
     * The default implementation uses the default constructor.
     *
     * @param attributeName the name of the attribute, never {@code null}
     * @param parameter     the method parameter
     * @param binderFactory for creating WebDataBinder instance
     * @param request       the current request
     * @return the created model attribute, never {@code null}
     */
    protected Object createAttribute(String attributeName, MethodParameter parameter,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

        String value = getRequestValueForAttribute(attributeName, request);

        if (value != null) {
            Object attribute = createAttributeFromRequestValue(value, attributeName, parameter, binderFactory, request);
            if (attribute != null) {
                return attribute;
            }
        }

        Object attributePre = createAttributeFromParameterAsPre(parameter,request);
        if(attributePre!=null)
        {
            return attributePre;
        }

        return BeanUtils.instantiateClass(parameter.getParameterType());
    }


    /**
     * 从request内获取parameter前缀的所有参数
     * 并根据parameter的类型将对应字段的值设置到parmaeter对象内并返回
     *
     * @param parameter
     * @param request
     * @return
     */
    protected Object createAttributeFromParameterAsPre(MethodParameter parameter, NativeWebRequest request) {
        /**
         * 根据请求参数类型初始化空对象
         */
        Object object = BeanUtils.instantiateClass(parameter.getParameterType());
        /**
         * 获取指定前缀的请求参数集合
         */
        Map<String, String[]> parameters = getPrefixParameterMap(parameter.getParameterName(), request, true);
        Iterator<String> iterator = parameters.keySet().iterator();
        while (iterator.hasNext()) {
            //字段名称
            String filedName = iterator.next();
            //请求参数值
            String[] parameterValue = parameters.get(filedName);
            try {
                Field filed = object.getClass().getDeclaredField(filedName);
                filed.setAccessible(true);

                //字段的类型
                Class<?> filedTargetType = filed.getType();

                //List（ArrayList、LinkedList）类型
                //将数组类型的值转换为List集合对象
                if (filedTargetType.isAssignableFrom(List.class)) {
                    filed.set(object, Arrays.asList(parameterValue));
                }
                //Object数组类型，直接将数组值设置为目标字段的值
                else if (Object[].class.isAssignableFrom(filedTargetType)) {
                    filed.set(object, parameterValue);
                }
                /**
                 * 单值时获取数组索引为0的值
                 */
                else {
                    String strType = filed.getGenericType().getTypeName();
                    filed.set(object, ConverterUtil.String2Other(parameterValue[0], strType));
                }
            } catch (Exception e) {
                System.out.println("Set Field：{" + filedName + "} Value Error，In {" + object.getClass().getName() + "}");
                continue;
            }
        }
        return object;
    }



}
