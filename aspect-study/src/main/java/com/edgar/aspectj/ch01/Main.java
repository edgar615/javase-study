package com.edgar.aspectj.ch01;

public class Main {
	public static void main(String[] args) {
		MessageCommunicator messageCommunicator = new MessageCommunicator();
		messageCommunicator.deliver("Wanna learn AspectJ?");
		messageCommunicator.deliver("Harry", "having fun?");
	}
}