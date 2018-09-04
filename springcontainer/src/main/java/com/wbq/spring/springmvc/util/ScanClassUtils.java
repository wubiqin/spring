package com.wbq.spring.springmvc.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  *
  * @author biqin.wu
  * @since 14 八月 2018
  */
public class ScanClassUtils {
	private static final Logger logger = LoggerFactory.getLogger(ScanClassUtils.class);

	/**是否迭代*/
	private static final boolean RECURSIVE = true;

	/**
	 * 获取类的容器
	 * @param pack 包 com.wbq.**
	 * @return 类的容器
	 */
	public static Set<Class<?>> getClasses(String pack) {
		logger.info("获取包下的类文件 包路径{}", pack);
		Set<Class<?>> classes = new LinkedHashSet<>();
		//获取包的名字
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');

		try {
			//获取这个路径下的文件
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				//获取协议
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					logger.info("file类型的文件扫描");
					//文件的物理路径
					String encoding = "UTF-8";
					String filePath = URLDecoder.decode(url.getFile(), encoding);
					//在包中搜索类文件
					findAndAddClasses(packageName, filePath, RECURSIVE, classes);
				}
				else if ("jar".equals(protocol)) {
					logger.info("jar类型的文件扫描");
					//获取jar
					JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
					//获取这个jar包下的文件
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry jarEntry = entries.nextElement();
						String fileName = jarEntry.getName();

						if (fileName.charAt(0) == '/') {
							fileName = fileName.substring(1);
						}
						if (fileName.startsWith(packageDirName)) {
							int idx = fileName.lastIndexOf('/');
							if (idx != -1) {
								//获取包名 将/ 替换为 .
								packageName = fileName.substring(0, idx).replace('/', '.');
							}
							if (idx != -1 || RECURSIVE) {
								//如果是class文件 且entry不是目录
								if (!jarEntry.isDirectory() && fileName.endsWith(".class")) {
									//获取类名
									String className = fileName.substring(packageName.length() + 1,
											fileName.length() - 6);
									String classPath = packageName + "." + className;
									//添加到容器中
									logger.info("添加类->{}", classPath);
									classes.add(Class.forName(classPath));
								}
							}
						}
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return classes;
	}

	/**
	 * 在包中加载类文件
	 * @param packageName 包名
	 * @param filePath 文件路径
	 * @param recursive 迭代
	 * @param classes 类容器
	 */
	private static void findAndAddClasses(String packageName, String filePath, boolean recursive, Set<Class<?>> classes) {
		logger.info("在包中加载类文件 包名{},包路径{}", packageName, filePath);
		File dir = new File(filePath);
		//文件不存在或者不是目录直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		//过滤文件 只保留目录和class文件
		File[] files = dir.listFiles(file -> recursive && file.isDirectory() || file.getName().endsWith(".class"));
		if (files != null) {
			//处理目录
			Arrays.stream(files)
					.filter(File::isDirectory)
					.forEach(file -> findAndAddClasses(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes));

			//处理class文件
			Arrays.stream(files).filter(File::isFile).filter(file -> file.getName().endsWith(".class"))
					.forEach(file -> {
						try {
							String fileName = file.getName();
							//获取类名
							String className = splitString(fileName,".").get(0);
							String classPath = packageName + "." + className;
							logger.info("添加类->{}", classPath);
							//添加到容器中
							Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(classPath);
							classes.add(c);
						}
						catch (ClassNotFoundException e) {
							logger.error(e.getMessage());
						}
					});
		}
	}

	/**
	 *
	 * @param origin 原始字符串
	 * @param split 分割符号
	 * @return 字符串数组
	 */
	private static List<String> splitString(String origin, String split) {
		Splitter splitter = Splitter.on(split);
		return splitter.splitToList(origin);
	}
}
