package com.wbq.spring.debugger;

import com.wbq.spring.debugger.bean.Hello;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *  *
 *  * @author biqin.wu
 *  * @since 14 九月 2018
 *  
 */
public class SpringBeanLifeCycle implements ApplicationContextAware, BeanPostProcessor {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before instance");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after instance");
        return bean;
    }

    public void test(){
        Hello hello= (Hello) context.getBean("hello");
        hello.hello();
    }
}
