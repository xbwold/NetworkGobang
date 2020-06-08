package com.wold.net;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class ChatThread extends Thread {
	private Socket client;
	private List<Socket> list;
	
	public ChatThread(Socket client,List<Socket> list){
		this.client=client;
		this.list=list;
	}
	
	public void run() {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(true) {
				String data=br.readLine();
				System.out.println(data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		for (Socket socket : list) {
			 PrintWriter bw;
			try {
				bw = new PrintWriter(socket.getOutputStream(),true);
				 bw.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
