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

public class Server extends Thread{
	private JTextArea showMessage;
	private Player player;
	private String part;
	private ServerSocket server;
	private List<Socket> listClientSocket;
	
	public Server(String part,JTextArea showMessage,Player player){
		this.part=part;
		this.showMessage=showMessage;
		this.player=player;
	}
	public static void main(String[] args) {
	
	}
	
	public void run() {
		try {
			server=new ServerSocket(Integer.valueOf(part));
			System.out.println("服务器开启");
			listClientSocket=Collections.synchronizedList(new ArrayList<>());
			while(true) {
				Socket scoktClient=server.accept();
				System.out.println(scoktClient.getInetAddress().getHostAddress() + "加入服务器"); 
				listClientSocket.add(scoktClient);
				try {
					while(true) {
						BufferedReader br=new BufferedReader(new InputStreamReader(scoktClient.getInputStream()));
						String data= br.readLine();
						String dataArr[]=data.split(":");
						System.out.println(dataArr[0]);
						if(dataArr[0].equals("0")) {
							System.out.println("坐标位置");
							MainPage.state1.setText("状态:等待...");
							MainPage.state2.setText("状态:下棋...");
							player.setState(1);
							player.setPlayerChessCoord(Integer.valueOf(dataArr[1]), Integer.valueOf(dataArr[2]),2);
							MainPage.contre.repaint();
							MainPage.contre.setChickeAble(true);
							MainPage.time=new TimeThread(MainPage.countDown);
							MainPage.time.start();
						}else if(dataArr[0].equals("1")){
							data+="\n";
							showMessage.append(data);
						}else if(dataArr[0].equals("2")) {
							//对方获得胜利，结束游戏
							MainPage.time.interrupt();
							String winMessage="很遗憾您输了!";
							JOptionPane winShow=new JOptionPane();
							winShow.showMessageDialog(null, winMessage);
							player.initPlayerChessCoord();
							MainPage.contre.repaint();
							
							player.setState(1);
							MainPage.contre.setChickeAble(true);
							MainPage.time=new TimeThread(MainPage.countDown);
							MainPage.state1.setText("状态:等待...");
							MainPage.state2.setText("状态:下棋...");
							MainPage.time.start();
							
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String message) {
		for (Socket socket : listClientSocket) {
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
