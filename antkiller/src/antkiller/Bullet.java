package antkiller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Bullet extends GameObject{
	private int size;
	private float angle;
	private float speed;
	private float bulletDmg;


	private Handler handler;
	private Handler antHandler;
	
	
	public Bullet(float x, float y,int size,float angle,float speed,float bulletDmg, ID id,Handler handler) {
		super(x, y, id);
		this.size = size;
		this.angle = angle;
		this.handler = handler;
		this.speed = speed;
		this.bulletDmg = bulletDmg;
	}

	@Override
	public void update() {
		
		hit();
		if (alive){
			velX = (float) (speed* Math.cos(Math.toRadians(angle)));
			velY = (float) (speed* Math.sin(Math.toRadians(angle)));
			
	
			x += velX;
			y += velY;
		}
	}

	@Override
	public void render(Graphics g) {
		if (alive){
			fire(g);
		}
	}
	public void fire(Graphics g) {
			Graphics2D g2D = (Graphics2D)g;
			
			AffineTransform old = g2D.getTransform();
			
			g2D.setColor(new Color(15, 31, 35));
			g2D.translate(x, y);
			g2D.fillOval(0-size/2, 0-size/2, size, size);
			
		
			
			
			g2D.setTransform(old);
	}
	
	
	public void hit() {
		if(!alive) return;
		if(x < 0) {
			alive = false;
			return;
		}
		if(x > Game.WIDTH) {
			alive = false;
			return;
		}
		if(y < 0) {
			alive = false;
			return;
		}
		if(y > Game.HEIGHT) {
			alive = false;
			return;
		}
		
		for(int i = 0;i<handler.object.size();i++) {
			if(handler.object.get(i).id==ID.Ant && handler.object.get(i).checkDistance(this) <= 10) {
				handler.object.get(i).hit = this.bulletDmg;
				this.alive = false;
				return;
			}
		}
		
		
		alive = true;
		return;
		
	}
	
	
	
	

}
