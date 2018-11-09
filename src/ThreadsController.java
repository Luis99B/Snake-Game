import java.util.ArrayList;

public class ThreadsController extends Thread {

	ArrayList<ArrayList<DataSquare>> Squares = new ArrayList<ArrayList<DataSquare>>();
	
	public Tuple posCabezaSnake;
	public int tamSnake = 3;
	long velocidad = 50;
	public static int direccionSnake;
	
	ArrayList<Tuple> posiciones = new ArrayList<Tuple>();
	Tuple posFood;
	
	public ThreadsController(Tuple posSalida) {
		Squares = Game.Grid;
		
		posCabezaSnake = new Tuple(posSalida.getX(), posSalida.getY());
		direccionSnake = 1;
		
		Tuple posCabeza = new Tuple(posCabezaSnake.getX(), posCabezaSnake.getY());
		posiciones.add(posCabeza);
		
		posFood = new Tuple(Game.HEIGHT-1, Game.WIDTH-1);
		spawnFood(posFood);
	}
	
	public void spawnFood(Tuple posFoodDentro) {
		Squares.get(posFoodDentro.getX()).get(posFoodDentro.getY()).LightMeUp(1);
	}
	
	// Parte Importante
	public void run() {
		while (true) {
			moverIntern(direccionSnake);
			checarColision();
			moverExtern();
			borrarCola();
			pausar();
		}
	}
	
	// Delay entre  cada movimientos de la serpiente
	public void pausar() {
		try {
			sleep(velocidad);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Checar si la serpiente come la fruta o asi misma
	public void checarColision() {
		Tuple posicionCritica = posiciones.get(posiciones.size() - 1);
		for (int i = 0; i < posiciones.size() - 2; i++) {
			boolean seMordio = ( posicionCritica.getX() == posiciones.get(i).getX() && posicionCritica.getY() == posiciones.get(i).getY() );
			if (seMordio) {
				detenerJuego();
			}
		}
		
		boolean comerFruta = ( posicionCritica.getX() == posFood.y && posicionCritica.getY() == posFood.x );
		if (comerFruta) {
			System.out.println("Rico (+1)");
			tamSnake++;
			posFood = getValorAreaNoEnSerpiente();
			spawnFood(posFood);
		}
	}
	
	// Detener el juego
	public void detenerJuego() {
		System.out.println("Perdiste");
		while (true) {
			pausar();
		}
	}
	
	// Regresar una posicion no ocupada por la serpiente
	public Tuple getValorAreaNoEnSerpiente() {
		Tuple p;
		int ranX = 0 + (int)(Math.random()*19);
		int ranY = 0 + (int)(Math.random()*19);
		p = new Tuple(ranX, ranY);
		for(int i = 0; i <= posiciones.size() - 1; i++){
			if(p.getY() == posiciones.get(i).getX() && p.getX() == posiciones.get(i).getY()) {
				ranX = 0 + (int)(Math.random()*19);
				ranY = 0 + (int)(Math.random()*19);
				p = new Tuple(ranX, ranY);
				i = 0;
			 }
		 }
		return p;
	}
	
	// Refrescar la cola de la serpiente, quitando la data superfluo en posisiones del arreglo
	// y refrescar la pantalla de las cosas que se remueven
	public void borrarCola() {
		int cmpt = tamSnake;
		for (int i = posiciones.size() - 1; i >= 0 ; i--) {
			if (cmpt == 0) {
				Tuple t = posiciones.get(i);
				Squares.get(t.y).get(t.x).LightMeUp(2);
			} else {
				cmpt--;
			}
		}
		cmpt = tamSnake;
		for (int i = posiciones.size() - 1; i >= 0 ; i--) {
			if (cmpt == 0) {
				posiciones.remove(i);
			} else {
				cmpt--;
			}
		}
	}
	
	// Mover la cabeza de la serpiente y actualizar las posiciones en el arreglo
	// 1 derecha, 2 izquierda, 3 arriba, 4 abajo, 0 nada
	public void moverIntern(int dir) {
		switch (dir) {
		case 1:
			posCabezaSnake.Posicion(Math.abs(posCabezaSnake.x + 1) % 20, posCabezaSnake.y);
			posiciones.add(new Tuple(posCabezaSnake.x, posCabezaSnake.y));
			break;
		case 2:
			if (posCabezaSnake.x - 1 < 0) {
				posCabezaSnake.Posicion(19, posCabezaSnake.y);
			} else {
				posCabezaSnake.Posicion(Math.abs(posCabezaSnake.y - 1) % 20, posCabezaSnake.y);
			}
			posiciones.add(new Tuple(posCabezaSnake.x, posCabezaSnake.y));
			break;
		case 3:
			if (posCabezaSnake.y - 1 < 0) {
				posCabezaSnake.Posicion(posCabezaSnake.x, 19);
			} else {
				posCabezaSnake.Posicion(posCabezaSnake.x, Math.abs(posCabezaSnake.y - 1) % 20);
			}
			posiciones.add(new Tuple(posCabezaSnake.x, posCabezaSnake.y));
			break;
		case 4:
			posCabezaSnake.Posicion(posCabezaSnake.x, (posCabezaSnake.y + 1) % 20);
			posiciones.add(new Tuple(posCabezaSnake.x, posCabezaSnake.y));
			break;
		}
	}
	
	 // Actualizar los cuadros que lo requieran
	private void moverExtern() {
		for(Tuple t : posiciones) {
			int y = t.getX();
			int x = t.getY();
			Squares.get(x).get(y).LightMeUp(0);
		}
	}
}
