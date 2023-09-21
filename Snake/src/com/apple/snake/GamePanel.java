package com.apple.snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.*;


public class GamePanel extends JPanel implements ActionListener {
	
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 400;
	private static final int UNIT = 20;
	private static final int ALL_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT*UNIT);
	private static final int DELAY = 100;
	
	boolean running = false;
	boolean right = true;
	boolean left = false;
	boolean down = false;
	boolean up = false;
	
	Timer timer;
	
	
	JButton restart = new JButton("Restart");

	
	
	//Snake
	final int x[] = new int[ALL_UNITS];
	final int y[] = new int[ALL_UNITS];
	int bodyUnits = 3;
	int applesEaten;
	//ArrayList<Point> snake = new ArrayList<>();
	
	//Apple coordinates
	ArrayList<Point> apl = new ArrayList<>();
	int appleX;
	int appleY;
	Point apple;
	
	///////////////////////////////////////////////////
	
	GamePanel(){
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();

	}
	
	public void startGame() {
		generateApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	
	//Drawings
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.RED);
			g.fillOval(apple.x, apple.y, UNIT, UNIT);
			for(int i=0; i<bodyUnits; i++) {
				if(i == 0) {
					g.setColor(Color.GREEN);
					g.fillRect(x[i], y[i], UNIT, UNIT);
				}
				else {
					g.setColor(Color.WHITE);
					g.fillRect(x[i], y[i], UNIT, UNIT);
				}
			}
			g.setColor(Color.CYAN);
			g.setFont(new Font("Arial", Font.BOLD, 30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics metric = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metric.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	
	}
	
	
	
	//SNAKE FUNCTIONALITY 
	 
	
	
	public void move() {
		for(int i=bodyUnits; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		if(right) x[0] +=  UNIT;
		if(left) x[0] -= UNIT;
		if(up) y[0] +=  UNIT;
		if(down) y[0] -= UNIT;
	}
	
	public void grow() {
		if(x[0] == apple.x && y[0] == apple.y) {
			applesEaten++;
			bodyUnits++;
			generateApple();
		}
	}
	
	public void checkCollision() {
		for(int i=bodyUnits; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) running = false;
		}
		if(x[0] < 0) running = false;
		if(x[0] > SCREEN_WIDTH) running = false;
		if(y[0] < 0) running = false;
		if(y[0] > SCREEN_HEIGHT) running = false;
		if(!running) timer.stop();
		
	}
	
	public void generateApple() {
		Random random = new Random();
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT))*UNIT;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT))*UNIT;
		apple = new Point(appleX, appleY);

	}
	
	
	
	
	//Inner class 
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e ) {
			int key = e.getKeyCode();
			
			if((key == KeyEvent.VK_RIGHT) && (!left)) {
				right = true;
				up = false;
				down = false;
			}
			if((key == KeyEvent.VK_LEFT) && (!right)) {
				left = true;
				up = false;
				down = false;
			}
			if((key == KeyEvent.VK_UP) && (!up)) {
				down = true;
				left = false;
				right = false;
			}
			if((key == KeyEvent.VK_DOWN) && (!down)) {
				up = true;
				left = false;
				right = false;
			}
			
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			grow();  
			checkCollision();
		}
		repaint();
		
	}


}




