
public class Tuple {

	int x, y;
	private int finalX, finalY;
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getFinalX() { return finalX; }
	public int getFinalY() { return finalY; }
	
	public Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
