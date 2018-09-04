package com.wbq.spring.io;

import java.net.URL;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public class ResourceLoader {

	public InputStreamSource getInputStreamSource(String location) {
		URL resource = this.getClass().getClassLoader().getResource(location);
		return new UrlResource(resource);
	}
}
