package com.TankGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

import com.TankGame.entities.Barrier;
import com.TankGame.entities.Bomb;
import com.TankGame.entities.Bullet;
import com.TankGame.entities.Entity;
import com.TankGame.entities.Tank;
import com.TankGame.entities.Wall;
/**
 * 
 * @author Zhang's laptop
 * This is the main panel that all the entities will appear
 */
public class MyPanel extends JPanel implements Runnable {

	//player's tank
	private Tank tank;
	//enemies
	private Vector<Tank> enemies = new Vector<Tank>();
	
	//bombs
	private Vector<Bomb> bombs = new Vector<Bomb>();
	
	//walls
	private Vector<Wall> walls = new Vector<Wall>();

	public static Vector<Entity> entities = new Vector<Entity>();

	/**
	 * three images that will create the scene of explode when a
	 * tank is hit by bullet 
	 */
	private Image img_1 = null;
	private Image img_2 = null;
	private Image img_3 = null;

	public static final int BORDER_MIN_X = 0;
	public static final int BORDER_MIN_Y = 0;
	public static final int BORDER_MAX_X = 600;
	public static final int BORDER_MAX_y = 450;
	public static int NUMBER_OF_ENEMIES = 4;
	public static int NUMBER_OF_WALLS_IN_ONE_ROW = 16;
	public static boolean PLAYER_WINS = false;
	public static boolean PLAYER_LOSSES = false;

