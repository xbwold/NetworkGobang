package com.wold.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.wold.controller.Judge;
import com.wold.controller.TimeThread;
import com.wold.net.Clinet;
import com.wold.net.Server;
import com.wold.pojo.Board;
import com.wold.pojo.Chess;
import com.wold.pojo.Player;

@SuppressWarnings("serial")
public class MainPage extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
	//public static MainPage main;
	private JPanel left;
	public static ChessBoard contre;
	private JPanel right;
	//private JTextField ip;
	public JButton start;
	public JButton remake;	//悔棋以及认输都需要到自己下棋才能操作
	public JButton lose;
	public JButton quit;
	private JTextArea showMessage;
	public JTextArea getShowMessage() {
		return showMessage;
	}
	private JTextField input;
	private JButton send;

	private Clinet clinet ;
	private Boolean isServer = null;
	private JLabel nameServer;
	private JLabel nameClinet;
	public void setNameClinet(String name) {
		nameClinet.setText(name);
	}
	public static JLabel countDown;
	public static JLabel state1;
	public static JLabel state2;
	
	private Player player;
	
	public Player getPlayer() {
		return player;
	}
	
	public static TimeThread time;//倒计时
	
	public int prepare;	//房间中准备的人数
	public MainPage(String mainName,String clinetName,Clinet clinet) {
		prepare=0;
		nameServer = new JLabel(mainName, JLabel.CENTER);
		nameClinet = new JLabel(clinetName, JLabel.CENTER);
		this.clinet=clinet;
		init();
		if("等待玩家加入".equals(clinetName)) {//房主
			player=new Player(1,0);
			start.setEnabled(false);
		}else {
			player=new Player(2,0);
		}
		
	}

