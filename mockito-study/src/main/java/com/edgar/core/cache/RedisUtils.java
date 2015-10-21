package com.edgar.core.cache;

import java.io.*;

public class RedisUtils {
    public static byte[] getKey(String key) {
		return key.getBytes();
	}

	public static <T> T byte2Object(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object value = objectInputStream.readObject();

			return (T) value;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
					objectInputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static <T> byte[] object2Bytes(T value) {

		if (value == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(value);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} finally {
			try {
				if (objectOutputStream != null) {
					objectOutputStream.close();
					objectOutputStream = null;
				}
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}