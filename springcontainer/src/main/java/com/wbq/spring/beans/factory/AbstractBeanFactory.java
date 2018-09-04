package com.wbq.spring.beans.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.wbq.spring.beans.factory.config.BeanDefinition;

import org.springframework.beans.factory.BeanNotOfRequiredTypeException;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public abstract class AbstractBeanFactory implements BeanFactory {
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

	private final List<String> beanDefinitionNames = new ArrayList<>(256);

	@Override
	public Object getBean(String name) throws Exception {
		return getBean(name, null);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws Exception {
		BeanDefinition bd = beanDefinitionMap.get(name);
		if (bd == null) {
			throw new IllegalArgumentException("no bean name [" + name + "] is defined");
		}
		Object bean = bd.getBean();
		if (bean == null) {
			bean = doCreateBean(bd);
		}
		if (requiredType != null && !requiredType.isInstance(bean)) {
			throw new BeanNotOfRequiredTypeException(name, requiredType, bean.getClass());
		}
		return (T) bean;
	}

	public void preInstantiateSingleton() throws Exception {
		for (String name : this.beanDefinitionNames) {
			getBean(name);
		}
	}

	public void registerBeanDefinition(String name, BeanDefinition bd) {
		beanDefinitionMap.put(name, bd);
		beanDefinitionNames.add(name);
	}


	/**
	 * 初始化bean
	 * @param bd BeanDefinition
	 * @return
	 * @throws Exception
	 */
	protected abstract Object doCreateBean(BeanDefinition bd) throws Exception;
}
