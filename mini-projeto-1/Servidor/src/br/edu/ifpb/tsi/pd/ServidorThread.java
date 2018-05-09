package br.edu.ifpb.tsi.pd;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class ServidorThread extends Thread{
	private Socket socket;
	private String file, status;

	public ServidorThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName());
			System.out.println("Conectou com " + socket.getInetAddress() + ":" + socket.getPort());
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			
			//l�gica
			//recuperando nome do arquivo
			file = in.readUTF();
			System.out.println("Servidor recebeu: " + file);
			
			//buscando o arquivo
			File arquivo = new File("C:\\Users\\jaeni\\Documents\\TSI - IFPB\\2018.1\\Programa��o Distribu�da"
					+ "\\workspace\\Mini Projetos\\Mini Projeto 1\\Servidor\\public\\" + file);
		
			if(arquivo.exists()) {
				System.out.println("achou");
				status = "200";
				out.writeUTF(status);
				String conteudo = new String(Files.readAllBytes(arquivo.toPath()));
				//System.out.println(conteudo);
				out.writeUTF("Conte�do do arquivo: " + file + "\n" + conteudo + "\n");
				
			}else {
				System.out.println("n�o achou");
				status = "400";
				out.writeUTF(status);
			}
			
			socket.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
