package antkiller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.Random;

public class Turret extends GameObject{
	private int size;
	private float circleSize;
	private float angle;
	private int rateOfFire = 10;
	private float bulletSpeed = 10;
	private float bulletDmg = 20;
	private int rate ;
	private int counter;
	private float bX ,bY;
	private float range;

	private Handler handler;
	private LinkedList<Bullet> bullets;
	private GameObject tempClosest;
	
	
	public Turret(float x, float y, int size,ID id,Handler handler) {
		super(x, y, id);
		this.size = size;
		circleSize = size*0.7f;
		angle = 0;
		rate = Game.FRAMERATE / rateOfFire;
		counter = 0;
		//range = circleSize*3.5f;
	
		tempClosest = null;
		this.handler = handler;
		//if (size<=80) 
		//	range = 50*4.5f;
		
		
		bullets = new LinkedList<Bullet>();
		range = 500;
	}

	
	
	@Override
	public void update() {
		angle += 1;
		checkInrange();
		
		
		if(tempClosest!=null) {
			if(tempClosest.x<=x)
				angle = ((float) Math.toDegrees(Math.atan((tempClosest.y-y)/(tempClosest.x-x))))-180;
			else
				angle = ((float) Math.toDegrees(Math.atan((tempClosest.y-y)/(tempClosest.x-x))));
		}
	
		
		counter++ ;
		if(counter%rate == 0 && tempClosest!=null) {
			
			bX = (float) (circleSize*0.7f * Math.cos(Math.toRadians(angle)));
			bY = (float) (circleSize*0.7f * Math.sin(Math.toRadians(angle)));
			bullets.add(new Bullet(x+bX,y+bY,(int)circleSize/4,angle,bulletSpeed,bulletDmg,ID.Bullet,handler));
			
		}
		counter = counter%rate;
		
		
		if(bullets.size()!=0)
			if(!bullets.getFirst().alive) {
				bullets.removeFirst();
			}
		
		
		if(bullets.size()!=0) {
			for(Bullet b : bullets) {
				b.update();
			}
		}
		
	}
	
	private void checkInrange() {
		boolean flag = true;
		GameObject temp = null;
		for(int i = 0;i<handler.object.size();i++) {
			
			if(handler.object.get(i).id==ID.Ant && handler.object.get(i).checkDistance(this)<=range/2) {
				
				if(flag) {
					tempClosest = handler.object.get(i);
					
					flag = false;
				}else {
					temp = handler.object.get(i);
					if(tempClosest.checkDistance(this) > temp.checkDistance(this))
						tempClosest = temp;
				}
			}
				
		}
		if (flag)
			tempClosest = null;
	}

	@Override
	public void render(Graphics g) {
		drawTurret(g);
		if(bullets.size()!=0) {
			for(Bullet b : bullets)
				b.render(g);
		}
	}
	
	
	public void drawTurret(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		
		AffineTransform old = g2D.getTransform();
		
		
		
		
		g2D.setColor(Color.darkGray);
		g2D.translate(x, y);
		g2D.fillRect(0-size/2,0-size/2, size, size );
		
		
		//Range
		g2D.setColor(Color.black);
		g2D.setStroke(new BasicStroke(3));
		g2D.drawOval(0-(int)range/2,0-(int)range/2, (int)range,(int)range);
		g2D.setStroke(new BasicStroke(1));
		
		
		g2D.setColor(Color.LIGHT_GRAY);
		g2D.fillOval((int)(0-circleSize/2),(int)( 0-circleSize/2), (int)circleSize, (int)circleSize);
		
		
		g2D.rotate(Math.toRadians(angle));
		g2D.setColor(Color.RED);
		g2D.fillRect(0, 0-(int)(circleSize/12), (int)(circleSize*0.7f), (int)(circleSize/6));
		
		g2D.setTransform(old);
		
		
		old = g2D.getTransform();
	
		
		g2D.translate(x, y);
		g2D.setColor(Color.DARK_GRAY);
		g2D.fillOval(0-(int)(circleSize/4),0-(int)(circleSize/4), (int)(circleSize/2), (int)(circleSize/2));
		
		
		
		
		
		

		
		g2D.setTransform(old);
		
	}
	
	
	

}
