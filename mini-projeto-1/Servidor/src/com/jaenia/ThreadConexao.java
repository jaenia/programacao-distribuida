package com.jaenia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class ThreadConexao extends Thread{
	final private String baseDirectory = System.getProperty("user.dir").concat("/public/");
	private Socket socket;

	public ThreadConexao(Socket socket) {
		this.socket = socket;
	}
	
	public String pathApplication() {
		return "";	
	}
	
	public void run() {
		String file_name, status;
		try {
			System.out.println("[TC] Thread atual no servidor: " + Thread.currentThread().getName());
			System.out.println("[TC] Conectou com " + socket.getInetAddress() + ":" + socket.getPort());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			//recuperando nome do arquivo
			file_name = in.readUTF();
			System.out.println("[TC] Servidor recebeu: " + file_name);
			
			//buscando o arquivo
			File file = new File(baseDirectory + file_name);
		
			if(file.exists()) {
				System.out.println("[TC] Arquivo encontrado");
				status = "200";
				out.writeUTF(status);
				String content = new String(Files.readAllBytes(file.toPath()));
				out.writeUTF("Conte�do do arquivo: " + file_name + "\n" + content + "\n");			
			}else {
				System.out.println("[TC] Arquivo n�o encontrado");
				status = "400";
				out.writeUTF(status);
			}
			socket.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
