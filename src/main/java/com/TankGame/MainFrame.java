package com.TankGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import com.TankGame.entities.Tank;

public class MainFrame extends JFrame{

	private MyPanel panel;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mf = new MainFrame();
	}
	
	public MainFrame() {
		panel = new MyPanel();
		this.add(panel);
		Thread t = new Thread(panel);
		t.start();
		this.setSize(MyPanel.BORDER_MAX_X+100, MyPanel.BORDER_MAX_y+100);
		this.setVisible(true);
		this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("key pressed : "+e.getKeyChar());
				if(e.getKeyChar()=='d') {
					panel.rightMoveTank();
				}
				
				if(e.getKeyChar()=='a') {
					panel.leftMoveTank();
				}
				
				if(e.getKeyChar() == 'w') {
					panel.upMoveTank();
				}
				
				if(e.getKeyChar() == 's') {
					panel.downMoveTank();
				}
				
				if(e.getKeyChar() == 'j') {
					panel.tankShot();
				}
				
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
