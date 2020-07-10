package com.wold.page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
		userLabel = new JLabel("用户名:"); // 创建UserJLable
		userText = new JTextField(); // 获取登录名
		passLabel = new JLabel("密码:"); // 创建PassJLable
		passText = new JPasswordField(20); //密码框隐藏
		loginButton = new JButton(bStr1); // 创建登录按钮
		registerButton = new JButton(bStr2); // 创建注册按钮
		
		
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		// 设置窗体位置及大小
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null); // 屏幕居中
		frame.add(panel); // 添加面板
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 设置点击关闭按钮
		placeComponents(panel); // 往窗体里放其他空间
		frame.setVisible(true); // 设置窗体可见
	}

	
	private void placeComponents(JPanel panel) {

		panel.setLayout(null); //设置布局为null

		
		userLabel.setBounds(30, 30, 80, 25);
		panel.add(userLabel);
		
		userText.setBounds(105, 30, 165, 25);
		panel.add(userText);

		passLabel.setBounds(30, 60, 80, 25);
		panel.add(passLabel);
		
		passText.setBounds(105, 60, 165, 25);
		panel.add(passText);

		loginButton.setBounds(25, 100, 80, 25);
		panel.add(loginButton);
		registerButton.setBounds(190, 100, 80, 25);
		panel.add(registerButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			if (loginButton.getText().equals("登录")) {
				if(userText.getText().equals("")) {
					userText.setText("用户名不能为空!");
				}else {
					User user=new User();
					user.setName(userText.getText());
					user.setPassword(new String(passText.getPassword()));
					User temp=userService.getUserByNameAndPwd(user);
					if(temp==null) {
						JOptionPane.showMessageDialog(null,"账户密码错误!","错误",JOptionPane.ERROR_MESSAGE);
					}else {
						new GameHall(userText.getText(),ip);
						frame.dispose();
						Menu.m.dispose();
					}
				}
				
			} else if (loginButton.getText().equals("注册")) {
				String name=userText.getText();
				String pwd=new String(passText.getPassword());
				if(name.equals("")||pwd.equals("")) {
					JOptionPane.showMessageDialog(null,"账户密码不能为空","警告",JOptionPane.WARNING_MESSAGE);
				}else {
					User user=new User();
					user.setName(name);
					user.setPassword(pwd);
					boolean b=userService.saveUser(user);
					if(b) {
						JOptionPane.showMessageDialog(null,"注册成功","成功",JOptionPane.WARNING_MESSAGE);
						frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null,"用户名已存在","警告",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		if (e.getSource() == registerButton) {
			if (registerButton.getText().equals("注册")) {
				new Login("注册", "注册", "返回",ip);
			} else if (registerButton.getText().equals("返回")) {
				frame.dispose();
			}
		}
	}
}
