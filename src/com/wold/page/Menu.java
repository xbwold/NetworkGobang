package com.wold.page;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Menu extends JFrame implements MouseListener{
	public static Menu m;
	private ImageIcon mune;
	private JLabel mJLabel;//������ʾͼƬ
	private int x;
	private int y;
	
	public Menu() {
		mune=new ImageIcon("src/image/menu.png");
		init();
		startListener();
	}
	
	public void init() {
		this.setTitle("����������");
		this.setSize(420, 625); // ���ô��ڴ�С
		this.setResizable(false);// ���ڴ�С���ɱ�
		
		mJLabel=new JLabel(mune);
		
		this.add(mJLabel);
		
		this.setLocationRelativeTo(null);	//λ����Ļ����
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startListener() {
		mJLabel.addMouseListener(this);
		
	}
	public static void main(String[] args) {
		m=new Menu();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x=e.getX();
		int y=e.getY();
		if(x>120&&x<300&&y>220&&y<470) {
			if(y>220&&y<280) {
				String ip=JOptionPane.showInputDialog(null,"�����������ip","���ӷ�����",JOptionPane.PLAIN_MESSAGE);
				Pattern pattern=Pattern.compile("^(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}$");	//�����ж�ip��ʽ
				Matcher mather=pattern.matcher(ip);
				if(!ip.equals("")&&mather.find()) {
					new Login("��¼","��¼","ע��",ip);
				}else {
					JOptionPane.showMessageDialog(null,"����IP��ʽ����","����",JOptionPane.ERROR_MESSAGE);
				}
				
				//this.dispose();
			}
			if(y>285&&y<340) {
				JOptionPane.showMessageDialog(this, "���ܴ�����!");
			}
			if(y>345&&y<410) {
				System.out.println(e.getX()+","+e.getY()+"����");
			}
			if(y>420&&y<470) {
				JOptionPane.showMessageDialog(this, "1.0.0�汾   ����:wold","����",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
