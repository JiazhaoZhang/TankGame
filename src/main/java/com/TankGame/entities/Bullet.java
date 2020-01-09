package com.TankGame.entities;

import java.awt.Color;
import java.util.Vector;

import com.TankGame.MyPanel;
/**
 * 
 * @author Zhang's laptop
 * This is class for depicting the bullets
 */
public class Bullet implements Runnable,Entity{

	/**
	 * Coordinates
	 */
	private int x;
	private int y;
	/**
	 * direction that a bullet will go towards
	 */
	private int direction;
	
	/**
	 * speed bullet will go with
	 */
	private int speed = 5;
	
	private boolean isAlive = true;
	private Color type;
	
	public static final Color PLAYER_BULLET = Color.ORANGE;
	public static final Color ENEMY_BULLET = Color.GREEN;
	
	/**
	 * Constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param direction direction to go
	 * @param type bullet type
	 */
	public Bullet(int x, int y, int direction, Color type) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.type = type;
	}

	
	public int getX() {
		return x;
	}

	
	public void setX(int x) {
		this.x = x;
	}

	
	public int getY() {
		return y;
	}

	
	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Color getType() {
		return type;
	}

	public void setType(Color type) {
		this.type = type;
	}

	/**
	 * Check if a bullet hits an entity on the canvas
	 * @param e entity
	 * @return true is bullet will hit, otherwise false
	 */
	public boolean hitEntity(Entity e) {
		
		if(this.type.equals(ENEMY_BULLET)) { //see what type if tank that fires the bullet
		
	    // if a bullet is fired by an enemy tank
		//a bullet hit an entity only when that entity is not an enemy tank
		return x<=e.getMaxX()
				&& x >= e.getMinX()
				&& y>= e.getMinY()
				&& y<=e.getMaxY()
				&& !e.getType().equals(Tank.ENEMY);
		}
		
		if(this.type.equals(PLAYER_BULLET)) {
			return x<=e.getMaxX()
					&& x >= e.getMinX()
					&& y>= e.getMinY()
					&& y<=e.getMaxY()
					&& !e.getType().equals(Tank.PLAYER);
			}
		return false;
	}
	
	/**
	 * loop to see if a bullet hit either of entities given
	 * @param entities entities to be checked
	 */
	public void hitEntities(Vector<Entity> entities) {
		
		for(Entity e:entities) {
			if(e.isAlive() && this.hitEntity(e)) {
				//if a bullet hits an entity, make it dead
				this.setAlive(false);
				break;
			}
		}
	}
	
	public void move() {
		
		//Vector entities = MyPanel.getEntities();
		
		if(this.direction==Tank.TO_RIGHT) { //if a bullet is going to right
			if(this.x > MyPanel.BORDER_MAX_X) {
				//if a bullet is out of gaming area
				//make it dead and disappear
				this.setAlive(false);
			}else {
				this.x+=this.speed;
			}
		}
		
		if(this.direction==Tank.TO_LEFT) { //if a bullet is going to left
			
			if(this.x < MyPanel.BORDER_MIN_X ) {
				this.setAlive(false);
			}else {
				this.x-=this.speed;
			}
		}
		
		if(this.direction==Tank.TO_UP) { //if a bullet is going up
			if(this.y < MyPanel.BORDER_MIN_Y ) {
				this.setAlive(false);
			}else {
				this.y-=this.speed;
			}
		}
		if(this.direction==Tank.TO_DOWN) { //if a bullet is going down
			if(this.y > MyPanel.BORDER_MAX_y ) {
				this.setAlive(false);
			}else {
				this.y+=this.speed;
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while(this.isAlive()) {
			try {
				Thread.sleep(50);
				this.move();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getMaxX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMinY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
