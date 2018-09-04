package com.wbq.spring.beans.factory.config.support;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public interface BeanDefinitionReader {
	/**
	 * 从配置文件中读取 BeanDefinition
	 * @param location 配置文件
	 * @throws Exception
	 */
	void loadBeanDefinitions(String location) throws Exception;
}
