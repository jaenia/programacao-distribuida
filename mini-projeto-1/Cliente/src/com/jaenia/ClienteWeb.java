package com.jaenia;

public class ClienteWeb {

	public static void main(String[] args) {
		for(int i=0;i<1;i++) {
			ClienteThread ct = new ClienteThread();
			ct.start();
		}
	}
}
