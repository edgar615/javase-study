package com.edgar.designpattern.strategy.ftoc;

public class ApplicationRunner {

	private Application application = null;

	public ApplicationRunner(Application application) {
		super();
		this.application = application;
	}

	public void run() {
		application.init();
		while (!application.done()) {
			application.idle();
		}
		application.cleanup();
	}
}
