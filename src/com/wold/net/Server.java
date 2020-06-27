package com.wold.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.wold.controller.TimeThread;
import com.wold.page.MainPage;
import com.wold.pojo.Player;

public class Server extends Thread {
	private JTextArea showMessage;
	private Player player;
	private ServerSocket server;
	private List<Socket> listClientSocket;
	private List<PrintWriter> listClientPrintWriter;
	private List<String> homeList=Collections.synchronizedList(new ArrayList<>());; //创建房间的所用信息
	private List<List<Socket>> home=new ArrayList<>();
	
	public Server() {
		listClientSocket = Collections.synchronizedList(new ArrayList<>());
		listClientPrintWriter = Collections.synchronizedList(new ArrayList<>());
//		this.part=part;
//		this.showMessage=showMessage;
//		this.player=player;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	public void run() {
		try {
			server = new ServerSocket(9999);
			System.out.println("服务器开启");

			while (true) {
				Socket scoktClient = server.accept();
				System.out.println(scoktClient.getInetAddress().getHostAddress() + "加入服务器");
				listClientSocket.add(scoktClient);
				PrintWriter bw = new PrintWriter(scoktClient.getOutputStream(), true);
				listClientPrintWriter.add(bw);
				new ChatThread(scoktClient,listClientSocket,listClientPrintWriter,homeList,home).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
