package com.wbq.spring.io;

import java.io.IOException;
import java.io.InputStream;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public interface InputStreamSource {
	/**
	 * 获取配置文件
	 * @return InputStream
	 * @throws IOException io异常
	 */
	InputStream getInputStream() throws IOException;
}
