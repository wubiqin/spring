package com.wbq.spring.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
 * @description 使用map保存BeanDefinition
 */
public interface BeanFactory {
	/**
	 * 通过名称获取bean
	 * @param name 名称
	 * @return bean
	 * @throws BeansException  BeansException
	 */
	Object getBean(String name) throws Exception;

	/**
	 * 通过名称获取指定类型的bean
	 * @param name 名称
	 * @param requiredType 类型
	 * @return bean
	 * @throws BeansException BeansException
	 */
	<T> T getBean(String name, @Nullable Class<T> requiredType) throws Exception;
}
