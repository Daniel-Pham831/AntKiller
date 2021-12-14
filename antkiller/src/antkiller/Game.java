package antkiller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.ImageIcon;



public class Game extends Canvas implements Runnable{
	public static int WIDTH = 1280, HEIGHT = 960;
	public static int FRAMERATE = 60;
	public String title = "Ant Killer";
	private int counter ;
	private Thread thread;
	
	private boolean isRunning = false;
	public Handler handler;
	public Game() {
		new Window(WIDTH,HEIGHT,title,this);
		start();
		init();
		counter = 0;
		
	//	handler.addObject(new Ant(200,200,ID.Ant));
	//	handler.addObject(new Ant(400,200,ID.Ant));
	//	handler.addObject(new Ant(200,200,ID.Ant));
		handler.addObject(new Ant(300,300,ID.Ant));
		handler.addObject(new Turret(Game.WIDTH/2,Game.HEIGHT/2,60,ID.Turret,handler));
		handler.addObject(new Turret(200,200,40,ID.Turret,handler));
		
		for (int i =0;i<10;i++) {
		
			handler.addObject(new Ant(new Random().nextInt(Game.WIDTH),new Random().nextInt(Game.HEIGHT),ID.Ant));
		}
		
	}
	
	private void init() {
		handler = new Handler();
	}
	
	private synchronized void start() {
		if(isRunning) return;
		
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	private synchronized void stop() {
		if(!isRunning) return;
		try {
			thread.join();
		}catch(Exception e) {
			e.printStackTrace();
		}
		isRunning = false;
	}
	
	@Override
	public void run() {
		/*long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		*/
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;     //NUMBER OF TICKS / SECOND
		double amountOfUpdates = 60.0;     //FPS
		double ns = 1000000000 / amountOfTicks;   //FIND THE NUMBER OF NANO SECONDS AFTER WHICH TICK HAPPENS
		double fp = 1000000000 / amountOfUpdates;       //FIND THE NUMBER OF NANO SECONDS AFTER WHICH UPDATE HAPPENS
		double delta = 0, delta2 = 0; 
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;    //WHEN CHANGE IN NANOSECONDS 
			delta2 += (now - lastTime) / fp;    //BETWEEN 2 INSTANCES IS AS LARGE AS THE TICK AND UPDATE INTERVAL
			lastTime = now; 
			if(delta > 5) delta = 3;     //IF IT FALLS BEHIND TOO MUCH IT SKIPS TICKS INSTEAD OF LAGGING
			while(delta >= 1){       //IT MAKES THE TICK
				update();  
				updates++;
				delta--;
			}
			if(delta2 > 3) delta2 = 1;     //IF IT FALLS BEHIND TOO MUCH IT ONLY DRAWS THE LATEST FRAME
			while(delta2 >= 1){       //AND THE UPDATE
				render();
				frames++;
		    	delta2--;
			}
		              //SHOW FPS AND TICKS AFTER EVERY SECOND
			if(System.currentTimeMillis() - timer > 1000){   
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}

	private void update() {
		handler.update();
		counter++;
		if(counter%30 == 0)
			handler.addObject(new Ant(new Random().nextInt(Game.WIDTH),new Random().nextInt(Game.HEIGHT),ID.Ant));
		counter = counter%30;
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
	
		g.setColor(new Color(215,230,225));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Graphics goes here

		
		handler.render(g);
		
		
		
		bs.show();
		g.dispose();
	}
	
	public static void main(String[] args) {
		new Game();
		
	}
}
