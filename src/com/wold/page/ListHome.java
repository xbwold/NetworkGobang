package com.wold.page;

import javax.swing.*;

public class ListHome extends JPanel {
	private JLabel number;
	private JLabel homeMainName;
	private JLabel vs;
	private JLabel otherName;
	private JButton inside;
	
	/**
	 * 
	 * @param number 房间号
	 * @param nameMain 房主名称
	 */
	public ListHome(int number,String nameMain) {
		this.number=new JLabel(String.valueOf(number));
		homeMainName=new JLabel();
		vs=new JLabel("vs");
		otherName=new JLabel("等待玩家加入");
		inside=new JButton("加入房间");
		
		this.setSize(400,50);
	}
	
	
}
