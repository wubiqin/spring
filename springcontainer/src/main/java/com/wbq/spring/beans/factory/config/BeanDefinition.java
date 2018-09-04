package com.wbq.spring.beans.factory.config;

import com.wbq.spring.beans.PropertyValues;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
 * @description 封装bean
 */
public class BeanDefinition {

	private Object bean;

	private String beanClassName;

	private Class beanClass;

	private PropertyValues propertyValues = new PropertyValues();

	public BeanDefinition() {
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}
}
