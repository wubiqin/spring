package com.wbq.spring.debugger;

import com.wbq.spring.debugger.bean.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  *
 *  * @author biqin.wu
 *  * @since 14 九月 2018
 *  
 */
public class LearnSpringBean {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        Hello hello = (Hello) context.getBean("hello");
        hello.hello();

        SpringBeanLifeCycle springBeanLifeCycle= (SpringBeanLifeCycle) context.getBean("springBeanLifeCycle");
        springBeanLifeCycle.test();
    }
}
