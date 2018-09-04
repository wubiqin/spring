package com.wbq.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
 * @description 封装对象的PropertyValue
 */
public class PropertyValues {

	private final List<PropertyValue> propertyValueList = new ArrayList<>();

	public void addPropertyValue(PropertyValue pv) {
		checkPropertyValue(pv);
		this.propertyValueList.add(pv);
	}

	public List<PropertyValue> getPropertyValueList() {
		return propertyValueList;
	}

	private void checkPropertyValue(PropertyValue pv) {
		for (PropertyValue propertyValue : propertyValueList) {
			if (propertyValue.getName().equals(pv.getName())) {
				throw new IllegalArgumentException("存在重复的property name:" + pv.getName());
			}
		}
	}
}
