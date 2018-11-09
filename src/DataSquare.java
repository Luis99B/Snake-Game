import java.util.ArrayList;
import java.awt.Color;

public class DataSquare {

	// Arreglo que contiene los colores
	ArrayList<Color> C = new ArrayList<Color>();
	private int color;	// 2 Nada, 1 Food, 0 Snake
	SquarePanel square;
	
	public DataSquare(int colors) {
		C.add(new Color(50, 50, 50));	//0
		C.add(new Color(180, 60, 20));	//1
		C.add(new Color(125, 160, 80));	//2
		color = colors;
		square = new SquarePanel(C.get(color));
	}
	
	public void LightMeUp(int c) {
		square.ChangeColor(C.get(c));
	}
}
