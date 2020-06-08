package com.wold.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.wold.controller.Judge;
import com.wold.controller.TimeThread;
import com.wold.page.MainPage;
import com.wold.pojo.Player;

public class Clinet extends Thread {
	private String ip;
	private Socket socket;
	private JTextArea showMessage;

	private Player player;

	public Clinet(String ip,JTextArea showMessage, Player player) {
		this.ip=ip;
		this.showMessage = showMessage;
		this.player = player;
	}

	public void run() {
		try {
			System.out.println("客户端开启");
			socket = new Socket(ip, 9999);
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String data = br.readLine();
				String dataArr[] = data.split(":");
				if (dataArr[0].equals("0")) {
					System.out.println("坐标位置");
					MainPage.state1.setText("状态:下棋...");
					MainPage.state2.setText("状态:等待...");
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
					System.out.println("结束了");
					String winMessage = "很遗憾您输了!";
					JOptionPane winShow = new JOptionPane();
					winShow.showMessageDialog(MainPage.main, winMessage);
					player.initPlayerChessCoord();
					MainPage.contre.repaint();

					player.setState(0);
					MainPage.contre.setChickeAble(false);
					MainPage.state1.setText("状态:等待...");
					MainPage.state2.setText("状态:下棋...");
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
