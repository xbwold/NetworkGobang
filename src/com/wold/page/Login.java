package com.wold.page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wold.net.Clinet;
import com.wold.pojo.User;
import com.wold.service.UserService;

public class Login extends JFrame implements ActionListener {
	private UserService userService;
	
	private JFrame frame;
	private JPanel panel;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel passLabel;
	private JPasswordField passText;
	private JButton loginButton;
	private JButton registerButton;
	private String ip;

	public Login(String title, String bStr1, String bStr2,String ip) {
		
		ApplicationContext applicationContext= new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		userService=applicationContext.getBean(UserService.class);
		
		this.ip=ip;
		frame = new JFrame(title);
		panel = new JPanel();
		userLabel = new JLabel("�û���:"); // ����UserJLabel
		userText = new JTextField(); // ��ȡ��¼��
		passLabel = new JLabel("����:"); // ����PassJLabel
		passText = new JPasswordField(20); // ���������
		loginButton = new JButton(bStr1); // ������¼��ť
		registerButton = new JButton(bStr2); // ����ע�ᰴť
		
		
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		// ���ô����λ�ü���С
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null); // ����Ļ�о�����ʾ
		frame.add(panel); // ������
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ����X�ź�ر�
		placeComponents(panel); // ��������������ؼ�
		frame.setVisible(true); // ���ô���ɼ�
	}

	/**
	 * �����岼��
	 * 
	 * @param panel
	 */
	private void placeComponents(JPanel panel) {

		panel.setLayout(null); // ���ò���Ϊ null

		// ���� UserJLabel
		userLabel.setBounds(30, 30, 80, 25);
		panel.add(userLabel);
		// �����ı��������û�����
		userText.setBounds(105, 30, 165, 25);
		panel.add(userText);

		// ����PassJLabel
		passLabel.setBounds(30, 60, 80, 25);
		panel.add(passLabel);
		// ��������� ����
		passText.setBounds(105, 60, 165, 25);
		panel.add(passText);

		// ������¼��ť
		loginButton.setBounds(25, 100, 80, 25);
		panel.add(loginButton);
		registerButton.setBounds(190, 100, 80, 25);
		panel.add(registerButton);
	}

//	public static void main(String[] args) {
//		new Login("��¼","��¼","ע��","127.0.0.1");
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			if (loginButton.getText().equals("��¼")) {
				if(userText.getText().equals("")) {
					userText.setText("�û�������Ϊ��!");
				}else {
					User user=new User();
					user.setName(userText.getText());
					user.setPassword(new String(passText.getPassword()));
					User temp=userService.getUserByNameAndPwd(user);
					if(temp==null) {
						JOptionPane.showMessageDialog(null,"�˻��������!","����",JOptionPane.ERROR_MESSAGE);
					}else {
						new GameHall(userText.getText(),ip);
						frame.dispose();
						Menu.m.dispose();
					}
				}
				
			} else if (loginButton.getText().equals("ע��")) {
				String name=userText.getText();
				String pwd=new String(passText.getPassword());
				if(name.equals("")||pwd.equals("")) {
					JOptionPane.showMessageDialog(null,"�˻����벻��Ϊ��","����",JOptionPane.WARNING_MESSAGE);
				}else {
					User user=new User();
					user.setName(name);
					user.setPassword(pwd);
					boolean b=userService.saveUser(user);
					if(b) {
						JOptionPane.showMessageDialog(null,"ע��ɹ�","�ɹ�",JOptionPane.WARNING_MESSAGE);
						frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null,"�û����Ѵ���","����",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		if (e.getSource() == registerButton) {
			if (registerButton.getText().equals("ע��")) {
				new Login("ע��", "ע��", "����",ip);
			} else if (registerButton.getText().equals("����")) {
				frame.dispose();
			}
		}
	}
}
