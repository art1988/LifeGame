import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.util.Timer;

public class LifeGame {
	private static JFrame window;
	private static BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
	private static Canvas canvas = new Canvas();
	
	private static Graphics imgGr = img.getGraphics();
	
	private static final int CELL_SIZE = 4;
	
	private static Cell[][] field = new Cell[img.getWidth() / CELL_SIZE][img.getHeight() / CELL_SIZE];
	
	private static void initWindowContent() {
		window.getContentPane().add(canvas);
		
		for(int y = 0; y < img.getHeight() / CELL_SIZE; y++) {
			for(int x = 0; x < img.getWidth() / CELL_SIZE; x++) {
				field[x][y] = new Cell();
			}
		}
		
		// генерируем случайные точки на поле
		for(int i = 0; i < 2000; i++) {
			int x = (int) ((Math.random() * img.getWidth()) / CELL_SIZE),
			    y = (int) ((Math.random() * img.getHeight()) / CELL_SIZE);
			
			field[x][y].turnOn();
		}
		
	}
	
	private static void initWindow() {
		window = new JFrame("Life game");
		window.setSize(450, 450);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 4, dim.height / 4);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	private static void paintCanvas() {
		for(int y = 0; y < img.getHeight(); y += CELL_SIZE) {
			for(int x = 0; x < img.getWidth(); x += CELL_SIZE) {
				int x_ = x / CELL_SIZE,
				    y_ = y / CELL_SIZE;
				
				if(field[x_][y_].getState() == true) {
					imgGr.setColor(Color.white);
					imgGr.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				}
				if(field[x_][y_].getState() == false) {
					imgGr.setColor(Color.black);
					imgGr.fillRect(x, y, CELL_SIZE, CELL_SIZE);
				}
			}
		}
		canvas.repaint();
	}
	
	LifeGame() {
		initWindow();
		initWindowContent();
		paintCanvas();
		
		new Timer().schedule(new MyTimerTask(), 0, 40);
	}
	
	public static void main(String[] args) {
		new LifeGame();
	}

	static class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			g.drawImage(img, 0,0, img.getWidth(), img.getHeight(), null);
		}
	}
	
	private static void parseOneCell(int x, int y) {
		int count = 0;
		
		if(field[x-1][y-1].getState() == true) count++;
		if(field[x][y-1].getState() == true) count++;
		if(field[x+1][y-1].getState() == true) count++;
		
		if(field[x-1][y].getState() == true) count++;
		if(field[x+1][y].getState() == true) count++;
		
		if(field[x-1][y+1].getState() == true) count++;
		if(field[x][y+1].getState() == true) count++;
		if(field[x+1][y+1].getState() == true) count++;
		
		field[x][y].setNeibCount(count);
	}
	
	// Сосчитать соседей вокруг клетки
	private static void countNeighbours() {
		for(int y = 1; y < (img.getHeight() / CELL_SIZE - 1); y++) {
			for(int x = 1; x < (img.getHeight() / CELL_SIZE - 1); x++) {
				parseOneCell(x, y);
			}
		}
	}
	
	public static void process() {
		for(int y = 0; y < img.getHeight() / CELL_SIZE; y++) {
			for(int x = 0; x < img.getWidth() / CELL_SIZE; x++) {
				field[x][y].checkDeathN1();
				field[x][y].checkDeathN2();
				field[x][y].checkAlive();
			}
		}
	}
	
	class MyTimerTask extends TimerTask {
		public void run() {
			countNeighbours();
			process();
			paintCanvas();
		}
	}
}