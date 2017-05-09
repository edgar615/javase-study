package com.edgar.aspectj;

public class Email implements Sendable {
	private String body;
	private String toAddress;
	private String fromAddress;
	private String subject;

	public String getBody() {
		return body;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getSubject() {
		return subject;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setBody(String string) {
		body = string;
	}

	public void setFromAddress(String string) {
		fromAddress = string;
	}

	public void setSubject(String string) {
		subject = string;
	}

	public void setToAddress(String string) {
		toAddress = string;
	}
}