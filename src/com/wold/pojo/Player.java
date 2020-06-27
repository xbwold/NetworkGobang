package com.wold.pojo;

import java.net.Socket;

public class Player {
	private String name;
	public static int[][] playerChessCoord = new int[16][16];
	private int role;	//角色棋子颜色,1黑2白
	private int state;	//玩家状态,0等待1下棋
	private Socket socket;	//记录玩家的socket
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[][] getPlayerChessCoord() {
		return playerChessCoord;
	}

	public void setPlayerChessCoord(int x,int y,int role) {
		this.playerChessCoord[x][y]=role;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	 public Player(int role,int state) {
		 this.role=role;
		 this.state=state;
		 initPlayerChessCoord();
	 }
	 
	 public void initPlayerChessCoord() {
		 for(int i=0;i<16;i++) {
			 for(int j=0;j<16;j++) {
				 playerChessCoord[i][j]=Chess.BLANK; 
			 }
		 }
	 }
}
