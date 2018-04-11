package com.myth.handler;

import com.myth.base.ApiResult;
import com.myth.base.ApiResultGenerator;
import com.myth.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BaseExceptionResolver extends AbstractHandlerExceptionResolver {
	

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ex.printStackTrace();
		StringUtils.printString(BaseExceptionResolver.class, ex.getMessage());
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		ApiResult result = ApiResultGenerator.errorResult(ex.getMessage(), ex);
		mv.addObject("result", result);
		return mv;
	}
}
