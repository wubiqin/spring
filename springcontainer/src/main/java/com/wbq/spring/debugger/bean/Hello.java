package com.wbq.spring.debugger.bean;

/**
 *  *
 *  * @author biqin.wu
 *  * @since 14 九月 2018
 *  
 */
public class Hello {
    private String name;

    public Hello(String name) {
        this.name = name;
        System.out.println("instance hello");
    }

    public void init(){
        System.out.println("hello init");
    }

    public void hello() {
        System.out.println("hello " + name);
    }

}
