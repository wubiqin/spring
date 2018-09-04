package com.wbq.spring.springmvc.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
public class HandlerMapping {
	/**请求路径->方法*/
	private static final Map<String, Method> HANDLE_MAP = new HashMap<>(256);

	public static Method getMethod(String path) {
		return HANDLE_MAP.get(path);
	}

	public static void put(String path, Method method) {
		HANDLE_MAP.put(path, method);
	}

	public static Map<String, Method> getHandlerMap() {
		return HANDLE_MAP;
	}
}