	/**
	 * Constructor
	 */
	public MyPanel() {

		//create a player's tank and put it to one of entities on panel
		tank = new Tank(MyPanel.BORDER_MAX_X / 2 - 10, MyPanel.BORDER_MAX_y - 35, Tank.TO_UP, Tank.PLAYER);
		entities.add(tank);
		
		//load images
		this.img_1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explode_1.jpg"));
		this.img_2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explode_2.jpg"));
		this.img_3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/explode_3.jpg"));		
		
		//specify the x,y coordinates of start point of an enemy tank
		int enmey_start_point_x = MyPanel.BORDER_MAX_X / 2 - 50;
		int enmey_start_point_y = MyPanel.BORDER_MIN_Y;

		//create a enemy's tank and put them to entities on panel
		for (int i = 0; i < MyPanel.NUMBER_OF_ENEMIES; i++) {
			Tank enemy = new Tank(enmey_start_point_x + (80 * i), enmey_start_point_y, Tank.TO_DOWN, Tank.ENEMY);
			Thread t_enemy = new Thread(enemy);
			t_enemy.start();
			this.enemies.add(enemy);
			entities.add(enemy);
		}
		
		//specify the x,y coordinates of start point of walls
		int wall_start_point_x_1 = 100;
		int wall_start_point_y_1 = 100;

		//create walls and put them to entities on panel
		for (int i = 0; i < MyPanel.NUMBER_OF_WALLS_IN_ONE_ROW; i++) {
			Wall w_1 = new Wall(wall_start_point_x_1 + i * Wall.WIDTH, wall_start_point_y_1);
			this.walls.add(w_1);
			entities.add(w_1);
		}
		
		//specify the x,y coordinates of start point of another row of walls
		int wall_start_point_x_2 = 100;
		int wall_start_point_y_2 = 280;
		
		for (int i = 0; i < MyPanel.NUMBER_OF_WALLS_IN_ONE_ROW; i++) {
			Wall w_2 = new Wall(wall_start_point_x_2 + i * Wall.WIDTH, wall_start_point_y_2);
			this.walls.add(w_2);
			entities.add(w_2);
		}
	}

	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}

	public static Vector<Entity> getEntities() {
		return entities;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		try {
			g.setColor(Color.BLACK);
			
			//draw the area of this game
			g.fillRect(MyPanel.BORDER_MIN_X, MyPanel.BORDER_MIN_Y, MyPanel.BORDER_MAX_X, MyPanel.BORDER_MAX_y);
			// g.setColor(Color.CYAN);
			// g.fill3DRect(tank.getX(), tank.getY(), 30, 5,false);
			// g.fill3DRect(tank.getX(), tank.getY()+15, 30, 5,false);
			// g.fill3DRect(tank.getX()+5, tank.getY()+5, 20, 10,false);
			// g.drawLine(tank.getX()+25, tank.getY()+10, tank.getX()+35, tank.getY()+10);
			// g.drawOval(tank.getX()+10, tank.getY()+5, 10, 10);

			//try to preload the image to memory
			//not a good solution, I will see if there is better one
			g.drawImage(img_1, 100, 500, 50, 50, this);
			g.drawImage(img_2, 150, 500, 50, 50, this);
			g.drawImage(img_3, 200, 500, 50, 50, this);
			
			// draw the player's tank
			if (tank.isAlive()) {
				drawTank(tank, g);
			}

			// draw enemies' tanks
			Vector<Tank> theEnemies = this.enemies;
			for (Tank et : theEnemies) {
				if (et.isAlive()) {
					drawTank(et, g);
				}
			}

			//draw player's bullets
			drawBullets(tank, g);

			//draw enemies' bullets
			drawBulletsForMultiTanks(theEnemies, g);

			//draw bombs if an enemy is hit
			if (this.bombs.size() > 0) {
				drawBombs(this.bombs, g);
			}
			
			//draw walls
			drawWalls(walls, g);
			
			//Tell player he/she wins
			if(MyPanel.PLAYER_WINS) {
				g.setColor(Color.RED);
				Font font = new Font("TimesRoman", Font.BOLD, 30);
				g.setFont(font);
				g.drawString("CONGRATS, YOU WIN", 150, 200);
			}
			
			if(MyPanel.PLAYER_LOSSES) {
				g.setColor(Color.RED);
				Font font = new Font("TimesRoman", Font.BOLD, 30);
				g.setFont(font);
				g.drawString("SORRY, YOU LOSE", 150, 200);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("A bullet is destroyed");
		}

	}

	/**
	 * draw a tank
	 * @param tank the tank to be drew
	 * @param g
	 */
	public void drawTank(Tank tank, Graphics g) {

		//specify the color of a tank
		g.setColor(tank.getType());

		if (tank.getDirection() == Tank.TO_RIGHT) {
			drawTankToRight(tank, g);
		}

		if (tank.getDirection() == Tank.TO_LEFT) {
			drawTankToLeft(tank, g);
		}

		if (tank.getDirection() == Tank.TO_UP) {
			drawTankToUp(tank, g);
		}

		if (tank.getDirection() == Tank.TO_DOWN) {
			drawTankToDown(tank, g);
		}
	}

	/**
	 * draw a tank which is pointing to right
	 * @param tank the tank to be drew
	 * @param g
	 */
	public void drawTankToRight(Tank tank, Graphics g) {
		g.fill3DRect(tank.getX(), tank.getY(), 30, 5, false);
		g.fill3DRect(tank.getX(), tank.getY() + 15, 30, 5, false);
		g.fill3DRect(tank.getX() + 5, tank.getY() + 5, 20, 10, false);
		g.drawLine(tank.getX() + 25, tank.getY() + 10, tank.getX() + 35, tank.getY() + 10);
		g.drawOval(tank.getX() + 10, tank.getY() + 5, 10, 10);
	}

	/**
	 * draw a tank which is pointing to left
	 * @param tank the tank to be drew
	 * @param g
	 */
	public void drawTankToLeft(Tank tank, Graphics g) {
		g.fill3DRect(tank.getX() + 5, tank.getY(), 30, 5, false);
		g.fill3DRect(tank.getX() + 5, tank.getY() + 15, 30, 5, false);
		g.fill3DRect(tank.getX() + 10, tank.getY() + 5, 20, 10, false);
		g.drawOval(tank.getX() + 15, tank.getY() + 5, 10, 10);
		g.drawLine(tank.getX(), tank.getY() + 10, tank.getX() + 10, tank.getY() + 10);
	}

	/**
	 * draw a tank which is pointing up
	 * @param tank the tank to be drew
	 * @param g
	 */
	public void drawTankToUp(Tank tank, Graphics g) {

		g.fill3DRect(tank.getX(), tank.getY() + 5, 5, 30, false);
		g.fill3DRect(tank.getX() + 15, tank.getY() + 5, 5, 30, false);
		g.fill3DRect(tank.getX() + 5, tank.getY() + 10, 10, 20, false);
		g.drawOval(tank.getX() + 5, tank.getY() + 15, 10, 10);
		g.drawLine(tank.getX() + 10, tank.getY(), tank.getX() + 10, tank.getY() + 10);
	}

	/**
	 * draw a tank which is pointing down
	 * @param tank the tank to be drew
	 * @param g
	 */
	public void drawTankToDown(Tank tank, Graphics g) {

		g.fill3DRect(tank.getX(), tank.getY(), 5, 30, false);
		g.fill3DRect(tank.getX() + 5, tank.getY() + 5, 10, 20, false);
		g.fill3DRect(tank.getX() + 15, tank.getY(), 5, 30, false);
		g.drawOval(tank.getX() + 5, tank.getY() + 10, 10, 10);
		g.drawLine(tank.getX() + 10, tank.getY() + 25, tank.getX() + 10, tank.getY() + 35);
	}

	/**
	 * draw all bullets that a tank fires
	 * @param tank the tank that fires the bullets
	 * @param g
	 */
	public void drawBullets(Tank tank, Graphics g) {

		//get all bullets of this tank
		Vector<Bullet> bullets = tank.getBullets();

		if (bullets.size() > 0) {

			for (Bullet b : bullets) {
				//specify the color of bullet
				//according to the type of bullet
				g.setColor(b.getType());
				if (b.isAlive()) { // check if the bullet is alive
					if (tank.getDirection() == Tank.TO_RIGHT) {
						g.drawOval(b.getX(), b.getY() - 1, 2, 2);
					}
					if (tank.getDirection() == Tank.TO_LEFT) {
						g.drawOval(b.getX() - 1, b.getY() - 1, 2, 2);
					}
					if (tank.getDirection() == Tank.TO_UP) {
						g.drawOval(b.getX() - 2, b.getY() - 2, 2, 2);
					}
					if (tank.getDirection() == Tank.TO_DOWN) {
						g.drawOval(b.getX() - 1, b.getY() + 2, 2, 2);
					}
				} else {
					//remove the bullet from bullets if it is not alive
					//set the bullet to be null
					bullets.remove(b); 
					b = null;
				}
			}
		}

	}

	/**
	 * draw all the bullets of all tanks
	 * @param tanks tanks that fire bullets
	 * @param g
	 */
	public void drawBulletsForMultiTanks(Vector<Tank> tanks, Graphics g) {
		for (Tank tank : tanks) {
			drawBullets(tank, g);
		}
	}

	/**
	 * draw all bombs
	 * @param bombs
	 * @param g
	 */
	public void drawBombs(Vector<Bomb> bombs, Graphics g) {

		for (Bomb b : bombs) {

			if (b.isAlive()) {//check if a bomb exist

				//System.out.println("A Bomb explodes");
				
				//draw different images according to the lifecount
				//to make an animation of explode
				if (b.getLifeCount() > 8) {
					g.drawImage(img_1, b.getX(), b.getY(), 30, 30, this);
				} else if (b.getLifeCount() > 4) {
					g.drawImage(img_2, b.getX(), b.getY(), 30, 30, this);
				} else {
					g.drawImage(img_3, b.getX(), b.getY(), 30, 30, this);
				}
				b.lifeDeduction();
			}
			
		}

	}

	/**
	 * draw walls
	 * @param walls
	 * @param g
	 */
	public void drawWalls(Vector<Wall> walls, Graphics g) {
		
		g.setColor(Wall.TYPE_IS_WALL);
		
		for(Wall wall : walls) {
			
			if(wall.isAlive()) {
				g.fill3DRect(wall.getX(), wall.getY(), Wall.WIDTH, Wall.HEIGHT, false);
			}
		}
		
	}
	
	/**
	 * move the player's tank to right by number of pixels of its speed
	 */
	public void rightMoveTank() {

		if (tank.getDirection() != Tank.TO_RIGHT) {
			//if the tank is not pointing to right
			//let it turn right
			tank.setDirection(Tank.TO_RIGHT);
		} else {
			if (!isTankHitBorder(tank) && !tank.hasEntityInFront(entities) ) {
				//if the tank is pointing to right and will not hit boundary or other
				//entities, let it move to right
				this.tank.rightMove();
			}
		}
		//reset the x,y coordinates of muzzle
		this.tank.setMuzzle();
		// this.repaint();
	}

	/**
	 * move the player's tank to left by number of pixels of its speed
	 */
	public void leftMoveTank() {

		if (tank.getDirection() != Tank.TO_LEFT) {
			//if the tank is not pointing to left
			//let it turn left
			tank.setDirection(Tank.TO_LEFT);
		} else {
			if (!isTankHitBorder(tank) && !tank.hasEntityInFront(entities) ) {
				//if the tank is pointing to left and will not hit boundary or other
				//entities, let it move to left
				tank.leftMove();
			}
		}
		//reset the x,y coordinates of muzzle
		this.tank.setMuzzle();
		// this.repaint();
	}

	/**
	 * move the player's tank up by number of pixels of its speed
	 */
	public void upMoveTank() {

		if (tank.getDirection() != Tank.TO_UP) {
			tank.setDirection(Tank.TO_UP);
		} else {
			if (!isTankHitBorder(tank) && !tank.hasEntityInFront(entities)) {
				tank.upMove();
			}
		}
		this.tank.setMuzzle();
		// this.repaint();
	}

	/**
	 * move the player's tank down by number of pixels of its speed
	 */
	public void downMoveTank() {

		if (tank.getDirection() != Tank.TO_DOWN) {
			tank.setDirection(Tank.TO_DOWN);
		} else {
			if (!isTankHitBorder(tank) && !tank.hasEntityInFront(entities)) {
				tank.downMove();
			}
		}
		this.tank.setMuzzle();
		// this.repaint();
	}

	public boolean isTankHitBorder(Tank tank) {

//		if (tank.getDirection() == Tank.TO_LEFT) {
//			return (tank.getMuzzle().getX() - tank.getSpeed()) <= MyPanel.BORDER_MIN_X;
//		}
//
//		if (tank.getDirection() == Tank.TO_RIGHT) {
//			return (tank.getMuzzle().getX() + tank.getSpeed()) >= MyPanel.BORDER_MAX_X;
//		}
//
//		if (tank.getDirection() == Tank.TO_UP) {
//			return (tank.getMuzzle().getY() - tank.getSpeed()) <= MyPanel.BORDER_MIN_Y;
//		}
//
//		if (tank.getDirection() == Tank.TO_DOWN) {
//			return (tank.getMuzzle().getY() + tank.getSpeed()) >= MyPanel.BORDER_MAX_y;
//		}
//		return false;

		return tank.isBoundryHit();
	}

	/**
	 * let the player's tank shot
	 */
	public void tankShot() {

//		if(this.tank.getBullets().size()< Tank.MAX_BULLETS) {
//			this.tank.Shot();
//		}
		this.tank.Shot();
		// this.repaint();
	}

	/**
	 * check if a bullet hits a tank;
	 * @param bullet
	 * @param tank
	 */
	public void checkTankIfIsHit(Bullet bullet, Tank tank) {

		//get the x,y of bullet and tank
		int bx = bullet.getX();
		int by = bullet.getY();
		int tx = tank.getX();
		int ty = tank.getY();

		if (tank.isAlive()) {
			if (tank.getDirection() == Tank.TO_RIGHT || tank.getDirection() == Tank.TO_LEFT) {
				
				//check if a bullet falls in the area of the tank
				if (bx >= tx && bx <= tx + 35 && by >= ty && by <= ty + 20) {
					//set bullet and tank to be dead
					bullet.setAlive(false);
					tank.setAlive(false);
					//reduce the number of enemy by 1 if the
					//tank got hit is an enemy
					if(tank.getType().equals(Tank.ENEMY)) {
						MyPanel.NUMBER_OF_ENEMIES--;
					}
					
					//create a bomb and put it in bombs
					//this bomb will be drew on panel to
					//create an animation of explode
					Bomb bomb = new Bomb(tank.getX(), tank.getY(), true);
					this.bombs.add(bomb);
				}
			}

			if (tank.getDirection() == Tank.TO_UP || tank.getDirection() == Tank.TO_DOWN) {
				if (bx >= tx && bx <= tx + 20 && by >= ty && by <= ty + 35) {
					bullet.setAlive(false);
					tank.setAlive(false);
					
					if(tank.getType().equals(Tank.ENEMY)) {
						MyPanel.NUMBER_OF_ENEMIES--;
					}
					
					Bomb bomb = new Bomb(tank.getX(), tank.getY(), true);
					this.bombs.add(bomb);
				}
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			// System.out.println("Now repainting .. ");
			try {
				Thread.sleep(50);
				Vector<Bullet> player_bullets = this.tank.getBullets();
				Vector<Tank> theEnemies = this.enemies;

				//check if enemy tanks are hit by player's bullet
				for (Bullet b : player_bullets) {
					for (Tank t : theEnemies) {
						checkTankIfIsHit(b, t);
					}
				}

				//check if player's tank is hit by enemy's bullet
				for(Tank enemy: theEnemies) {
					for(Bullet b : enemy.getBullets()) {
						checkTankIfIsHit(b, tank);
					}
				}
				
				//check if a bullet hit an entity
				for (Bullet b : player_bullets) {
					b.hitEntities(entities);
				}
				
				//check if an enemy tank hit an entity
				for (Tank enemey : theEnemies) {
					for (Bullet b : enemey.getBullets()) {
						b.hitEntities(entities);
					}
				}
				
				//if no enemies, set player to win
				if(MyPanel.NUMBER_OF_ENEMIES<=0) {
					MyPanel.PLAYER_WINS=true;
				}
				
				//if player is killed, set player to lose
				if(!this.tank.isAlive()) {
					MyPanel.PLAYER_LOSSES=true;
				}
				
				this.repaint();
				
//				if(MyPanel.PLAYER_LOSSES || MyPanel.PLAYER_WINS) {
//					break;
//				}
						
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
