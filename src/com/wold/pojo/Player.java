package com.wold.pojo;

public class Player {
	private String name;
	//private int[][] myChessCoord = new int[16][16];
	public static int[][] playerChessCoord = new int[16][16];
	private int role;	//½ÇÉ«Æå×ÓÑÕÉ«,1ºÚ2°×
	private int state;	//Íæ¼Ò×´Ì¬,0µÈ´ý1ÏÂÆå
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
