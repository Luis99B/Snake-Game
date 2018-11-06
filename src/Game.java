import java.awt.*;
import javax.swing.*;

public class Game extends JFrame {

	private final static int GAMEWIDTH = 500, GAMEHEIGHT = 600, PIXELSIZE = 25;
	private final static int TOTALPIXELS = (GAMEWIDTH * GAMEHEIGHT) / (PIXELSIZE * PIXELSIZE);
	private boolean inGame = true;
	
	public Game() {
		super("Snake Game");
		setSize(500, 600);
		setLocation(200, 50);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
		
		setVisible(true);
	}
}
