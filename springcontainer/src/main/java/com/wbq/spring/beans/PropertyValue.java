package com.wbq.spring.beans;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
 * @description bean的属性
 */
public class PropertyValue {
	private String name;

	private Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
