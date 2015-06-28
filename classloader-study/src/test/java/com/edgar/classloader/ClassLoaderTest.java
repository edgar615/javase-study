package com.edgar.classloader;

import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name)
					throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					if (is == null) {
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
		};
		Object obj = myLoader.loadClass("com.edgar.classloader.ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof ClassLoaderTest);
	}

}
