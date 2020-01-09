package com.TankGame.entities;

import java.awt.Color;
import java.util.Vector;

import com.TankGame.MyPanel;
/**
 * 
 * @author Zhang's laptop
 * This is a class for depicting a tank on canvas
 */
public class Tank implements Runnable, Entity {

	/**
	 * x,y coordinates
	 */
	private int x;
	private int y;
	// this is the direction of a tank's muzzle points to
	private int direction = 0; 
	private Muzzle muzzle;
	
	/**
	 * bullets this tank fires
	 */
	private Vector<Bullet> bullets = new Vector<Bullet>();
	private Color type;
	private boolean isAlive = true;

	public static final int TO_LEFT = 0;
	public static final int TO_DOWN = 1;
	public static final int TO_RIGHT = 2;
	public static final int TO_UP = 3;
	/**
	 * maximum number of bullets a tank can fire
	 */
	public static final int MAX_BULLETS = 5;
	public static final Color PLAYER = Color.CYAN;
	public static final Color ENEMY = Color.PINK;
	public static final String PLAYER_NAME = "hero";
	
	/**
	 * the width and height a tank will strech when it going to left or right
	 */
	public static final int HORIZONTAL_WIDTH = 35;
	public static final int HORIZONTAL_HEIGHT = 20;
	
	/**
	 * the width and height a tank will strech when it going to up or down
	 */
	public static final int VERTICAL_WIDTH = 20;
	public static final int VERTICAL_HEIGHT = 35;

	private int speed = 3;

	/**
	 * Constructor
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param direction direction a tank goes towards
	 * @param type type of tank, player or enemy
	 */
	public Tank(int x, int y, int direction, Color type) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.type = type;
		this.muzzle = new Muzzle();
		this.setMuzzle();
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * specify the x,y coordinates of muzzle
	 */
	public void setMuzzle() {
		if (this.direction == Tank.TO_RIGHT) {
			this.muzzle.setX(x + 35);
			this.muzzle.setY(y + 10);
		}

		if (this.direction == Tank.TO_UP) {
			this.muzzle.setX(x + 10);
			this.muzzle.setY(y);
		}

		if (this.direction == Tank.TO_LEFT) {
			this.muzzle.setX(x);
			this.muzzle.setY(y + 10);
		}

		if (this.direction == Tank.TO_DOWN) {
			this.muzzle.setX(x + 10);
			this.muzzle.setY(y + 35);
		}
	}

	public Muzzle getMuzzle() {
		return muzzle;
	}

	public Vector<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(Vector<Bullet> bullets) {
		this.bullets = bullets;
	}

	public Color getType() {
		return type;
	}

