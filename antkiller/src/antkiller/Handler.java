package antkiller;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void update() {
		/*
		for(GameObject temp: object) {
			temp.update();
		}*/
		
		for(int i = 0;i<object.size();i++) {
			if(!object.get(i).alive)
				object.remove(object.get(i));
			else
				object.get(i).update();
		}
		System.out.println("number of objects:"+object.size());
	}
	
	public void render(Graphics g) {
		/*
		for(GameObject temp: object) {
			temp.render(g);
		}*/
		
		for(int i = 0;i<object.size();i++) {
			object.get(i).render(g);
		}
	}
	
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
	}
	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}
}
