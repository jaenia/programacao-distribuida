package br.edu.ifpb.tsi.pd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteWeb {

	public static void main(String[] args) {
		for(int i=0;i<5;i++) {
			ClienteThread ct = new ClienteThread();
			ct.start();
		}
	}

}
