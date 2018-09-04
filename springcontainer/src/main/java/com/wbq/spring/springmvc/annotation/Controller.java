package com.wbq.spring.springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
	/**
	 *
	 * @return 名字
	 */
	String value() default "";
}
