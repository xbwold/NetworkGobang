package com.wold.net;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.wold.controller.TimeThread;
import com.wold.page.MainPage;

public class ChatThread extends Thread {
	private Socket client;
	private List<Socket> socketList;
	private List<PrintWriter> list;
	private List<String> homeList;
	List<List<Socket>> home;

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
				System.out.println(dataArr[0]);
				if (dataArr[0].equals("0")) {
//					System.out.println("����λ��");
//					MainPage.state1.setText("״̬:�ȴ�...");
//					MainPage.state2.setText("״̬:����...");
//					player.setState(1);
//					player.setPlayerChessCoord(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]), 2);
//					MainPage.contre.repaint();
//					MainPage.contre.setChickeAble(true);
//					MainPage.time = new TimeThread(MainPage.countDown);
//					MainPage.time.start();
				} else if (dataArr[0].equals("1")) {
//					data += "\n";
//					showMessage.append(data);
				} else if (dataArr[0].equals("2")) {
//					// �Է����ʤ����������Ϸ
//					MainPage.time.interrupt();
//					String winMessage = "���ź�������!";
//					JOptionPane.showMessageDialog(null, winMessage);
//					player.initPlayerChessCoord();
//					MainPage.contre.repaint();
//
//					player.setState(1);
//					MainPage.contre.setChickeAble(true);
//					MainPage.time = new TimeThread(MainPage.countDown);
//					MainPage.state1.setText("״̬:�ȴ�...");
//					MainPage.state2.setText("״̬:����...");
//					MainPage.time.start();

				} else if (dataArr[0].equals("3")) {// ������Ϸ������Ϣ
					System.out.println(data);
					sendMessageAllUser(data);
				} else if (dataArr[0].equals("4")) { // ���մ�������
					homeList.add(data);
					
					sendMessageAllUser(data);
				} else if (dataArr[0].equals("5")) { // ����ˢ������
					for (int i = 0; i < socketList.size(); i++) {
						Socket socket = socketList.get(i);
						if (socket == this.client) {
							PrintWriter bw = list.get(i);
							System.out.println("��ǰ������:" + homeList.size());
							for (String string : homeList) {
								bw.println(string);
							}
							break;
						}
					}

				} else if (dataArr[0].equals("6")) { // ���ܼ��뷿������

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessageAllUser(String message) {
		for (PrintWriter bw : list) {
			System.out.println("����ȥһ��");
			bw.println(message);
		}
	}

}
