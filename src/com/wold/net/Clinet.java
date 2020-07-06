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
			System.out.println("�ͻ��˿���");
			socket = new Socket(ip, 9999);
			sendMessage("5:get");
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String data = null;
				while ((data = br.readLine()) != null) {
					System.out.println("�յ���" + data);
					String dataArr[] = data.split(":");

					if (dataArr[0].equals("0")) { // ������Ϣ
						System.out.println("����λ��");
						GameHall.mainPage.getPlayer().setState(1);
						if (GameHall.mainPage.getPlayer().getRole() == 1) {
							GameHall.mainPage.state1.setText("״̬:�ȴ�...");
							GameHall.mainPage.state2.setText("״̬:����...");
							GameHall.mainPage.getPlayer().setPlayerChessCoord(Integer.valueOf(dataArr[1]),
									Integer.valueOf(dataArr[2]), 2);
							int win = Judge.whowin(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]),
									GameHall.mainPage.getPlayer().getPlayerChessCoord(), 2);
							String winMessage = "";
							if (win == 2) {
								GameHall.mainPage.contre.repaint();
								winMessage = "���ź������ˣ�";
								JOptionPane.showMessageDialog(null, winMessage);
								GameHall.mainPage.prepare = 0;
								GameHall.mainPage.start.setText("��ʼ��Ϸ");
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
								
								//��¼����ط�
								List<Integer> list=new ArrayList<>();
								list.add(Integer.valueOf(dataArr[1]));
								list.add(Integer.valueOf(dataArr[2]));
								GameHall.mainPage.getPlayer().getRecord().push(list);
							}
						} else {
							GameHall.mainPage.state2.setText("״̬:�ȴ�...");
							GameHall.mainPage.state1.setText("״̬:����...");
							GameHall.mainPage.getPlayer().setPlayerChessCoord(Integer.valueOf(dataArr[1]),
									Integer.valueOf(dataArr[2]), 1);
							int win = Judge.whowin(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]),
									GameHall.mainPage.getPlayer().getPlayerChessCoord(), 1);
							String winMessage = "";
							if (win == 1) {
								winMessage = "���ź������ˣ�";
								JOptionPane.showMessageDialog(null, winMessage);
								GameHall.mainPage.prepare = 0;
								GameHall.mainPage.start.setText("��ʼ��Ϸ");
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
								
								//��¼����ط�
								List<Integer> list=new ArrayList<>();
								list.add(Integer.valueOf(dataArr[1]));
								list.add(Integer.valueOf(dataArr[2]));
								GameHall.mainPage.getPlayer().getRecord().push(list);
							}
						}

					} else if (dataArr[0].equals("1")) { // ��������Ϣ
						String message = "";
						for (int i = 1; i < dataArr.length; i++) {
							if (i != 1) {
								message += dataArr[i];
							}
						}
						GameHall.mainPage.getShowMessage()
								.append(new Date() + "  " + dataArr[1] + " ˵:\n" + message + "\n");
					} else if (dataArr[0].equals("2")) {// ����׼��
						GameHall.mainPage.prepare += 1;
						if (GameHall.mainPage.prepare >= 2) {
							if (GameHall.mainPage.getPlayer().getRole() == 1) {
								GameHall.mainPage.contre.setChickeAble(true);
								GameHall.mainPage.lose.setEnabled(true);
								GameHall.mainPage.quit.setEnabled(false);
								GameHall.mainPage.getPlayer().setState(1);
								GameHall.mainPage.time = new TimeThread(MainPage.countDown);
								GameHall.mainPage.time.start();
								GameHall.mainPage.start.setText("��Ϸ��");
							} else {
								GameHall.mainPage.contre.setChickeAble(false);
								GameHall.mainPage.getPlayer().setState(0);
								GameHall.mainPage.start.setText("��Ϸ��");
								GameHall.mainPage.remake.setEnabled(false);
								GameHall.mainPage.lose.setEnabled(false);
								GameHall.mainPage.quit.setEnabled(false);
							}
						}

					} else if (dataArr[0].equals("3")) { // ������Ϸ��������Ϣ
						GameHall.show.append(new Date() + "  " + dataArr[1] + " ˵:\n" + dataArr[2] + "\n");
					} else if (dataArr[0].equals("4")) { // ���մ���������Ϣ
						System.out.println("�յ�:" + data);
						List<String> list = new ArrayList<>();
						list.add(dataArr[1]);
						list.add(dataArr[2]);
						list.add(dataArr[3]);
						list.add(dataArr[4]);
						GameHall.data.add(list);
					} else if (dataArr[0].equals("5")) { // ֪ͨ�������˼���
						GameHall.mainPage.setNameClinet(dataArr[1]);
						GameHall.mainPage.start.setEnabled(true);
					} else if (dataArr[0].equals("6")) { // �����˷���
						List<String> list = GameHall.data.get(Integer.valueOf(dataArr[1]));// object[row][3]=this.name;
						list.set(3, dataArr[2]);
					}else if(dataArr[0].equals("7")) {	//���� 7:0 7:1 7:2
						if(dataArr[1].equals("0")) {	//��������
							int agree=JOptionPane.showConfirmDialog(null,"�Է��������,�Ƿ�ͬ��","��������",JOptionPane.YES_NO_OPTION);
							System.out.println("�Ƿ�ͬ��:"+agree);
							String message="7:";
							if(agree==1) {	//��ͬ�����
								message+="2";
							}else {	//ͬ�����
								message+="1";
								for(int i=0;i<2;i++) {
									List list=GameHall.mainPage.getPlayer().getRecord().pop();
									GameHall.mainPage.getPlayer().setPlayerChessCoord((int)list.get(0), (int)list.get(1), 0);
								}
							}
							sendMessage(message);
						}else if(dataArr[1].equals("1")){	//�յ�ͬ��
							JOptionPane.showMessageDialog(null, "�Է�ͬ�����", "������ʾ",JOptionPane.INFORMATION_MESSAGE);
							for(int i=0;i<2;i++) {
								List list=GameHall.mainPage.getPlayer().getRecord().pop();
								GameHall.mainPage.getPlayer().setPlayerChessCoord((int)list.get(0), (int)list.get(1), 0);
							}
							GameHall.mainPage.contre.setChickeAble(true);
							GameHall.mainPage.remake.setText("����");
						}else if(dataArr[1].equals("2")) {	//�յ���ͬ��
							JOptionPane.showMessageDialog(null, "�Է���ͬ�����", "������ʾ",JOptionPane.INFORMATION_MESSAGE);
							GameHall.mainPage.contre.setChickeAble(true);
							GameHall.mainPage.remake.setText("����");
						}
					}else if(dataArr[0].equals("8")) {
						JOptionPane.showMessageDialog(null, "��ϲ�����ʤ��!");
						GameHall.mainPage.prepare = 0;
						GameHall.mainPage.start.setText("��ʼ��Ϸ");
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
						if(dataArr[1].equals("1")) { //�����˳�����ɢ����
							GameHall.data.remove(number);
							System.out.println("��ɢһ��������С:"+GameHall.data.size());
						}else if(dataArr[1].equals("2")){ //����˳����ȴ���Ҽ���
							
							List<String> list = GameHall.data.get(number);
							list.set(3, "�ȴ���Ҽ���");
							GameHall.data.set(number, list);
						}else if(dataArr[1].equals("0")) {	//������Ϸ�����������������롢�������䰴ť
							GameHall.create.setEnabled(true);
							GameHall.inside.setEnabled(true);
							if(dataArr[2].equals("1")) {	//�����뿪�������ѽ�ɢ �˴���dataArr[2]���Ƿ���ţ���ʾ�Ƿ�Ϊ���ڷ����ѽ�ɢ
								JOptionPane.showMessageDialog(null, "�����뿪�������ѽ�ɢ");
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
