package com.TankGame.entities;

import java.awt.Color;
/**
 * @author Zhang's laptop
 * An interface for abstracting entity on canvas
 */
public interface Entity {

	public boolean isAlive();
	
	public void setAlive(boolean isAlive);
	
	public int getMaxX();
	
	public int getMaxY();
	
	public int getMinX();
	
	public int getMinY();
	
	public Color getType();
}
