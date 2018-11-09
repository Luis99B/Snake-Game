import javax.swing.JPanel;
import java.awt.Color;

public class SquarePanel extends JPanel {

	public SquarePanel(Color d) {
		this.setBackground(d);
	}
	
	public void ChangeColor(Color d) {
		this.setBackground(d);
		this.repaint();
	}
}
