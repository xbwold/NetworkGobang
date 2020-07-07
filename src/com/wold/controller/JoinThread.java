package com.wold.controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JoinThread extends Thread {
	private final static String[] columnName = { "房间号", "房主名称", "-----", "玩家名称" };
	private JTable table;
	private List<List<String>> data;
	private Object[][] object=null;
	public JoinThread(JTable table,List<List<String>> data) {
		this.data=data;
		this.table=table;
	}
	
	public void run() {
		while(true){
			try {
				this.sleep(10000);	//10秒更新一次
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(data.size()==0) {
				DefaultTableModel model = new DefaultTableModel(null, columnName);
				table.setModel(model);
			}else {
				int lenghtI = data.size();
				int lenghtJ = data.get(0).size();
				object = new Object[lenghtI][lenghtJ];
				for (int i = 0; i < lenghtI; i++) {
					List<String> list = data.get(i);
					for (int j = 0; j < lenghtJ; j++) {
						object[i][j] = list.get(j);
					}
				}		
				DefaultTableModel model = new DefaultTableModel(this.object, columnName);
				table.setModel(model);
			}
		}
	}
}
