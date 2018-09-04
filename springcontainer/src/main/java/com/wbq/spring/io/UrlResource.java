package com.wbq.spring.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
  *
  * @author biqin.wu
  * @since 12 八月 2018
  */
public class UrlResource implements InputStreamSource {
	private final URL url;

	public UrlResource(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection urlConnection = url.openConnection();
		urlConnection.connect();
		return urlConnection.getInputStream();
	}
}
