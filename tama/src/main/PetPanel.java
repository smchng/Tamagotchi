package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import ddf.minim.*;
import graphics.Background;
import graphics.Icons;
import graphics.Pet;
import graphics.Poo;
import interactions.Buttons;
import interactions.Decorator;
import interactions.Egg;
import interactions.Objects;
import interactions.ObjectsConcreteFactory;
import interactions.ObjectsFactory;
import tools.MinimHelper;

/*Samantha Chung
 * Dec 6 2021
 * IAT 265 D102
 * 
 * This is the panel class for my game
 * It calls the methods and takes in the mouse interactions
 * It also draws the graphics by calling the images as well as using the decorator patterns
 * 
 * *****ABOUT THE GAME*****
 * This game is inspired by the toy made in 1990's 
 * Where you feed and raise your pet through the provided interaction buttons
 * 
 */

public class PetPanel extends JPanel implements ActionListener{
	
	private Dimension windowSize;
	private JFrame frame;
	private Timer timer;
	private Objects[] obj= new Objects[4];
	
	private int mouseX, mouseY, gameCounter;
	private boolean iconClicked;
	
	private int state;
	private final int INTRO = 0;
	private final int START = 1;
	private final int END = 2;
	
	private Minim minim;
	private AudioPlayer select, cancel, next, bgm;
	
	ObjectsFactory objF = new ObjectsConcreteFactory();
	Decorator dec, dec2;
	
	public PetPanel(JFrame frame) {
		this.windowSize = new Dimension(700,800);
		state=INTRO;
		gameCounter=0;
		iconClicked=false;
		this.frame=frame;
		
		dec= new Egg(0,0,"imgs/egg.png");
		dec= new Buttons(0,0,"imgs/buttons.png",dec);
		dec= new Buttons(0,0,"imgs/tamagotchi.png",dec);
		
		dec2= new Egg(0,0,"imgs/introBg.png");
		dec2= new Buttons(0,0,"imgs/introIcons.png", dec2);
		dec2= new Buttons(0,0,"imgs/introText.png", dec2);
		
		try {
			obj[0]=objF.createObject("bg", windowSize);
			obj[1]=objF.createObject("pet", windowSize);
			obj[2]=objF.createObject("icons", windowSize);
			obj[3]=objF.createObject("poo", windowSize);
			minim = new Minim(new MinimHelper());
			select=minim.loadFile("Select.mp3");
			cancel=minim.loadFile("Error.mp3");
			next=minim.loadFile("Switch.mp3");
			bgm=minim.loadFile("bgm.mp3");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		timer = new Timer(30, this);
		timer.start();
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		MyMouseMotionListener m2 = new MyMouseMotionListener();
		addMouseMotionListener(m2);
		
		bgm.loop();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Draws the backgrounds of the window
		if (state==INTRO) {
			dec.draw(g2, windowSize);
			dec2.draw(g2, windowSize);
		}
		
		//Draws the mojor classes and provides the action texts
		for (int i=0;i<obj.length;i++) {
			obj[i].draw(g2, windowSize, state);
			obj[i].actionText(g2, windowSize,state);
		}
		
		//Calls the action method to  change the icon states
		((Icons) obj[2]).action(mouseX,mouseY,g2, windowSize, 35);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		//Ends game if the requirements are met
		gameCounter++;
		if (gameCounter>8000&&Pet.stateP==2) state=2;
		
		repaint();
	}
	
	public class MyMouseListener extends MouseAdapter {
		//Checks where the mouse clicks and changes the class state depending on what is clicked
		public void mouseClicked(MouseEvent e) {
			if (state==INTRO) {
				for(int i=0;i<obj.length;i++) {
					if(((Icons) obj[2]).startClicked(mouseX, mouseY, windowSize)) {
						if (state==0) state = 1;
					}
				}
			}
			
			//Starts and ends the game if the button is clicked
			if (state==START) {
				((Icons) obj[2]).clicked(mouseX, mouseY, windowSize);
				((Poo) obj[3]).clicked(mouseX, mouseY);
			}
			if(state==END) {
				for(int i=0;i<obj.length;i++) {
					if(((Icons) obj[2]).endClicked(mouseX, mouseY, windowSize)) {
						cancel.play(0);
						bgm.pause();
						frame.dispose();
						frame = new PetApp("Tamagotchi");
					}
				}
			}
				
		}
	
	}
	
	private class MyMouseMotionListener extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
		//Allows the pet ot be dragged around with the mouse clicks it
		public void mouseDragged(MouseEvent e) {
			for(int i=0;i<obj.length;i++) {
				if (((Pet) obj[1]).clicked(e.getX(), e.getY())&&state==1) {
					((Pet) obj[1]).setPos(e.getX(),e.getY(),windowSize);
				}
			}
		}
	}

}
