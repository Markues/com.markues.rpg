package com.markues.rpg;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

// The main game class
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// 12x9 resolution
	// Scale of 3
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	
	public static final String TITLE = "Legacy";
	
	private JFrame frame;
	
	public boolean running = false;
	
	// Constructor
	public Game() {
		// Sizing
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		frame = new JFrame(TITLE);
		
		// Exit upon closing window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set up the window
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}

	public void run() {
		if(running) {
			System.out.println("Hello World");
		}
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

}
