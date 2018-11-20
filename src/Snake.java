import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Snake extends JFrame {

	private JMenuBar bar;
	private JMenu options, game;
	private JMenuItem high_scores, reset;

	public Snake() {
		super("Snake Game");
		add(new Board());
		setLocation(450, 100);
		setResizable(false);
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		bar = new JMenuBar();
		options = new JMenu("Options");
		game = new JMenu("Game");
		high_scores = new JMenuItem("View HighScores F3");
		reset = new JMenuItem("Reset R");
		
		options.add(high_scores);
		game.add(reset);
		bar.add(options);
		bar.add(game);
		setJMenuBar(bar);
		
		pack();
		
		high_scores.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("HighScores button");
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
		});
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Reset button");
				JFrame ex = new Snake();
				ex.setVisible(true);
			}
		});
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame ex = new Snake();
				ex.setVisible(true);
			}
		});
	}
}
