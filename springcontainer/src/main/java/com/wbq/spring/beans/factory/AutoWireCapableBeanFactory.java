package com.wbq.spring.beans.factory;

import java.lang.reflect.Field;

import com.wbq.spring.beans.PropertyValue;
import com.wbq.spring.beans.factory.config.BeanDefinition;
import com.wbq.spring.beans.factory.config.BeanReference;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public class AutoWireCapableBeanFactory extends AbstractBeanFactory {
	@Override
	protected Object doCreateBean(BeanDefinition bd) throws Exception {
		Object bean = createBeanInstance(bd);
		bd.setBean(bean);
		applyPropertyValues(bean, bd);
		return bean;
	}

	private Object createBeanInstance(BeanDefinition bd) throws Exception {
		return bd.getBeanClass().newInstance();
	}

	private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
		for (PropertyValue pv : bd.getPropertyValues().getPropertyValueList()) {
			Field declaredField = bean.getClass().getDeclaredField(pv.getName());
			declaredField.setAccessible(true);
			Object value = pv.getValue();
			if (value instanceof BeanReference) {
				BeanReference br = (BeanReference) value;
				value = getBean(br.getName());
			}
			declaredField.set(bean, value);
		}
	}
}
