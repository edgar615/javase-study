package com.edgar.classloader;


public class DynamicDispatch {

	static abstract class Human {
		protected abstract void sayHello();
	}

	static class Man extends Human {

		@Override
		protected void sayHello() {
			System.out.println("hello, Man!");
		}
	}

	static class Women extends Human {
		@Override
		protected void sayHello() {
			System.out.println("hello, Women!");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Human man = new Man();
		Human women = new Women();
		man.sayHello();
		women.sayHello();
		man = new Women();
		man.sayHello();
	}

}
