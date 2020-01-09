package com.TankGame.entities;

import java.awt.Color;

/**
 * 
 * @author Zhang's laptop This class represent a piece of wall on the canvas
 */
public class Wall implements Entity {

	/**
	 * x,y coordinates of a piece of wall
	 */
	private int x;
	private int y;
	private boolean isAlive = true;
	
	/**
	 * width and height of a piece of wall
	 */
	public static int WIDTH = 20;
	public static int HEIGHT = 15;
	public static Color TYPE_IS_WALL = Color.YELLOW;
	
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
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

	public boolean isAlive() {
		// TODO Auto-generated method stub
		return this.isAlive;
	}

	public void setAlive(boolean isAlive) {
		// TODO Auto-generated method stub
		this.isAlive = isAlive;
	}

	public int getMaxX() {
		// TODO Auto-generated method stub
		return this.x + Wall.WIDTH;
	}

	public int getMaxY() {
		// TODO Auto-generated method stub
		return this.y + Wall.HEIGHT;
	}

	public int getMinX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public int getMinY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public Color getType() {
		// TODO Auto-generated method stub
		return Wall.TYPE_IS_WALL;
	}

}
