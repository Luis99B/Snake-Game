import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeWillExpandListener;

public class Game extends JFrame {
	
	public static ArrayList<ArrayList<DataSquare>> Grid;
	private static int width = 20, height = 20;
	int pausa = 0;
	
	public Game() {
		super("Snake Game");
		setSize(500, 600);
		setLocation(200, 50);
		getContentPane().setBackground(new Color(125, 160, 80));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// ArrayList que contiene los Threads
		Grid = new ArrayList<ArrayList<DataSquare>>();
		ArrayList<DataSquare> data;
		
		// Crear Threads y sus datos y añadirlos al ArrayList
		for (int i = 0; i < width; i++) {
			data = new ArrayList<DataSquare>();
			for (int j = 0; j < height; j++) {
				DataSquare ds = new DataSquare(2);
				data.add(ds);
			}
			Grid.add(data);
		}
		
		// Configurando el layout del panel
		getContentPane().setLayout(new GridLayout(20, 20, 0, 0));
		
		// Iniciar y Pausar todos los Threads, luego añadir cada cuadro al thread del panel
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				getContentPane().add(Grid.get(i).get(j).square);
			}
		}
		
		// Posicion inicial de la Serpiente
		Tuple position = new Tuple(10, 10);
		// Pasar el valor al Controlador
		ThreadsController c = new ThreadsController(position);
		// Iniciar el Menu Principal
		c.start();
		
		// Enlazar la ventana con el KeyboardListener
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// System.out.println(e.getKeyCode());
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					pausar();
					System.out.println("PAUSA");
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					// Si no es la direccion opuesta
					if (ThreadsController.direccionSnake != 2) {
						ThreadsController.direccionSnake = 1;
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (ThreadsController.direccionSnake != 1) {
						ThreadsController.direccionSnake = 2;
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (ThreadsController.direccionSnake != 4) {
						ThreadsController.direccionSnake = 3;
					}
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (ThreadsController.direccionSnake != 3) {
						ThreadsController.direccionSnake = 4;
					}
				}
			}
		});;
		
		setVisible(true);
	}

	protected void pausar() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
