package com.TankGame.entities;
/**
 * 
 * @author Zhang's laptop
 * This is the muzzle of a tank
 */
public class Muzzle {

	/**
	 * x,y coordinates of muzzle
	 */
	private int x;
	private int y;
	
	public Muzzle() {}
	
	public Muzzle(int x, int y) {
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
}
