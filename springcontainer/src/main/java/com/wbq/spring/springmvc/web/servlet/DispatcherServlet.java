package com.wbq.spring.springmvc.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Splitter;
import com.wbq.spring.springmvc.annotation.Controller;
import com.wbq.spring.springmvc.annotation.RequestMapping;
import com.wbq.spring.springmvc.util.ClassUtils;
import com.wbq.spring.springmvc.util.HandlerMapping;
import com.wbq.spring.springmvc.util.ScanClassUtils;
import com.wbq.spring.springmvc.util.WebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
public class DispatcherServlet extends HttpServlet {
	private Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.execute(req, resp);
		}
		catch (InvocationTargetException | IllegalAccessException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			this.execute(req, resp);
		}
		catch (InvocationTargetException | IllegalAccessException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		//记得调用父类的init不然可能会发生npe
		super.init(config);
		logger.info("------初始化-----");
		String basePackage = config.getInitParameter("basePackage");
		String split = ",";
		Splitter splitter = Splitter.on(split);
		//如果有多个包 com.wbq.*,com.wbq.**
		if (basePackage.contains(split)) {
			List<String> basePackages = splitter.splitToList(basePackage);
			basePackages.forEach(this::initHandlerMapping);
		}
		else {
			initHandlerMapping(basePackage);
		}
		logger.info("初始化结束");
	}

	/**
	 * 具体执行逻辑
	 * @param request 请求
	 * @param response 响应
	 * @throws InvocationTargetException InvocationTargetException
	 * @throws IllegalAccessException IllegalAccessException
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
		//使用ThreadLocal隔离
		WebApplicationContext.requestHolder.set(request);
		WebApplicationContext.responseHolder.set(response);

		String url = parseRequestUrl(request);
		Method method = HandlerMapping.getMethod(url);
		if (method != null) {
			Class<?> clazz = method.getDeclaringClass();
			Object instance = ClassUtils.instanceClass(clazz);
			//反射调用方法
			Object result = method.invoke(instance);
			logger.info("result:{}", result);
		}
	}

	/**
	 * 解析获得请求的映射路径
	 * @param request 请求
	 * @return 请求的映射路径
	 */
	private String parseRequestUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		String requestUrl = request.getRequestURI();
		return requestUrl.replace(path, "");
	}

	/**
	 * 初始化请求-》方法映射
	 * @param packageName 包名
	 */
	private void initHandlerMapping(String packageName) {
		Set<Class<?>> classes = ScanClassUtils.getClasses(packageName);
		classes.stream().filter(clazz -> clazz.isAnnotationPresent(Controller.class)).forEach(clazz -> {
			Method[] methods = ClassUtils.findDeclareMethods(clazz);
			Arrays.stream(methods).filter(method -> method.isAnnotationPresent(RequestMapping.class)).forEach(method -> {
				//获取映射的路径
				String path = method.getAnnotation(RequestMapping.class).path();
				logger.info("请求{}-————>>方法{}", path, method);
				HandlerMapping.put(path, method);
			});
		});
	}
}
