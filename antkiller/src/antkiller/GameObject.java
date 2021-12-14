package antkiller;

import java.awt.Graphics;

public abstract class GameObject {
	protected float x ,y;
	protected float velX ,velY;
	protected boolean alive;
	protected ID id;
	protected float hit;

	public GameObject(float x,float y,ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
		alive = true;
		hit = 0;
	}
	
	
	public abstract void update();
	public abstract void render(Graphics g);


	

	public float checkDistance(GameObject other) {
	
		return (float) Math.sqrt((other.getX()-this.x)*(other.getX()-this.x)+(other.getY()-this.y)*(other.getY()-this.y));
	}
	
	
	
	
	
	
	
	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	public float getX() {
		return x;
	}


	

	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public float getVelX() {
		return velX;
	}


	public void setVelX(float velX) {
		this.velX = velX;
	}


	public float getVelY() {
		return velY;
	}


	public void setVelY(float velY) {
		this.velY = velY;
	}


	public ID getId() {
		return id;
	}


	public void setId(ID id) {
		this.id = id;
	}
	
	public void hit(float dmg) {
		hit = dmg;
	}
	
}
