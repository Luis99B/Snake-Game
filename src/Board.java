import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.swing.*;

public class Board extends JPanel implements ActionListener {

	private final int B_WIDTH = 300, B_HEIGHT = 300;
	private final int DOT_SIZE = 10, ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private int delay = 70;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x, apple_y;
	
	private boolean leftDir = false, rightDir = true, upDir = false, downDir = false;
	private boolean inGame = true;
	
	private Timer timer;
	private Image apple;
	
	private int score = 0, speed = 0;
	private int l = 1;
	
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setBackground(new Color(120, 160, 80));
		loadImages();
		initGame();
	}
	
	void loadImages() {
		ImageIcon a = new ImageIcon(this.getClass().getResource("/res/apple20.png"));
		apple = a.getImage();
	}
	
	void initGame() {
		dots = 3;
		for (int z = 0; z < dots; z++) {
			x[z] = 50 - z * 10;
			y[z] = 50;
		}
		locateApple();
		// we use a timer on a timer to call an action performed method at fixed delay
		timer = new Timer(delay, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		if (inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			for (int z = 0; z < dots; z++) {
				if (z == 0) {
					g.setColor(new Color(80, 80, 80));
					g.fillRoundRect(x[z], y[z], 20, 20, 15, 15);
				} else {
					g.setColor(new Color(110, 110, 110));
					g.fillRoundRect(x[z], y[z], 20, 20, 8, 8);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}
	
	private void gameOver(Graphics g) {
		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 30);
		FontMetrics metr = getFontMetrics(small);
		g.setColor(Color.WHITE);
		g.setFont(small);
		g.drawString(msg, ((B_WIDTH - metr.stringWidth(msg)) / 2), B_HEIGHT / 2 - 10);
		
		String sc = "Score " + score;
		Font small2 = new Font("Helvetica", Font.PLAIN, 25);
		g.setFont(small2);
		g.drawString(sc, ((B_WIDTH - metr.stringWidth(sc)) / 2 ), B_HEIGHT/ 2  + 20);
		
		saveScore(score);
		
		score = 0;
		speed = 0;
	}
	
	private void checkApple() {
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
			score += 1000;
				if (delay > 10) {
					speed++;
					System.out.println(speed);
					if (speed % 5 == 0) {
						delay -= 10;
						System.out.println(delay);
					}
				} else {
					delay = 10;
				}
			}
		}
	
	private void move() {//
		for (int z = dots; z > 0; z--) {
			x[z] = x[z - 1];
			y[z] = y[z - 1];
		}
		if (leftDir) {
			x[0] -= DOT_SIZE;
		}
		if (rightDir) {
			x[0] += DOT_SIZE;
		}
		if (upDir) {
			y[0] -= DOT_SIZE;
		}
		if (downDir) {
			y[0] += DOT_SIZE;
		}
	}
	
	private void checkCollision() {
		for (int z = dots; z > 0; z--) {
			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}
		if (y[0] >= B_HEIGHT) {
			inGame = false;
		}
		if (y[0] < 0) {
			inGame = false;
		}
		if (x[0] >= B_WIDTH) {
			inGame = false;
		}
		if (x[0] < 0) {
			inGame = false;
		}
		if (! inGame) {
			timer.stop();
		}
	}
	
	private void locateApple() {
		int r = (int)(Math.random() * RAND_POS);
		for (int z = 0; z < dots; z++) {
			if (r != x[z]) {
				//System.out.println(r + " X Random * RAND_POS");
				apple_x = r * DOT_SIZE;
			} else {
				System.out.println("Recursividad x");
				locateApple();
			}
		}
		
		r = (int)(Math.random() * RAND_POS);
		for (int z = 0; z < dots; z++) {
			if (r != y[z]) {
				//System.out.println(r + " Y Random * RAND_POS");
				apple_y = r * DOT_SIZE;
			} else {
				System.out.println("Recursividad y");
				locateApple();
			}
		}
	}
	
	private void saveScore(int fScore) {
		try {
			FileReader fr = new FileReader("HighScores.txt");
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter("HighScores.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			for (int ln = 3; ln < 13; ln++) {
					//String[] highScores = line.split(".");
					//for (int i = 0; i < highScores.length; i++) {
						//System.out.println(highScores[i]);
					//}
					Date date = new Date();
					pw.println("#.Score.(Date)");
					pw.println(l + "." + fScore + ".(" + date.toString() + ")");
					System.out.println(l + "." + fScore + ".(" + date.toString() + ")");
					l++;
					break;
			}
			br.close();
			fr.close();
			pw.close();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (inGame) {
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			//System.out.println(e.getKeyCode());
			if ((e.getKeyCode() == KeyEvent.VK_UP) && !downDir) {
				// Arriba
				//System.out.println("Arriba");
				upDir = true;
				leftDir = false;
				rightDir = false;
			}
			if ((e.getKeyCode() == KeyEvent.VK_DOWN) && !upDir) {
				// Abajo
				//System.out.println("Abajo");
				downDir = true;
				leftDir = false;
				rightDir = false;
			}
			if ((e.getKeyCode() == KeyEvent.VK_LEFT) && !rightDir) {
				// Izquierda
				//System.out.println("Izquierda");
				leftDir = true;
				upDir = false;
				downDir = false;
			}
			if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && !leftDir) {
				// Derecha
				//System.out.println("Derecha");
				rightDir = true;
				upDir = false;
				downDir = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_R) {
				// Reiniciar
				if (!inGame) {
					System.out.println("Reiniciar");
					inGame = true;
					leftDir = false;
					rightDir = true;
					upDir = false;
					downDir = false;
					loadImages();
					initGame();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_F3) {
				// Ver HighScores
				if (!inGame) {
					System.out.println("HighScores");
					try {
						File scores = new File("HighScores.txt");
						if (scores.exists()) {
							Desktop.getDesktop().open(scores);
						}
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("El archivo NO se encuentra o NO existe");
					}
				}
			}
		}
	}
}
