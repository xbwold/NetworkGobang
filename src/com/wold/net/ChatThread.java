package com.wold.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.wold.controller.TimeThread;
import com.wold.page.GameHall;
import com.wold.page.MainPage;

public class ChatThread extends Thread {
	private Socket client;
	private List<Socket> socketList;
	private List<PrintWriter> list;
	private List<String> homeList;
	private List<List<Socket>> home;		//房间列表
	private List<Socket> createHome=null;	//当前房间的socket
	//private int number;	//房间号

	public ChatThread(Socket client, List<Socket> socketList, List<PrintWriter> list, List<String> homeLis,
			List<List<Socket>> home) {
		this.client = client;
		this.list = list;
		this.socketList = socketList;
		this.homeList = homeLis;
		this.home = home;
	}

	public void run() {
		try {
			while (true) {
				
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String data = br.readLine();
				String dataArr[] = data.split(":");
				System.out.println(data);
				if (dataArr[0].equals("0")||dataArr[0].equals("7")||dataArr[0].equals("8")) {	//0棋子信息、7悔棋申请、8对手认输直接转发
					int id=getSocketId(client);
					sendMessageById(id, data);
					System.out.println("信息转发id"+id+"  "+data);

				} else if (dataArr[0].equals("1")) {	//房间内信息
					int id=getSocketId(client);
					sendMessageById(id,data);
				} else if (dataArr[0].equals("2")) {	//接收准备信息
					int id=getSocketId(client);
					sendMessageById(id, data);
				} else if (dataArr[0].equals("3")) {// 接受游戏大厅消息
					System.out.println(data);
					sendMessageAllUser(data);
				} else if (dataArr[0].equals("4")) { // 创建房间
					homeList.add(data);
					createHome = new ArrayList<>();
					createHome.add(client);
					createHome.add(null);
					this.home.add(createHome);
					//this.number=home.size();
					sendMessageOtherUser(data);
				} else if (dataArr[0].equals("5")) { // 进入游戏大厅时获取已存在的房间
					for (int i = 0; i < socketList.size(); i++) {
						Socket socket = socketList.get(i);
						if (socket == this.client) {
							PrintWriter bw = list.get(i);					
							for (String string : homeList) {
								bw.println(string);
							}
							bw.flush();
							break;
						}
					}

				} else if (dataArr[0].equals("6")) { // 加入房间				
					String str = homeList.get(Integer.valueOf(dataArr[1]));
					String temp = "";
					String strArr[] = str.split(":");
					for (int i = 0; i < strArr.length - 1; i++) {
						String string = strArr[i];
						temp += string + ":";
					}
					temp += dataArr[2];				
					homeList.set(Integer.valueOf(dataArr[1]), temp);
					sendMessageOtherUser(data);
					createHome=home.get(Integer.valueOf(dataArr[1]));
					createHome.set(1, this.client);
					home.set(Integer.valueOf(dataArr[1]), createHome);
					//this.number=Integer.valueOf(dataArr[1])+1;
					int id=getSocketId(client);
					String message="5:"+dataArr[2];
					sendMessageById(id, message);
				}else if(dataArr[0].equals("9")) {	//有人退出房间
					int number=home.indexOf(createHome);
					System.out.println("----------------------");
					System.out.println(number);
					if(dataArr[1].equals("1")) { //房主退出，解散房间
						for(int i=0;i<2;i++) {	//用于开启游戏游戏大厅点击事件
							String message="9:0";
							if(i==0) {
								message+=":0";
							}else {
								message+=":1";
							}
							if(createHome.get(i)!=null) {
								for (int j=0;i<socketList.size();j++) {
									Socket socket1=socketList.get(j);
									if(createHome.get(i)==socket1) {
										sendMessageById(j, message);
										break;
									}
								}
							}
						}
					
						createHome=null;
						home.remove(number);
						homeList.remove(number);
						
					}else { //玩家退出，等待玩家加入
						
						//开启游戏大厅按钮点击事件
						String message="9:0:0";
						int id=getSocketId(createHome.get(0));
						sendMessageById(id, message);
						createHome.set(1, null);
						home.set(number,createHome);
						String str = homeList.get(number);
						String temp = "";
						String strArr[] = str.split(":");
						for (int i = 0; i < strArr.length - 1; i++) {
							String string = strArr[i];
							temp += string + ":";
						}
						temp += "等待玩家加入";				
						homeList.set(number, temp);
					}
					//通知所有玩家
					data+=":"+number;
					sendMessageAllUser(data);
				}
			}

		} catch (IOException e) {
			//e.printStackTrace();
			//用户退出游戏大厅
			System.out.println("用户关闭游戏大厅");
			int i=socketList.indexOf(client);
			System.out.println("==================");
			System.out.println(i);
			socketList.remove(i);	
			list.remove(i);
		}
	}

	public void sendMessageAllUser(String message) {
		for (PrintWriter bw : list) {
			System.out.println("发出去一个");
			bw.println(message);
		}
	}

	public void sendMessageOtherUser(String message) {		
		for (int i = 0; i < socketList.size(); i++) {
			Socket socket = socketList.get(i);
			if (socket != client) {
				PrintWriter bw = list.get(i);
				System.out.println("发出去一个:   "+message);
				bw.println(message);
			}
		}
	}
	
	public void sendMessageById(int id,String message) {
		System.out.println("通过id:"+id+"发送了   "+message);
		PrintWriter bw = this.list.get(id);
		bw.println(message);
	}
	
	/**
	 * 获取socket对手的id
	 * @param socket
	 * @return
	 */
	public int getSocketId(Socket socket) {
		Socket otherSocket=null;
		if (socket == createHome.get(0)) {
			otherSocket = createHome.get(1);

		}
		if (socket == createHome.get(1)) {
			otherSocket = createHome.get(0);

		}
		for (int i=0;i<socketList.size();i++) {
			Socket socket1=socketList.get(i);
			if(otherSocket==socket1) {
				return i;
			}
		}
		return -1;
	}
}
