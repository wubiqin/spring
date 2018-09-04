package com.wbq.spring.springmvc.controller;


import com.wbq.spring.springmvc.annotation.Controller;
import com.wbq.spring.springmvc.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
@Controller("login")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(path = "/login")
	public String login() {
		logger.info("登录成功");
		return "登录成功";
	}
}
