package com.markues.rpg;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

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
	
	public int tickCount = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
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
		
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0D;
		
		boolean shouldRender = true;
		
		// Main running loop
		while(running) {
			// The current time in nanoseconds
			long now = System.nanoTime();
			// the difference in time between the ticks
			delta += (now - lastTime) / nsPerTick;
			// reset the timer
			lastTime = now;
			
			// If time has passed, update (tick)
			while(delta >= 1) {
				ticks++;
				tick();
				delta--;
				shouldRender = true;
			}
			
			// Sleep every so often to keep the rendering in check
			/**try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			
			if(shouldRender) {
				// Increment frames each time we render
				frames++;
				render();
			}
			
			// Limit and reset variables every second
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks +" ticks, "+frames+" frames");
				frames = 0;
				ticks = 0;
			}	
		}
	}
	
	public void tick() {
		tickCount++;
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = i + tickCount;
		}
	}
	
	public void render() {
		BufferStrategy bufferStrat = getBufferStrategy();
		if(bufferStrat == null) {
			// Triple Buffering
			createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = bufferStrat.getDrawGraphics();
		
		// Make a black background behind the image
		/**graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		*/
		
		// Draw the image
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		// Dispose of the graphics object
		graphics.dispose();
		
		// Show the buffered image
		bufferStrat.show();
		
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

}
