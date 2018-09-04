package com.wbq.spring.springmvc.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  *
  * @author biqin.wu
  * @since 13 八月 2018
 * @description 反射工具类
 */
public class ClassUtils {
	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

	public static <T> T instanceClass(Class<T> clazz) {
		//接口和抽象类不可以实例化
		if (!clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
			try {
				return clazz.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e) {
				logger.error(e.getMessage());
			}
		}
		logger.info("接口和抽象类不可以实例化");
		return null;
	}

	/**
	 * 通过构造函数实例化
	 * @param constructor 构造函数
	 * @param args 参数
	 * @param <T> 类型
	 * @return 实例
	 */
	public static <T> T instanceClass(Constructor<T> constructor, Object... args) {
		makeAccessible(constructor);
		try {
			return constructor.newInstance(args);
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 查找方法
	 * @param clazz class
	 * @param methodName name
	 * @param paramTypes param
	 * @return method
	 */
	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 查找类中的所有方法
	 * @param clazz class
	 * @param methodName name
	 * @param paramTypes param
	 * @return method
	 */
	public static Method findDeclareMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException e) {
			if (clazz.getSuperclass() != null) {
				return findDeclareMethods(clazz, methodName, paramTypes);
			}
		}
		return null;
	}

	public static Method[] findDeclareMethods(Class<?> clazz) {
		return clazz.getDeclaredMethods();
	}

	public static Field[] findDeclareFields(Class<?> clazz) {
		return clazz.getDeclaredFields();
	}

	private static Method findDeclareMethods(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}
		catch (NoSuchMethodException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 使构造函数可达
	 * @param constructor 构造函数
	 */
	private static void makeAccessible(Constructor<?> constructor) {
		if (!constructor.isAccessible()) {
			if (!Modifier.isPublic(constructor.getModifiers()) || !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) {
				constructor.setAccessible(true);
			}
		}
	}

}
