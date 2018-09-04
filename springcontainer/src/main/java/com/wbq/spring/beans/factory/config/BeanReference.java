package com.wbq.spring.beans.factory.config;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
 * @description 对bean的引用
 */
public class BeanReference {
	private String name;

	public BeanReference(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
