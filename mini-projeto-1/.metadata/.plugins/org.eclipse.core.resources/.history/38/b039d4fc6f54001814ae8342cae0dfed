package com.jaenia;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorWeb {

	public static void main(String[] args) {
		ServerSocket serverSocket;
		
		try {
			serverSocket = new ServerSocket(4101);
			
			while(!serverSocket.isClosed()) {
				System.out.println("[SW] Servidor rodando em " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
				Socket socket = serverSocket.accept();
				ThreadConexao st = new ThreadConexao(socket);
				st.start();
			}
			//serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
