package com.wold.page;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.wold.pojo.Board;
import com.wold.pojo.Chess;
import com.wold.pojo.Player;

/**
 * 棋盘下棋操作
 * @author WOLD
 *
 */
public class ChessBoard extends JPanel {

	private Image checkerBoard;
	private Image whiteChess;
	private Image blackChess;
	private int mouseX;
	private int mouseY;
	private boolean chickeAble = false;


	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	public boolean isChickeAble() {
		return chickeAble;
	}

	public void setChickeAble(boolean chickeAble) {
		this.chickeAble = chickeAble;
	}

	public ChessBoard() {
		loadImages();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Component
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(checkerBoard, 0, 0, this);
		if (chickeAble) {
			drawCoord(g2);
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (Player.playerChessCoord[i][j] == Chess.BLACK) {
					g2.drawImage(blackChess, Board.board[0][i] - 21, Board.board[1][j] - 21, this);
				}
				if (Player.playerChessCoord[i][j] == Chess.WHITE) {
					g2.drawImage(whiteChess, Board.board[0][i] - 21, Board.board[1][j] - 21, this);
				}
			}
		}
	}

	public void drawCoord(Graphics2D g2) {
		int px = 0, py = 0;
		if (mouseX < 36) {
			px = 36;
		} else if (mouseX > 798) {
			px = 798;
		} else {
			for (int i = 0; i < 16; i++) {
				if (Board.board[0][i] <= mouseX && Board.board[0][i + 1] >= mouseX) {
					if (mouseX - Board.board[0][i] >= Board.board[0][i + 1] - mouseX) {
						px = Board.board[0][i + 1];
					} else {
						px = Board.board[0][i];
					}
					break;
				}
			}
		}

		if (mouseY < 24) {
			py = 24;
		} else if (mouseY > 705) {
			py = 705;
		} else {
			for (int i = 0; i < 16; i++) {
				if (Board.board[1][i] <= mouseY && Board.board[1][i + 1] >= mouseY) {
					if (mouseY - Board.board[1][i] >= Board.board[1][i + 1] - mouseY) {
						py = Board.board[1][i + 1];
					} else {
						py = Board.board[1][i];
					}
					break;
				}
			}
		}
		g2.drawRect(px - 21, py - 21, 42, 42);
	}

	public void loadImages() {
		checkerBoard = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/chessBoard.png"));
		whiteChess = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/white.png"));
		blackChess = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/black.png"));
	}

}
