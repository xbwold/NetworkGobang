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
	private final static String[] columnName = { "�����", "��������", "-----", "�������" };
	public static List<List<String>> data = null;
	public static Object[][] object = null;

	public static MainPage mainPage;

	public GameHall(String name, String ip) {
		this.data = new ArrayList<List<String>>();
		this.name = name;
		this.ip = ip;
		init();
		clinet = new Clinet(ip); // �����ͻ����߳�
		clinet.start();
		new JoinThread(this.listRoom, this.data).start(); // ʮ�뷿��ˢ�»���
	}

	public void init() {
		this.setTitle("��Ϸ����");
		this.setSize(900, 625); // ���ô��ڴ�С
		this.setResizable(false);// ���ڴ�С���ɱ�
		this.setLayout(null);

		initLeft();
		initRight();

		this.add(left);
		this.add(right);

		create.addActionListener(this);
		refresh.addActionListener(this);
		inside.addActionListener(this);
		send.addActionListener(this);

		this.setLocationRelativeTo(null); // λ����Ļ����
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initLeft() {
		left = new JPanel();
		left.setLayout(null);
		left.setBounds(0, 0, 600, 600);
		Color c = new Color(192, 192, 192);
		left.setBackground(c);
		left.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // ���ñ߿�

		userName = new JLabel("�û���:" + name, JLabel.CENTER);
		userName.setBounds(10, 5, 150, 20);
		userName.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JSeparator sep1 = new JSeparator();
		sep1.setForeground(Color.BLACK);
		sep1.setBounds(0, 30, 600, 1);

		serverIP = new JLabel("������IP:" + ip, JLabel.CENTER);
		serverIP.setBounds(170, 5, 150, 20);
		serverIP.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		create = new JButton("��������");
		create.setBounds(150, 40, 100, 50);

		refresh = new JButton("ˢ�·���");
		refresh.setBounds(350, 40, 100, 50);

		JSeparator sep2 = new JSeparator();
		sep2.setForeground(Color.BLACK);
		sep2.setBounds(0, 100, 600, 1);

		inside = new JButton("���뷿��");
		inside.setBounds(250, 520, 100, 50);

		DefaultTableModel model = new DefaultTableModel(this.object, columnName);
		listRoom = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;// ���ñ��Ԫ���ɱ༭,����true��ʾ�ܱ༭��false��ʾ���ܱ༭
			}
		};

		// �������ݾ���
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		listRoom.setDefaultRenderer(Object.class, r);

//		listRoom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // �����п��ɸı�
//		TableColumn col1 = listRoom.getColumnModel().getColumn(0);
//		TableColumn col2 = listRoom.getColumnModel().getColumn(1);
//		TableColumn col3 = listRoom.getColumnModel().getColumn(2);
//		TableColumn col4 = listRoom.getColumnModel().getColumn(3);
//		col1.setPreferredWidth(100); // �����п�
//		col2.setPreferredWidth(200);
//		col3.setPreferredWidth(60);
//		col4.setPreferredWidth(200);

		JScrollPane scrollpane = new JScrollPane(listRoom); // ��TextArea��װ��JScrollPane��ʵ�ֹ���Ч��
		scrollpane.setBounds(10, 110, 580, 390);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// ���ô�ֱ���������ǳ���
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
		JScrollPane scrollpane = new JScrollPane(show); // ��TextArea��װ��JScrollPane��ʵ�ֹ���Ч��
		scrollpane.setBounds(10, 10, 270, 520);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// ���ô�ֱ���������ǳ���

		input = new JTextField();
		input.setBounds(10, 550, 200, 30);

		send = new JButton("����");
		send.setBounds(220, 550, 60, 30);
		right.add(scrollpane);
		right.add(input);
		right.add(send);
	}

	/**
	 * ��ȡTable��ʾ������
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
		if (e.getSource() == create) { // ��������
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
			list.add("�ȴ���Ҽ���");
			data.add(list);
			getTableData();
			DefaultTableModel model = new DefaultTableModel(this.object, columnName);
			listRoom.setModel(model);
			mainPage = new MainPage(this.name, "�ȴ���Ҽ���", clinet);// MainPage gameStart =
			this.clinet.sendMessage("4:" + number + ":" + this.name + ":" + "VS:" + "�ȴ���Ҽ���");
		} else if (e.getSource() == refresh) { // ˢ�·����б�

			getTableData();
			DefaultTableModel model = new DefaultTableModel(this.object, columnName);
			listRoom.setModel(model);

		} else if (e.getSource() == inside) { // ���뷿��
			int row = listRoom.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "��ѡ���ȷ���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			} else {
				String name = listRoom.getValueAt(row, 3).toString();
				if (name.equals("�ȴ���Ҽ���")) {
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
					JOptionPane.showMessageDialog(this, "��������", "��ʾ", JOptionPane.WARNING_MESSAGE);
				}

			}
		} else if (e.getSource() == send) {
			if (!input.getText().equals("")) { // ������Ϣ
				clinet.sendMessage("3:" + this.name + ":" + input.getText());
				input.setText("");
			}
		}
	}
}
