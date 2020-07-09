package com.wold.page;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.wold.controller.JoinThread;
import com.wold.net.Clinet;

public class GameHall extends JFrame implements ActionListener {
	private String name;
	private String ip;
	private JPanel left;
	private JLabel userName;
	private JLabel serverIP;
	public static JButton create;
	private JButton refresh;
	public static JButton inside;

	private JPanel right;
	public static JTextArea show;
	private JTextField input;
	private JButton send;
	private Clinet clinet;

	private JTable listRoom;
	private final static String[] columnName = { "房间号", "房主名称", "-----", "玩家名称" };
	public static List<List<String>> data = null;
	public static Object[][] object = null;

	public static MainPage mainPage;

	public GameHall(String name, String ip) {
		this.data = new ArrayList<List<String>>();
		this.name = name;
		this.ip = ip;
		init();
		clinet = new Clinet(ip); // 开启客户端线程
		clinet.start();
		new JoinThread(this.listRoom, this.data).start(); // 十秒房间刷新机制
	}

	public void init() {
		this.setTitle("游戏大厅");
		this.setSize(900, 625); // 设置窗口大小
		this.setResizable(false);// 窗口大小不可变
		this.setLayout(null);

		initLeft();
		initRight();

		this.add(left);
		this.add(right);

		create.addActionListener(this);
		refresh.addActionListener(this);
		inside.addActionListener(this);
		send.addActionListener(this);

		this.setLocationRelativeTo(null); // 位于屏幕居中
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initLeft() {
		left = new JPanel();
		left.setLayout(null);
		left.setBounds(0, 0, 600, 600);
		Color c = new Color(192, 192, 192);
		left.setBackground(c);
		left.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 设置边框

		userName = new JLabel("用户名:" + name, JLabel.CENTER);
		userName.setBounds(10, 5, 150, 20);
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBounds(0, 30, 600, 1);

		serverIP = new JLabel("服务器IP:" + ip, JLabel.CENTER);
		serverIP.setBounds(170, 5, 150, 20);
		serverIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		create = new JButton("创建房间");
		create.setBounds(150, 40, 100, 50);

		refresh = new JButton("刷新房间");
		refresh.setBounds(350, 40, 100, 50);

		JSeparator sep2 = new JSeparator();
		sep2.setForeground(Color.BLACK);
		sep2.setBounds(0, 100, 600, 1);

		inside = new JButton("加入房间");
		inside.setBounds(250, 520, 100, 50);

		DefaultTableModel model = new DefaultTableModel(this.object, columnName);
		listRoom = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;// 设置表格单元不可编辑,返回true表示能编辑，false表示不能编辑
			}
		};

		// 设置内容居中
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		listRoom.setDefaultRenderer(Object.class, r);

//		listRoom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 设置列宽不可改变
//		TableColumn col1 = listRoom.getColumnModel().getColumn(0);
//		TableColumn col2 = listRoom.getColumnModel().getColumn(1);
//		TableColumn col3 = listRoom.getColumnModel().getColumn(2);
//		TableColumn col4 = listRoom.getColumnModel().getColumn(3);
//		col1.setPreferredWidth(100); // 设置列宽
//		col2.setPreferredWidth(200);
//		col3.setPreferredWidth(60);
//		col4.setPreferredWidth(200);

		JScrollPane scrollpane = new JScrollPane(listRoom); // 将TextArea包装到JScrollPane中实现滚轮效果
		scrollpane.setBounds(10, 110, 580, 390);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// 设置垂直滚动条总是出现
//		if(data!=null) {
//			
//		}else {
//			
//		}

		left.add(userName);
		left.add(sep1);
		left.add(serverIP);
		left.add(create);
		left.add(refresh);
		left.add(sep2);
		left.add(scrollpane);
		left.add(inside);
	}

	public void initRight() {
		right = new JPanel();
		right.setLayout(null);
		right.setBounds(600, 0, 300, 600);
		Color c = new Color(65, 105, 225);
		right.setBackground(c);
		//right.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		show = new JTextArea();

		show.setEditable(false);
		show.setLineWrap(true);
		JScrollPane scrollpane = new JScrollPane(show); // 将TextArea包装到JScrollPane中实现滚轮效果
		scrollpane.setBounds(10, 10, 270, 520);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// 设置垂直滚动条总是出现

		input = new JTextField();
		input.setBounds(10, 550, 200, 30);

		send = new JButton("发送");
		send.setBounds(220, 550, 60, 30);
		right.add(scrollpane);
		right.add(input);
		right.add(send);
	}

	/**
	 * 获取Table显示的数据
	 * 
	 * @param data
	 * @param list
	 * @return
	 */
	public void getTableData() {
		int lenghtI = data.size();
		if (lenghtI == 0) {
			object = null;
		} else {
			int lenghtJ = data.get(0).size();
			object = new Object[lenghtI][lenghtJ];
			for (int i = 0; i < lenghtI; i++) {
				List<String> list = data.get(i);
				for (int j = 0; j < lenghtJ; j++) {
					object[i][j] = list.get(j);
				}
			}
		}
	}

//	public static void main(String[] args) {
//		new GameHall("wold", "127.0.0.1");
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == create) { // 创建房间
			create.setEnabled(false);
			inside.setEnabled(false);
			int number = 0;
			if (data.size() == 0) {
				this.object = new Object[1][1];
				number = 1;
			} else {
				number = data.size() + 1;
			}
			List<String> list = new ArrayList<>();
			list.add(String.valueOf(number));
			list.add(this.name);
			list.add("VS");
			list.add("等待玩家加入");
			data.add(list);
			getTableData();
			DefaultTableModel model = new DefaultTableModel(this.object, columnName);
			listRoom.setModel(model);
			mainPage = new MainPage(this.name, "等待玩家加入", clinet);// MainPage gameStart =
			this.clinet.sendMessage("4:" + number + ":" + this.name + ":" + "VS:" + "等待玩家加入");
		} else if (e.getSource() == refresh) { // 刷新房间列表

			getTableData();
			DefaultTableModel model = new DefaultTableModel(this.object, columnName);
			listRoom.setModel(model);

		} else if (e.getSource() == inside) { // 加入房间
			int row = listRoom.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "请选择先房间", "提示", JOptionPane.WARNING_MESSAGE);
			} else {
				String name = listRoom.getValueAt(row, 3).toString();
				if (name.equals("等待玩家加入")) {
					List<String> list = data.get(row);// object[row][3]=this.name;
					list.set(3, this.name);
					String message = "6:" + row + ":" + this.name;
					this.clinet.sendMessage(message);
					getTableData();
					DefaultTableModel model = new DefaultTableModel(this.object, columnName);
					listRoom.setModel(model);
					create.setEnabled(false);
					inside.setEnabled(false);
					mainPage = new MainPage((String) object[row][1], this.name, clinet);
				} else {
					JOptionPane.showMessageDialog(this, "房间已满", "提示", JOptionPane.WARNING_MESSAGE);
				}

			}
		} else if (e.getSource() == send) {
			if (!input.getText().equals("")) { // 发送消息
				clinet.sendMessage("3:" + this.name + ":" + input.getText());
				input.setText("");
			}
		}
	}
}
