package com.TankGame.entities;
/**
 * 
 * @author Zhang's laptop
 * This is a class for depicting a bomb, which will be drew
 * on the canvas when a tank is hit by a bullet
 */
public class Bomb {

	/**
	 * Coordinates
	 */
	private int x;
	private int y;
	private boolean isAlive;
	
	/**
	 * life count of a bomb, if it reduce to 0,
	 * a bomb will be set to be not alive
	 */
	private int lifeCount = 12;
	
	/**
	 * Constructor
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param isAlive status of bomb
	 */
	public Bomb(int x, int y, boolean isAlive) {
		this.x = x;
		this.y = y;
		this.isAlive = isAlive;
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
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public int getLifeCount() {
		return lifeCount;
	}
	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}
	
	/**
	 * reduce lifecount by 1 each time
	 */
	public void lifeDeduction() {
		if(this.lifeCount>0) {
			this.lifeCount--;
		}else {
			setAlive(false);
		}
	}
}
