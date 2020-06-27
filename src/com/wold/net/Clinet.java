package com.wold.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.wold.controller.Judge;
import com.wold.controller.TimeThread;
import com.wold.page.GameHall;
import com.wold.page.MainPage;
import com.wold.pojo.Player;

public class Clinet extends Thread {
	private String ip;
	private Socket socket;
	private JTextArea showMessage;

	private Player player;

	public Clinet(String ip) {
		this.ip=ip;
//		this.showMessage = showMessage;
//		this.player = player;
	}

	public void run() {
		try {
			System.out.println("�ͻ��˿���");
			socket = new Socket(ip, 9999);
			sendMessage("5:get");
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String data = br.readLine();
				System.out.println(data);
				String dataArr[] = data.split(":");
				if (dataArr[0].equals("0")) {
					System.out.println("����λ��");
					MainPage.state1.setText("״̬:����...");
					MainPage.state2.setText("״̬:�ȴ�...");
					player.setState(1);
					player.setPlayerChessCoord(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]), 1);
					System.out.println(Integer.valueOf(dataArr[1]) + "," + Integer.valueOf(dataArr[2]));
					MainPage.contre.repaint();
					MainPage.contre.setChickeAble(true);
					MainPage.time = new TimeThread(MainPage.countDown);
					MainPage.time.start();
				} else if (dataArr[0].equals("1")) {
					data += "\n";
					showMessage.append(data);
				} else if (dataArr[0].equals("2")) {
					MainPage.time.interrupt();
					System.out.println("������");
					String winMessage = "���ź�������!";
					JOptionPane.showMessageDialog(MainPage.main, winMessage);
					player.initPlayerChessCoord();
					MainPage.contre.repaint();

					player.setState(0);
					MainPage.contre.setChickeAble(false);
					MainPage.state1.setText("״̬:�ȴ�...");
					MainPage.state2.setText("״̬:����...");
				}else if(dataArr[0].equals("3")) {	//������Ϸ��������Ϣ
					GameHall.show.append(new Date()+"  "+dataArr[1]+" ˵:\n"+dataArr[2]+"\n");
				}else if(dataArr[0].equals("4")) {	//���մ���������Ϣ
					if(GameHall.data.size()!=Integer.valueOf(dataArr[1])) {
						List<String> list=new ArrayList<>();
						list.add(dataArr[1]);
						list.add(dataArr[2]);
						list.add(dataArr[3]);
						list.add(dataArr[4]);
						GameHall.data.add(list);
					}
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		PrintWriter bw;
		try {
			bw = new PrintWriter(socket.getOutputStream(), true);
			bw.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
