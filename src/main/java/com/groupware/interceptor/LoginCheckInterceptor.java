package com.groupware.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	// 회원전용처리를 위한 Interceptor
	// 1. extends HandlerInterceptorAdapter
	// 2. Override
	// 3. Interceptor 주소처리는 servlet-context.xml에서 (/loginCheck/**)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle >>>>>>>");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("login") == null) {
			// /loginCheck/** 주소일 경우 ..없이 호출하면 sub 주소가 남아있게 되어 /loginCheck/loginForm 주소 요청이 됨
			response.sendRedirect("../loginForm");
			// servlet-context.xml
			// <view-controller path="/loginForm" view-name="loginForm"/>
			return false;
		} else
			return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	
}
