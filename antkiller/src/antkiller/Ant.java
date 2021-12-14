package antkiller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;


import javax.swing.ImageIcon;


public class Ant extends GameObject{
	private float angle;
	private int antW;
	private int antH;
	private float speed = 1.2f;
	private boolean isAlive;
	private int antMaxHealth;
	private int antCurrentHealth;
	private float healthBarW;
	private Image image;
	public Ant(float x, float y, ID id) {
		super(x, y, id);
		angle = new Random().nextInt(360);
		image = new ImageIcon("ant.png").getImage();
		antW = (int)(image.getWidth(null)*0.08f);
		antH = (int)(image.getHeight(null)*0.08f);
		isAlive = alive;
		antMaxHealth = 100;
		antCurrentHealth = antMaxHealth;
		healthBarW = antW;
	}

	private void turn() {
		if(new Random().nextInt(100)<10) 
			angle += (float)(new Random().nextInt(67) - 33.5);	
	}
	private void checkCollision() {
		
		
		if(x <= 0 +antW/2) {
			x += 3;
			angle += 180;
		}
		if(x >= Game.WIDTH - antW) {
			x -= 3;
			angle += 180;
		}
		if(y <= 0 +antW/2) {
			y += 3;
			angle += 180;
		}
		if( y >= Game.HEIGHT - antW) {
			y -= 3;
			angle += 180;
		}


		
		angle = angle%360;
	}
	private void move() {
		if(alive) {
			checkCollision();
			
			velX = (float) (speed* Math.cos(Math.toRadians(angle)));
			velY = (float) (speed* Math.sin(Math.toRadians(angle)));
			
	
			x += velX;
			y += velY;
		}	
	}
	@Override
	public void update() {
		checkHealth();
		move();	
		turn();
		
	}

	private void checkHealth() {
		antCurrentHealth -= hit;
		hit = 0;
		if(antCurrentHealth<=0) {
			antCurrentHealth = 0;
			alive = false;
		}
		healthBarW = (float)(antCurrentHealth*antW/antMaxHealth);
	}
	
	

	@Override
	public void render(Graphics g) {
		
		if(alive) {
			Graphics2D g2D = (Graphics2D)g;
			g2D.setColor(Color.CYAN);
			AffineTransform old = g2D.getTransform();
			
			g2D.translate(x, y);
			
			
			//draw the health bar
			g2D.setColor(new Color(28, 238, 129));
			g2D.fillRect(0-antW/2, (int)(0-(antH*1f)), antW, antH/4);
			//current health bar
			g2D.setColor(new Color(224, 93, 94));
			g2D.fillRect(0-antW/2, (int)(0-(antH*1f)), (int)healthBarW, antH/4);
			
			g2D.setColor(Color.black);
			g2D.drawRect(0-antW/2, (int)(0-(antH*1f)), antW, antH/4);
			
			
			//draw the ant
			g2D.rotate(Math.toRadians(angle));
			g2D.drawImage(image, 0-antW/2,0-antH/2,antW,antH,null);
			
			
			
			
			
			
			/*
			g2D.translate(x, y);
			g2D.rotate(Math.toRadians(angle));
			g2D.fillRect(0-antW/2,0-antH/2, antW, antH );
			g2D.setColor(Color.RED);
			g2D.fillRect(0,0 - antH/2, antW/2, antH);
			*/
			
			
			
			
			
			
			g2D.setTransform(old);
		}
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getAntMaxHealth() {
		return antMaxHealth;
	}

	public void setAntMaxHealth(int antMaxHealth) {
		this.antMaxHealth = antMaxHealth;
	}

	public int getAntCurrentHealth() {
		return antCurrentHealth;
	}

	public void setAntCurrentHealth(int antCurrentHealth) {
		this.antCurrentHealth = antCurrentHealth;
	}

	public void subtractAntCurrentHealth(int antCurrentHealth) {
		this.antCurrentHealth -= antCurrentHealth;
	}
	
	

}
