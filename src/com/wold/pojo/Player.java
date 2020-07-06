package com.wold.pojo;

import java.net.Socket;
import java.util.List;

public class Player {
	private String name;
	public static int[][] playerChessCoord = new int[16][16];	//棋盘
	private int role;	//角色棋子颜色,1黑2白
	private int state;	//玩家状态,0等待1下棋
	private RecordStack<List<Integer>> record;

	public RecordStack<List<Integer>> getRecord() {
		return record;
	}

	public void setRecord(RecordStack<List<Integer>> record) {
		this.record = record;
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
	/**
	 * 修改棋盘上棋子
	 * @param x 
	 * @param y
	 * @param role 黑白棋
	 */
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
		 record=new RecordStack<>();
		 initPlayerChessCoord();
	 }
	 /**
	  * 初始化棋盘数组
	  */
	 public void initPlayerChessCoord() {
		 for(int i=0;i<16;i++) {
			 for(int j=0;j<16;j++) {
				 playerChessCoord[i][j]=Chess.BLANK; 
			 }
		 }
	 }
}
