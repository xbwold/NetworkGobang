package com.wold.pojo;

import java.net.Socket;
import java.util.List;

public class Player {
	private String name;
	public static int[][] playerChessCoord = new int[16][16];	//����
	private int role;	//��ɫ������ɫ,1��2��
	private int state;	//���״̬,0�ȴ�1����
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
	 * �޸�����������
	 * @param x 
	 * @param y
	 * @param role �ڰ���
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
	  * ��ʼ����������
	  */
	 public void initPlayerChessCoord() {
		 for(int i=0;i<16;i++) {
			 for(int j=0;j<16;j++) {
				 playerChessCoord[i][j]=Chess.BLANK; 
			 }
		 }
	 }
}
