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
	 * @param number �����
	 * @param nameMain ��������
	 */
	public ListHome(int number,String nameMain) {
		this.number=new JLabel(String.valueOf(number));
		homeMainName=new JLabel();
		vs=new JLabel("vs");
		otherName=new JLabel("�ȴ���Ҽ���");
		inside=new JButton("���뷿��");
		
		this.setSize(400,50);
	}
	
	
}
