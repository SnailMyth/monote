package com.myth.intercepter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myth.annotation.ContentSecurity;
import com.myth.base.ContentSecurityConstants;
import com.myth.utils.DES3Util;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class ContentSecurityInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        boolean canPass = true;//默认可以通过

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();

        ContentSecurity annotation = method.getAnnotation(ContentSecurity.class);

        if (annotation != null){
            switch (annotation.away()){
                case DES:
                    canPass = checkDES(request,response);
                    break;
                default:
                    break;
            }
        }


        return canPass;
    }

    private boolean checkDES(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String des = request.getParameter(ContentSecurityConstants.DES_PARAMETER_NAME);
        System.out.println("请求加密的内容:{ "+ des +" }");
        if (des == null || des.length() == 0){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","The DES Content Security Away Request , Parameter Required is "+ ContentSecurityConstants.DES_PARAMETER_NAME);
            response.getWriter().print(JSON.toJSONString(jsonObject));
        }


        try {
            des = DES3Util.decrypt(des, DES3Util.DESKEY, "UTF-8");
            if (!StringUtils.isEmpty(des)){
                JSONObject params = JSON.parseObject(des);
                System.out.println("解密请求后获得参数列表:{ "+ des +" }");
                Iterator<String> iterator = params.keySet().iterator();
                while (iterator.hasNext()){
                    String parameterName = iterator.next();
                    if (!StringUtils.isEmpty(parameterName)) {
                        request.setAttribute(ContentSecurityConstants.ATTRIBUTE_PREFFIX + parameterName,params.get(parameterName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("msg","The DES Content Security Error."+ContentSecurityConstants.DES_PARAMETER_NAME);
            response.getWriter().print(JSON.toJSONString(json));
            return  false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
