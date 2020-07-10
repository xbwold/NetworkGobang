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
	// private JTextArea showMessage;

	// private Player player;

	public Clinet(String ip) {
		this.ip = ip;
		// this.showMessage = GameHall.mainPage.getShowMessage();
//		this.player = player;
	}

	public void run() {
		try {
			System.out.println("客户端开启");
			socket = new Socket(ip, 9999);
			sendMessage("5:get");
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String data = null;
				while ((data = br.readLine()) != null) {
					System.out.println("收到" + data);
					String dataArr[] = data.split(":");

					if (dataArr[0].equals("0")) { // 棋子信息
						System.out.println("坐标位置");
						GameHall.mainPage.getPlayer().setState(1);
						if (GameHall.mainPage.getPlayer().getRole() == 1) {
							GameHall.mainPage.state1.setText("状态:等待...");
							GameHall.mainPage.state2.setText("状态:下棋...");
							GameHall.mainPage.getPlayer().setPlayerChessCoord(Integer.valueOf(dataArr[1]),
									Integer.valueOf(dataArr[2]), 2);
							int win = Judge.whowin(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]),
									GameHall.mainPage.getPlayer().getPlayerChessCoord(), 2);
							String winMessage = "";
							if (win == 2) {
								GameHall.mainPage.contre.repaint();
								winMessage = "很遗憾您输了";
								JOptionPane.showMessageDialog(null, winMessage);
								GameHall.mainPage.prepare = 0;
								GameHall.mainPage.start.setText("开始游戏");
								GameHall.mainPage.start.setEnabled(true);
								GameHall.mainPage.remake.setEnabled(false);
								GameHall.mainPage.lose.setEnabled(false);
								GameHall.mainPage.quit.setEnabled(true);
								GameHall.mainPage.contre.setChickeAble(false);
								GameHall.mainPage.getPlayer().setState(0);
								GameHall.mainPage.getPlayer().initPlayerChessCoord();
							} else {
								GameHall.mainPage.remake.setEnabled(true);
								GameHall.mainPage.lose.setEnabled(true);
								GameHall.mainPage.contre.repaint();
								GameHall.mainPage.contre.setChickeAble(true);
								GameHall.mainPage.time = new TimeThread(MainPage.countDown);
								GameHall.mainPage.time.start();
								
								//记录下棋地方
								List<Integer> list=new ArrayList<>();
								list.add(Integer.valueOf(dataArr[1]));
								list.add(Integer.valueOf(dataArr[2]));
								GameHall.mainPage.getPlayer().getRecord().push(list);
							}
						} else {
							GameHall.mainPage.state2.setText("状态:等待...");
							GameHall.mainPage.state1.setText("状态:下棋...");
							GameHall.mainPage.getPlayer().setPlayerChessCoord(Integer.valueOf(dataArr[1]),
									Integer.valueOf(dataArr[2]), 1);
							int win = Judge.whowin(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]),
									GameHall.mainPage.getPlayer().getPlayerChessCoord(), 1);
							String winMessage = "";
							if (win == 1) {
								winMessage = "很遗憾您输了";
								JOptionPane.showMessageDialog(null, winMessage);
								GameHall.mainPage.prepare = 0;
								GameHall.mainPage.start.setText("开始游戏");
								GameHall.mainPage.start.setEnabled(true);
								GameHall.mainPage.remake.setEnabled(false);
								GameHall.mainPage.lose.setEnabled(false);
								GameHall.mainPage.quit.setEnabled(true);
								GameHall.mainPage.contre.setChickeAble(false);
								GameHall.mainPage.getPlayer().setState(0);
								GameHall.mainPage.getPlayer().initPlayerChessCoord();
								GameHall.mainPage.repaint();
							} else {
								GameHall.mainPage.remake.setEnabled(true);
								GameHall.mainPage.lose.setEnabled(true);
								GameHall.mainPage.contre.repaint();
								GameHall.mainPage.contre.setChickeAble(true);
								GameHall.mainPage.time = new TimeThread(MainPage.countDown);
								GameHall.mainPage.time.start();
								
								//记录下棋地方
								List<Integer> list=new ArrayList<>();
								list.add(Integer.valueOf(dataArr[1]));
								list.add(Integer.valueOf(dataArr[2]));
								GameHall.mainPage.getPlayer().getRecord().push(list);
							}
						}

					} else if (dataArr[0].equals("1")) { // 房间内信息
						String message = "";
						for (int i = 1; i < dataArr.length; i++) {
							if (i != 1) {
								message += dataArr[i];
							}
						}
						GameHall.mainPage.getShowMessage()
								.append(new Date() + "  " + dataArr[1] + " ˵:\n" + message + "\n");
					} else if (dataArr[0].equals("2")) {//有人准备
						GameHall.mainPage.prepare += 1;
						if (GameHall.mainPage.prepare >= 2) {
							if (GameHall.mainPage.getPlayer().getRole() == 1) {
								GameHall.mainPage.contre.setChickeAble(true);
								GameHall.mainPage.lose.setEnabled(true);
								GameHall.mainPage.quit.setEnabled(false);
								GameHall.mainPage.getPlayer().setState(1);
								GameHall.mainPage.time = new TimeThread(MainPage.countDown);
								GameHall.mainPage.time.start();
								GameHall.mainPage.start.setText("游戏中");
							} else {
								GameHall.mainPage.contre.setChickeAble(false);
								GameHall.mainPage.getPlayer().setState(0);
								GameHall.mainPage.start.setText("游戏中");
								GameHall.mainPage.remake.setEnabled(false);
								GameHall.mainPage.lose.setEnabled(false);
								GameHall.mainPage.quit.setEnabled(false);
							}
						}

					} else if (dataArr[0].equals("3")) { // 接受游戏大厅的信息
						GameHall.show.append(new Date() + "  " + dataArr[1] + " ˵:\n" + dataArr[2] + "\n");
					} else if (dataArr[0].equals("4")) { // 接受创建房间信息
						System.out.println("收到:" + data);
						List<String> list = new ArrayList<>();
						list.add(dataArr[1]);
						list.add(dataArr[2]);
						list.add(dataArr[3]);
						list.add(dataArr[4]);
						GameHall.data.add(list);
					} else if (dataArr[0].equals("5")) { // 通知房主有人加入
						GameHall.mainPage.setNameClinet(dataArr[1]);
						GameHall.mainPage.start.setEnabled(true);
					} else if (dataArr[0].equals("6")) { // 加入了房间
						List<String> list = GameHall.data.get(Integer.valueOf(dataArr[1]));// object[row][3]=this.name;
						list.set(3, dataArr[2]);
					}else if(dataArr[0].equals("7")) {	//悔棋 7:0 7:1 7:2
						if(dataArr[1].equals("0")) {	//悔棋申请
							int agree=JOptionPane.showConfirmDialog(null,"对方申请悔棋，是否同意?","悔棋申请",JOptionPane.YES_NO_OPTION);
							System.out.println("是否同意:"+agree);
							String message="7:";
							if(agree==1) {	//不同意悔棋
								message+="2";
							}else {	//ͬ同意悔棋
								message+="1";
								for(int i=0;i<2;i++) {
									List list=GameHall.mainPage.getPlayer().getRecord().pop();
									GameHall.mainPage.getPlayer().setPlayerChessCoord((int)list.get(0), (int)list.get(1), 0);
								}
							}
							sendMessage(message);
						}else if(dataArr[1].equals("1")){	//收到同意
							JOptionPane.showMessageDialog(null, "对方同意悔棋", "悔棋提示",JOptionPane.INFORMATION_MESSAGE);
							for(int i=0;i<2;i++) {
								List list=GameHall.mainPage.getPlayer().getRecord().pop();
								GameHall.mainPage.getPlayer().setPlayerChessCoord((int)list.get(0), (int)list.get(1), 0);
							}
							GameHall.mainPage.contre.setChickeAble(true);
							GameHall.mainPage.remake.setText("悔棋");
						}else if(dataArr[1].equals("2")) {	//收到不同意
							JOptionPane.showMessageDialog(null, "对方不同意悔棋", "悔棋提示",JOptionPane.INFORMATION_MESSAGE);
							GameHall.mainPage.contre.setChickeAble(true);
							GameHall.mainPage.remake.setText("悔棋");
						}
					}else if(dataArr[0].equals("8")) {
						JOptionPane.showMessageDialog(null, "恭喜您获得胜利!");
						GameHall.mainPage.prepare = 0;
						GameHall.mainPage.start.setText("开始游戏");
						GameHall.mainPage.start.setEnabled(true);
						GameHall.mainPage.remake.setEnabled(false);
						GameHall.mainPage.lose.setEnabled(false);
						GameHall.mainPage.quit.setEnabled(true);
						GameHall.mainPage.contre.setChickeAble(false);
						GameHall.mainPage.getPlayer().setState(0);
						GameHall.mainPage.getPlayer().initPlayerChessCoord();
						GameHall.mainPage.repaint();
					}else if(dataArr[0].equals("9")) {
						int number=Integer.valueOf(dataArr[2]);
						if(dataArr[1].equals("1")) { //房主退出，解散房间
							GameHall.data.remove(number);
							System.out.println("解散一个房间后大小:"+GameHall.data.size());
						}else if(dataArr[1].equals("2")){ //玩家退出，等待玩家加入
							
							List<String> list = GameHall.data.get(number);
							list.set(3, "等待玩家加入");
							GameHall.data.set(number, list);
						}else if(dataArr[1].equals("0")) {	//放回游戏大厅，开启大厅加入。创建按钮
							GameHall.create.setEnabled(true);
							GameHall.inside.setEnabled(true);
							if(dataArr[2].equals("1")) {	//房主离开，房间已解散 此处的dataArr[2]不是房间号，表示是否为所在房间已解散
								JOptionPane.showMessageDialog(null, "房主离开，房间已解散");
								GameHall.mainPage.dispose();
							}
						}
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