	public void setType(Color type) {
		this.type = type;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void leftMove() {
		this.x -= this.speed;
	}

	public void rightMove() {
		this.x += this.speed;
	}

	public void upMove() {
		this.y -= this.speed;
	}

	public void downMove() {
		this.y += this.speed;
	}

	public void turnLeft() {
		this.setDirection(Tank.TO_LEFT);
	}

	public void turnRight() {
		this.setDirection(Tank.TO_RIGHT);
	}

	public void turnUp() {
		this.setDirection(Tank.TO_UP);
	}

	public void turnDown() {
		this.setDirection(Tank.TO_DOWN);
	}

	/**
	 * This method will let a tank shot a bullet
	 */
	public void Shot() {
		//check if a tank has already fire maximun number of bullets
		if (this.bullets.size() < Tank.MAX_BULLETS) { 

			if (this.type.equals(Tank.PLAYER)) {
				
				//create a bullet and set its type to be a player's bullet 
				Bullet bullet = new Bullet(this.getMuzzle().getX(), this.getMuzzle().getY(), this.getDirection(),
						Bullet.PLAYER_BULLET);
				//make the bullet a thread and start it
				Thread t = new Thread(bullet);
				t.start();
				this.bullets.add(bullet);
			}

			if (this.type.equals(Tank.ENEMY)) {
				//create a bullet and set its type to be an enemy's bullet
				Bullet bullet = new Bullet(this.getMuzzle().getX(), this.getMuzzle().getY(), this.getDirection(),
						Bullet.ENEMY_BULLET);
				//make the bullet a thread and start it
				Thread t = new Thread(bullet);
				t.start();
				this.bullets.add(bullet);
			}
		}
	}

	/**
	 * check to see if this tank hits an entity
	 * @param e entity
	 * @return true if hits, otherwise false
	 */
	public boolean hitEntity(Entity e) {

		if (this.getDirection() == Tank.TO_UP) {
			return x >= e.getMinX() - Tank.VERTICAL_WIDTH && x <= e.getMaxX() && y - speed <= e.getMaxY()
					&& y - speed >= e.getMinY();
		}

		if (this.getDirection() == Tank.TO_DOWN) {
			return x >= e.getMinX() - Tank.VERTICAL_WIDTH && x <= e.getMaxX()
					&& y + Tank.VERTICAL_HEIGHT + speed <= e.getMaxY()
					&& y + Tank.VERTICAL_HEIGHT + speed >= e.getMinY();
		}

		if (this.getDirection() == Tank.TO_LEFT) {
			return x - speed >= e.getMinX() && x - speed <= e.getMaxX() && y <= e.getMaxY()
					&& y >= e.getMinY() - Tank.HORIZONTAL_HEIGHT;
		}

		if (this.getDirection() == Tank.TO_RIGHT) {
			return x + Tank.HORIZONTAL_WIDTH + speed >= e.getMinX() && x + Tank.HORIZONTAL_WIDTH + speed <= e.getMaxX()
					&& y <= e.getMaxY() && y >= e.getMinY() - Tank.HORIZONTAL_HEIGHT;
		}

		return false;
	}

	/**
	 * 
	 * @param entities all the entities to be checked
	 * @return true if the tank hits either of entity, otherwise false
	 */
	public boolean hasEntityInFront(Vector<Entity> entities) {
		for (Entity e : entities) {

			if (e.isAlive()) {
				if (this.hitEntity(e)) {
					return true;
				}
			}
		}

		return false;

	}

	public void move() {

		//get all entities on the canvass
		Vector<Entity> entities = MyPanel.getEntities();
		boolean hasEntityInFront = this.hasEntityInFront(entities);
		if (this.getDirection() == Tank.TO_DOWN) {
			
			//if a tank is going down and it will hit the boundary or another entity
			//then turn up and keep moving to a different direction
			if (!this.isBoundryHit() && !hasEntityInFront) {
				this.downMove();
			} else {
				this.turnUp();
				this.upMove();
			}
		}
		
		if (this.getDirection() == Tank.TO_UP) {
			//if a tank is going up and it will hit the boundary or another entity
			//then turn down and keep moving to a different direction
			if (!this.isBoundryHit() && !hasEntityInFront) {
				this.upMove();
			} else {
				this.turnDown();
				;
				this.downMove();
				;
			}
		}
		
		if (this.getDirection() == Tank.TO_LEFT) {
			
			//if a tank is going left and it will hit the boundary or another entity
			//then turn right and keep moving to a different direction
			if (!this.isBoundryHit() && !hasEntityInFront) {
				this.leftMove();
			} else {
				this.turnRight();
				this.rightMove();
			}
		}
		if (this.getDirection() == Tank.TO_RIGHT) {
			
			//if a tank is going right and it will hit the boundary or another entity
			//then turn left and keep moving to a different direction
			if (!this.isBoundryHit() && !hasEntityInFront) {
				this.rightMove();
			} else {
				this.turnLeft();
				this.leftMove();
			}
		}
		
		//reset the x,y coordinates of muzzle
		this.setMuzzle();
	}

	/**
	 * check to see if this tank will hit the boundary if keep going
	 * @return return true if it will, otherwise false
	 */
	public boolean isBoundryHit() {
		if (this.getDirection() == Tank.TO_LEFT) {
			return (this.getMuzzle().getX() - this.getSpeed()) <= MyPanel.BORDER_MIN_X;
		}

		if (this.getDirection() == Tank.TO_RIGHT) {
			return (this.getMuzzle().getX() + this.getSpeed()) >= MyPanel.BORDER_MAX_X;
		}

		if (this.getDirection() == Tank.TO_UP) {
			return (this.getMuzzle().getY() - this.getSpeed()) <= MyPanel.BORDER_MIN_Y;
		}

		if (this.getDirection() == Tank.TO_DOWN) {
			return (this.getMuzzle().getY() + this.getSpeed()) >= MyPanel.BORDER_MAX_y;
		}
		return false;
	}

	public int getMaxX() {
		// TODO Auto-generated method stub
		if (this.direction == Tank.TO_LEFT || this.direction == Tank.TO_RIGHT) {
			return this.x + Tank.HORIZONTAL_WIDTH;
		} else {
			return this.x + Tank.VERTICAL_WIDTH;
		}
	}

	public int getMaxY() {
		// TODO Auto-generated method stub
		if (this.direction == Tank.TO_LEFT || this.direction == Tank.TO_RIGHT) {
			return this.y + Tank.HORIZONTAL_HEIGHT;
		} else {
			return this.y + Tank.VERTICAL_HEIGHT;
		}
	}

	public int getMinX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	public int getMinY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public void run() {
		// TODO Auto-generated method stub
		this.setSpeed(5);

		while (this.isAlive()) {
			try {
				Thread.sleep(130);
				int random_num_1 = (int) (Math.random() * 100 % 13);
				int random_num_2 = (int) (Math.random() * 100 % 4);
				
				//use random numbers to let tank move randomly
				if (random_num_1 == 2) {
					if (random_num_2 == 0) {
						this.turnUp();
					}
					if (random_num_2 == 1) {
						this.turnDown();
					}
					if (random_num_2 == 2) {
						this.turnLeft();
					}
					if (random_num_2 == 3) {
						this.turnRight();
					}
				}
				this.move();

				int random_num_shot_count = (int) (Math.random() * 100 % 18);
				if (random_num_shot_count == 1) {
					this.Shot();
				}
				
				//if player wins or losses, stop the thread
				if(MyPanel.PLAYER_LOSSES || MyPanel.PLAYER_WINS) {
					break;
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
