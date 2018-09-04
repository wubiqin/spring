package com.wbq.spring.springmvc.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
public class WebApplicationContext {
	/**使用ThreadLocal在多线程下隔离*/
	public static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

	public static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

	public static HttpServletRequest getHttpServletRequest() {
		return requestHolder.get();
	}

	public static HttpServletResponse getHttpServletResponse() {
		return responseHolder.get();
	}

	public static HttpSession getHttpSession() {
		return requestHolder.get().getSession();
	}

	public static ServletContext getServletContext() {
		return requestHolder.get().getServletContext();
	}

	/**
	 * 清除ThreadLocal变量 防止gc不能回收 导致oom
	 */
	public static void remove() {
		requestHolder.remove();
		responseHolder.remove();
	}
}
