package com.wbq.spring.beans.factory.config.support;

import java.util.HashMap;
import java.util.Map;

import com.wbq.spring.beans.factory.config.BeanDefinition;
import com.wbq.spring.io.ResourceLoader;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
	private Map<String, BeanDefinition> registry = new HashMap<>(256);

	private ResourceLoader resourceLoader;

	AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	public Map<String, BeanDefinition> getRegistry() {
		return registry;
	}
}