//	public static void main(String[] args) {
//		new MainPage("1","1",null);
//	}

	public void init() {
		this.setTitle("网络五子棋");
		this.setSize(1400, 800); // 设置窗口大小
		this.setResizable(false);// 窗口大小不可变
		this.setLayout(null); // 设置JFrame布局为null

		initLeftJPane();
		initContreJPane();
		initRightJPane();
		this.add(left);
		this.add(contre);
		this.add(right);

		startMonitor();

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 获取屏幕大小
//		int centerX = screenSize.width / 2;
//		int centerY = screenSize.height / 2;
//		this.setLocation(centerX - 1400 / 2, centerY - 800 / 2);// 屏幕居中显示
		this.setLocationRelativeTo(null);	//位于屏幕居中
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 页面左部初始化
	 */
	private void initLeftJPane() {
		left = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		fl.setAlignment(FlowLayout.CENTER);
		left.setLayout(fl);
		left.setSize(230, 800);
		left.setLocation(0, 0);
		Color c = new Color(192, 192, 192);
		left.setBackground(c);
		left.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));//设置边框
		
		ImageIcon headImage1 = new ImageIcon("src/image/head1.jpg");
		headImage1.setImage(headImage1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel head1 = new JLabel(headImage1);
		head1.setPreferredSize(new Dimension(100, 100));// 设置按钮大小
		head1.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon headImage2 = new ImageIcon("src/image/head2.jpg");
		headImage2.setImage(headImage2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel head2 = new JLabel(headImage2);
		head2.setPreferredSize(new Dimension(100, 100));// 设置按钮大小
		head2.setBorder(BorderFactory.createLineBorder(Color.black));
		//名字标签属性
		nameServer.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		nameServer.setBorder(BorderFactory.createLineBorder(Color.black));
		
		nameClinet.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		nameClinet.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel win1 = new JLabel("胜率:0%", JLabel.CENTER);
		win1.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		win1.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel win2 = new JLabel("胜率:0%", JLabel.CENTER);
		win2.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		win2.setBorder(BorderFactory.createLineBorder(Color.black));
		state1 = new JLabel("状态:等待...", JLabel.CENTER);
		state1.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		state1.setBorder(BorderFactory.createLineBorder(Color.black));
		state2 = new JLabel("状态:下棋...", JLabel.CENTER);
		state2.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		state2.setBorder(BorderFactory.createLineBorder(Color.black));
		countDown = new JLabel("倒计时:30s", JLabel.CENTER);
		countDown.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		countDown.setBorder(BorderFactory.createLineBorder(Color.black));

		left.add(head1);
		left.add(nameClinet);
		left.add(win1);
		left.add(state1);
		left.add(countDown);
		left.add(head2);
		left.add(nameServer);
		left.add(win2);
		left.add(state2);

	}

	/**
	 * 页面中间JPane布局初始化
	 */
	private void initContreJPane() {
		contre = new ChessBoard();
		contre.setSize(840, 800);
		contre.setLocation(230, 0);

	}

	/**
	 * 页面右部布局初始化
	 */

	private void initRightJPane() {
		right = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		right.setLayout(fl);
		right.setSize(330, 800);
		right.setLocation(1070, 0);
		Color c = new Color(65, 105, 225);
		right.setBackground(c);	
		right.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));//设置边框
		
		start = new JButton("开始游戏");
		start.setPreferredSize(new Dimension(200, 50));// 设置按钮大小
		start.setFont(new Font("宋体", Font.BOLD, 20));
		remake = new JButton("悔棋");
		remake.setPreferredSize(new Dimension(200, 50));
		remake.setFont(new Font("宋体", Font.BOLD, 20));
		lose = new JButton("认输");
		lose.setPreferredSize(new Dimension(200, 50));
		lose.setFont(new Font("宋体", Font.BOLD, 20));
		quit = new JButton("返回");
		quit.setPreferredSize(new Dimension(200, 50));
		quit.setFont(new Font("宋体", Font.BOLD, 20));
		showMessage = new JTextArea(23, 25);
		showMessage.setEditable(false);
		showMessage.setLineWrap(true);
		JScrollPane scrollpane = new JScrollPane(showMessage); // 将TextArea包装到JScrollPane中实现滚轮效果
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// 设置垂直滚动条总是出现

		input = new JTextField(20);
		send = new JButton("发送");
		start.setSize(20, 20);
		right.add(start);
		right.add(remake);
		right.add(lose);
		right.add(quit);
		right.add(scrollpane);
		right.add(input);
		right.add(send);
	}

	/**
	 * 开启组件的事件监听
	 */
	private void startMonitor() {
		start.addActionListener(this);
		remake.addActionListener(this);
		lose.addActionListener(this);
		quit.addActionListener(this);
		send.addActionListener(this);

		contre.addMouseListener(this);
		contre.addMouseMotionListener(this);
		send.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {	//准备游戏
			prepare+=1;
			start.setEnabled(false);
			String message="2:"+"start";
			clinet.sendMessage(message);
			if(prepare>=2) {
				if(player.getRole()==1) {
					contre.setChickeAble(true);
					lose.setEnabled(true);
					quit.setEnabled(false);
					player.setState(1);
					time=new TimeThread(countDown);
					time.start();
					start.setText("游戏中");
				}else {
					contre.setChickeAble(false);
					player.setState(0);
					start.setText("游戏中");
					remake.setEnabled(false);
					lose.setEnabled(false);
				}
			}else {
				start.setText("等待对手准备");
			}

		} else if (e.getSource() == remake) {	//悔棋
			if(player.getRecord().getSize()>=2) {
				String message="7:0";	//发送悔棋请求
				clinet.sendMessage(message);
				contre.setChickeAble(false);
				remake.setEnabled(false);
				remake.setText("等待对方回复");
				//List list=player.getRecord().pop();
				//System.out.println("悔棋:"+list.get(0)+","+list.get(1));
			}else {
				JOptionPane.showMessageDialog(this, "步数不够无法悔棋", "悔棋提示",JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == lose) {	//认输
			time.interrupt();//结束倒计时
			String message="8:0";
			clinet.sendMessage(message);
			
			prepare=0;
			start.setText("开始游戏");
			start.setEnabled(true);
			remake.setEnabled(false);
			lose.setEnabled(false);
			quit.setEnabled(true);
			contre.setChickeAble(false);
			player.setState(0);
			player.initPlayerChessCoord();		
			repaint();
			
			JOptionPane.showMessageDialog(this, "很遗憾您输了！");
			
		} else if (e.getSource() == quit) {	//退出房间	9:role
			System.out.println("返回");
			int role=player.getRole();
			String message="9:"+role;
			this.dispose();	//关闭窗口
			clinet.sendMessage(message);
		} else if (e.getSource() == send) {	//发送信息
			if (!"等待玩家加入".equals(nameClinet.getText())) {
				String message = input.getText();
				String name="";
				if (!message.equals("")) {
					if(player.getRole()==1) {
						name =nameServer.getText();
					}else {
						name =nameClinet.getText();
					}
					showMessage.append(new Date()+"  "+name+" 说:\n"+message+"\n");
					message="1:"+name+":"+message;
					input.setText("");
					clinet.sendMessage(message);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == contre) {
			if (player.getState() == 1) {
				int x = e.getX();
				int y = e.getY();
				int px=0, py=0;
				if (x < 36) {
					px = 0;
				} else if (x > 798) {
					px = 15;
				} else {
					for (int i = 0; i < 16; i++) {
						if (Board.board[0][i] <= x && Board.board[0][i + 1] >= x) {
							if (x - Board.board[0][i] >= Board.board[0][i + 1] - x) {
								px = i + 1;
							} else {
								px = i;
							}
							break;
						}
					}
				}

				if (y < 24) {
					py = 0;
				} else if (y > 705) {
					py = 15;
				} else {
					for (int i = 0; i < 16; i++) {
						if (Board.board[1][i] <= y && Board.board[1][i + 1] >= y) {
							if (y - Board.board[1][i] >= Board.board[1][i + 1] - y) {
								py = i + 1;
							} else {
								py = i;
							}
							break;
						}
					}
				}
				//判断点击地方是否有棋子
				if (player.getPlayerChessCoord()[px][py] == Chess.BLANK) {
					player.setPlayerChessCoord(px, py,player.getRole());
					repaint();
					player.setState(0);
					if(player.getRole()==1) {
						state2.setText("状态:等待...");
						state1.setText("状态:下棋...");
					}else {
						state1.setText("状态:等待...");
						state2.setText("状态:下棋...");
					}
					time.interrupt();//结束倒计时
					
					String message="0:"+px+":"+py;
					clinet.sendMessage(message);	//发送信息到服务器
					
					//判断是否结束游戏
					int win=Judge.whowin(px, py, player.getPlayerChessCoord(), player.getRole());
					String winMessage="";
					if(win==player.getRole()) {
						winMessage="恭喜您获得胜利!";
						JOptionPane.showMessageDialog(this, winMessage);
						prepare=0;
						start.setText("开始游戏");
						start.setEnabled(true);
						remake.setEnabled(false);
						lose.setEnabled(false);
						quit.setEnabled(true);
						contre.setChickeAble(false);
						player.setState(0);
						player.initPlayerChessCoord();		
						repaint();
					}else {
						contre.setChickeAble(false);
						GameHall.mainPage.remake.setEnabled(false);
						GameHall.mainPage.lose.setEnabled(false);
						
						//记录下棋地方
						List<Integer> list=new ArrayList<>();
						list.add(px);
						list.add(py);
						player.getRecord().push(list);
					}
				}
			}

			System.out.println("x:" + e.getX() + "   y:" + e.getY());
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		contre.setMouseX(e.getX());
		contre.setMouseY(e.getY());
		repaint();
	}
}
