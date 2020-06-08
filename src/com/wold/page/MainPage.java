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
import java.util.Date;

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
	public static MainPage main;
	private JPanel left;
	public static ChessBoard contre;
	private JPanel right;
	private JTextField ip;
	private JButton start;
	private JButton remake;
	private JButton lose;
	private JButton quit;
	private JTextArea showMessage;
	private JTextField input;
	private JButton send;

	private Server server = null;
	private Clinet clinet = null;
	private Boolean isServer = null;
	private JLabel nameServer;
	private JLabel nameClinet;
	public static JLabel countDown;
	public static JLabel state1;
	public static JLabel state2;
	private Player player;
	
	public static TimeThread time;
	public MainPage() {
		init();
	}

	public static void main(String[] args) {
		main=new MainPage();
	}

	public void init() {
		this.setTitle("����������");
		this.setSize(1400, 800); // ���ô��ڴ�С
		this.setResizable(false);// ���ڴ�С���ɱ�
		this.setLayout(null); // ����JFrame����Ϊnull

		initLeftJPane();
		initContreJPane();
		initRightJPane();
		this.add(left);
		this.add(contre);
		this.add(right);

		startMonitor();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ��ȡ��Ļ��С
		int centerX = screenSize.width / 2;
		int centerY = screenSize.height / 2;
		this.setLocation(centerX - 1400 / 2, centerY - 800 / 2);// ��Ļ������ʾ

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * ҳ���󲿳�ʼ��
	 */
	private void initLeftJPane() {
		left = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		fl.setAlignment(FlowLayout.CENTER);
		left.setLayout(fl);
		left.setSize(230, 800);
		left.setLocation(0, 0);
		left.setBackground(Color.gray);

		ImageIcon headImage1 = new ImageIcon("src/image/head1.jpg");
		headImage1.setImage(headImage1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel head1 = new JLabel(headImage1);
		head1.setPreferredSize(new Dimension(100, 100));// ���ð�ť��С
		head1.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon headImage2 = new ImageIcon("src/image/head2.jpg");
		headImage2.setImage(headImage2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		JLabel head2 = new JLabel(headImage2);
		head2.setPreferredSize(new Dimension(100, 100));// ���ð�ť��С
		head2.setBorder(BorderFactory.createLineBorder(Color.black));
		nameServer = new JLabel("������", JLabel.CENTER);
		nameServer.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		nameServer.setBorder(BorderFactory.createLineBorder(Color.red));
		nameClinet = new JLabel("������", JLabel.CENTER);
		nameClinet.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		nameClinet.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel win1 = new JLabel("ʤ��:0%", JLabel.CENTER);
		win1.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		win1.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel win2 = new JLabel("ʤ��:0%", JLabel.CENTER);
		win2.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		win2.setBorder(BorderFactory.createLineBorder(Color.red));
		state1 = new JLabel("״̬:�ȴ�...", JLabel.CENTER);
		state1.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		state1.setBorder(BorderFactory.createLineBorder(Color.red));
		state2 = new JLabel("״̬:����...", JLabel.CENTER);
		state2.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		state2.setBorder(BorderFactory.createLineBorder(Color.red));
		countDown = new JLabel("����ʱ:30s", JLabel.CENTER);
		countDown.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		countDown.setBorder(BorderFactory.createLineBorder(Color.red));

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
	 * ҳ���м�JPane���ֳ�ʼ��
	 */
	private void initContreJPane() {
		contre = new ChessBoard();
		contre.setSize(840, 800);
		contre.setLocation(230, 0);

	}

	/**
	 * ҳ���Ҳ����ֳ�ʼ��
	 */

	private void initRightJPane() {
		right = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setVgap(20);
		right.setLayout(fl);
		right.setSize(330, 800);
		right.setLocation(1070, 0);
		right.setBackground(Color.blue);
		ip = new JTextField("����Է�ip��ַ:�˿�(����ʱ����˿�)", 20);
		start = new JButton("��ʼ��Ϸ");
		start.setPreferredSize(new Dimension(200, 50));// ���ð�ť��С
		start.setFont(new Font("����", Font.BOLD, 20));
		remake = new JButton("����");
		remake.setPreferredSize(new Dimension(200, 50));
		remake.setFont(new Font("����", Font.BOLD, 20));
		lose = new JButton("����");
		lose.setPreferredSize(new Dimension(200, 50));
		lose.setFont(new Font("����", Font.BOLD, 20));
		quit = new JButton("����");
		quit.setPreferredSize(new Dimension(200, 50));
		quit.setFont(new Font("����", Font.BOLD, 20));
		showMessage = new JTextArea(20, 25);
		showMessage.setEditable(false);
		showMessage.setLineWrap(true);
		JScrollPane scrollpane = new JScrollPane(showMessage); // ��TextArea��װ��JScrollPane��ʵ�ֹ���Ч��
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// ���ô�ֱ���������ǳ���

		input = new JTextField(20);
		send = new JButton("����");
		start.setSize(20, 20);
		right.add(ip);
		right.add(start);
		right.add(remake);
		right.add(lose);
		right.add(quit);
		right.add(scrollpane);
		right.add(input);
		right.add(send);
	}

	/**
	 * ����������¼�����
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
		if (e.getSource() == start) {
			String str1;
			String str2;
			
			String part = ip.getText();
			if (part.equals("9999") && server == null) {
				isServer = true;
				contre.setChickeAble(true);
				player = new Player(1, 1);
				server = new Server(part, showMessage,player);
				server.start();
				time=new TimeThread(countDown);
				time.start();
			} else { // ����������ʽip��ַ���
				isServer = false;
				contre.setChickeAble(false);
				player = new Player(2, 0);
				clinet = new Clinet(part,showMessage,player);
				clinet.start();
				
			}

		} else if (e.getSource() == remake) {
			System.out.println("����");
		} else if (e.getSource() == lose) {
			System.out.println("����");
		} else if (e.getSource() == quit) {
			System.out.println("����");
		} else if (e.getSource() == send) {
			if (isServer != null) {
				String message = input.getText();
				if (!message.equals("")) {
					message ="1:"+message;
					showMessage.append(message+"\n");
					input.setText("");
					if (isServer) {
						server.sendMessage(message);
					} else {
						clinet.sendMessage(message);
					}
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
				if (player.getPlayerChessCoord()[px][py] == Chess.BLANK) {
					player.setPlayerChessCoord(px, py,player.getRole());
					repaint();
					player.setState(0);
					if(isServer) {
						state2.setText("״̬:�ȴ�...");
						state1.setText("״̬:����...");
					}else {
						state1.setText("״̬:�ȴ�...");
						state2.setText("״̬:����...");
					}
					contre.setChickeAble(false);
					String message="0:"+px+":"+py;
					if(player.getRole()==1) {
						server.sendMessage(message);
					}else {
						clinet.sendMessage(message);
					}
					
					time.interrupt();//��������ʱ
					
					//�ж��Ƿ������Ϸ
					int win=Judge.whowin(px, py, player.getPlayerChessCoord(), player.getRole());
					String winMessage="";
					JOptionPane winShow=new JOptionPane();
					if(win==player.getRole()) {
						winMessage="��ϲ�����ʤ��!";
						if(player.getRole()==1) {
							server.sendMessage("2:"+winMessage);
							//�ı�״̬
							player.setState(1);
							MainPage.contre.setChickeAble(true);
							MainPage.time=new TimeThread(MainPage.countDown);
							MainPage.state1.setText("״̬:�ȴ�...");
							MainPage.state2.setText("״̬:����...");
							winShow.showMessageDialog(this, winMessage);
							MainPage.time.start();
						}else {
							clinet.sendMessage("2:"+winMessage);
							//�ı�״̬
							player.setState(0);
							MainPage.contre.setChickeAble(false);
							MainPage.state1.setText("״̬:�ȴ�...");
							MainPage.state2.setText("״̬:����...");
							winShow.showMessageDialog(this, winMessage);
						}
						player.initPlayerChessCoord();
						repaint();
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
