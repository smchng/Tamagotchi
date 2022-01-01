package graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import interactions.Objects;
import processing.core.PApplet;
import processing.core.PVector;
import tools.Util;

/*
 * This is the main class for your pet
 * It draws the pet, changes the states for the pet to age, as well as handle collisions
 * If the mouse is dragging it, it will move around
 * It will also make the pet sick or hungry for the player to interact with
 * To show it, text will appear and the pet will change visually
 */

public class Pet implements Objects{
	
	public static PVector pos;
	private double sizeX, sizeY, animate, addAnimate, gender, ageTimer, dirtX, dirtY, healthTimer, happyTimer, dirtTimer;
	private static BufferedImage petBaby, petKid, petAdult;
	private PApplet pa;
	private float xstart, xnoise, ynoise;
	public static int stateP, genderState, health, happy;
	private final static int BABY=0;
	private final static int KID=1;
	private final static int ADULT=2;
	public static boolean sick;
	public static double sickTimer, scale,  eatTimer, dirtAmount;
	
	public Pet(Dimension wnd) {
		pos=new PVector(wnd.width/2-10, wnd.height/2+50);
		animate=0;
		dirtTimer=0;
		ageTimer=0;
		addAnimate=0.0001;
		dirtX = 25;
		dirtY = 20;
		sick=false;
		health=0;
		gender=Math.random()*2;
		pa = new PApplet();
		xstart = Util.random(10);
		xnoise = xstart;
		ynoise = Util.random(5);
	}
	
	private void ageChange() {
		if (ageTimer<2000) stateP=0;
		else if (ageTimer>2000&&ageTimer<6000&&!sick&&health==5)stateP=1;
		else if (ageTimer>6000&&!sick&&health==5) stateP=2;
	}
	
	private void drawChanges() {
		animate+=addAnimate;
		if (animate>0.008||animate<-0.008) addAnimate*=-1;
		
		if (stateP==BABY&&!sick)scale=0.25;
		if (stateP==KID&&!sick)scale=0.2;
		if (stateP==ADULT&&!sick)scale=0.15;
		
		healthTimer++;
		happyTimer++;
		if (healthTimer>250) {
			if(health>=1)health--;
			else if(health<1) health=0;
			healthTimer=0;
		}
		if (happyTimer>400) {
			if(happy>=1)happy--;
			else if(happy<1)happy=0;
			happyTimer=0;
		}
		
		if(!sick) ageTimer++;
		dirtTimer++;
		
		eatTimer++;
		if(eatTimer>500&&health>5&&!sick) {
			sick=true;
			if (stateP==BABY)scale=0.3;
			if (stateP==KID)scale=0.25;
			if (stateP==ADULT)scale=0.2;
		}
		
		sickTimer++;
		if(sickTimer>1000) {
			sick=true;
			if(stateP==BABY&&gender<1)petBaby=Util.loadImage("imgs/babyPSick.png");
			else if(stateP==KID&&gender<1)petKid=Util.loadImage("imgs/kidPSick.png");
			else if(stateP==ADULT&&gender<1)petAdult=Util.loadImage("imgs/adultPSick.png");
			if(stateP==BABY&&gender>=1)petBaby=Util.loadImage("imgs/babySick.png");
			else if(stateP==KID&&gender>=1)petKid=Util.loadImage("imgs/kidSick.png");
			else if(stateP==ADULT&&gender>=1)petAdult=Util.loadImage("imgs/adultSick.png");
		}
		if (gender >= 1&&!sick) {
			petBaby=Util.loadImage("imgs/baby.png");
			petKid=Util.loadImage("imgs/kid.png");
			petAdult=Util.loadImage("imgs/adult.png");
			genderState=2;
		}
		else if (gender < 1&&!sick) {
			petBaby=Util.loadImage("imgs/babyP.png");
			petKid=Util.loadImage("imgs/kidP.png");
			petAdult=Util.loadImage("imgs/adultP.png");
			genderState=1;
		}
	}
	
	public void draw(Graphics2D g2, Dimension wnd, int gameState) {
		ageChange();
		drawChanges();
		
		if (gameState==1&&stateP==BABY) {
			AffineTransform baby = g2.getTransform();
			g2.translate(pos.x,pos.y);
			g2.scale(scale,scale+animate);
			g2.drawImage(petBaby, -petBaby.getWidth()/2, -petBaby.getHeight()/2, null);
			g2.setTransform(baby);	
		} else if (gameState==1&&stateP==KID) {
			AffineTransform kid = g2.getTransform();
			g2.translate(pos.x,pos.y);
			g2.scale(scale,scale+animate);
			g2.drawImage(petKid, -petKid.getWidth()/2, -petKid.getHeight()/2, null);
			g2.setTransform(kid);	
		}	else if (gameState==1&&stateP==ADULT) {
			AffineTransform adult = g2.getTransform();
			g2.translate(pos.x,pos.y);
			g2.scale(scale,scale+animate);
			g2.drawImage(petAdult, -petAdult.getWidth()/2, -petAdult.getHeight()/2, null);
			g2.setTransform(adult);	
		}
		
		if(gameState==1)dirty(g2);
	}
	
	private void dirty(Graphics2D g2) {
		float noiseFactor;
		AffineTransform dirt = g2.getTransform();
		g2.translate(pos.x-dirtX, pos.y-dirtY);

		for(int y=0; y <=5; y += 1) {
			ynoise += 0.1;
			xnoise = xstart;
			for(int x= 0; x<=5; x+=1) {
				xnoise+= 0.1;
				noiseFactor = pa.noise(xnoise,ynoise);
				
				float edgeSize = noiseFactor * 35;
				g2.setColor(new Color(69, 35, 32));
				
				if(dirtTimer>500) {
					if(dirtAmount<=4)dirtAmount++;
					else dirtAmount=5;
					dirtTimer=0;
				}
				
				if (dirtAmount>=1) {
					AffineTransform at1 = g2.getTransform();
					g2.translate(x,y);
					g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/2, edgeSize*noiseFactor, edgeSize/2*noiseFactor));
					g2.setTransform(at1);
				}
				if (dirtAmount>=2) {
					AffineTransform at2 = g2.getTransform();
					g2.translate(x+45,y+20);
					g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/2, edgeSize*noiseFactor, edgeSize/2*noiseFactor));
					g2.setTransform(at2);
				}
				if (dirtAmount>=3) {
					AffineTransform at3 = g2.getTransform();
					g2.translate(x-10,y+30);
					g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/2, edgeSize*noiseFactor, edgeSize/2*noiseFactor));
					g2.setTransform(at3);
				}
				if (dirtAmount>=4) {
				AffineTransform at4 = g2.getTransform();
					g2.translate(x+30,y+55);
					g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/2, edgeSize*noiseFactor, edgeSize/2*noiseFactor));
				g2.setTransform(at4);
				}
				if (dirtAmount>=5) {
				AffineTransform at5 = g2.getTransform();
					g2.translate(x+50,y-8);
					g2.fill(new Ellipse2D.Float(-edgeSize/2, -edgeSize/2, edgeSize*noiseFactor, edgeSize/2*noiseFactor));
				g2.setTransform(at5);
				}
			}

		}
		g2.setTransform(dirt);
	}
	
	public void actionText(Graphics2D g, Dimension wnd, int gameState) {
		if (gameState==1) {
			String icon=null;
			if (sick) icon="Your pet is sick!";
			else if (dirtAmount>=1) icon="Clean your pet!";
			else if (health<3) icon="Feed your pet!";
			else icon="";
			
			AffineTransform at = g.getTransform();
			g.translate(200, 390);
	
			Font f = new Font("Gill Sans", Font.BOLD, 17);
			g.setFont(f);
			FontMetrics metrics = g.getFontMetrics(f);
			
			float textWidth = metrics.stringWidth(icon);
			float textHeight = metrics.getHeight();
			float margin = 12, spacing = 2;
	
			g.setColor(new Color (108,126,159));
			g.drawString(icon, -textWidth/2,
					-75.75f - margin - (textHeight + spacing) * 2f);
			
			g.setTransform(at);
		}
		
	}
		
	public static boolean collided(double x, double y) {
		boolean collide=false;
		if(stateP==BABY) {
			if (x > (pos.x-((double) petBaby.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petBaby.getWidth()/2)*scale) && 
				y > (pos.y-((double) petBaby.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petBaby.getHeight()/2)*scale)) 
				collide = true;
		} else if(stateP==KID) {
			if (x > (pos.x-((double) petKid.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petKid.getWidth()/2)*scale) && 
				y > (pos.y-((double) petKid.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petKid.getHeight()/2)*scale)) 
				collide = true;
		} else if(stateP==ADULT) {
			if (x > (pos.x-((double) petAdult.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petAdult.getWidth()/2)*scale) && 
				y > (pos.y-((double) petAdult.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petAdult.getHeight()/2)*scale)) 
				collide = true;
		}
		return collide;
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if(stateP==BABY) {
			if (x > (pos.x-((double) petBaby.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petBaby.getWidth()/2)*scale) && 
				y > (pos.y-((double) petBaby.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petBaby.getHeight()/2)*scale)) 
				clicked = true;
		} else if(stateP==KID) {
			if (x > (pos.x-((double) petKid.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petKid.getWidth()/2)*scale) && 
				y > (pos.y-((double) petKid.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petKid.getHeight()/2)*scale)) 
				clicked = true;
		} else if(stateP==ADULT) {
			if (x > (pos.x-((double) petAdult.getWidth()/2)*scale) &&
				x < (pos.x+ ((double) petAdult.getWidth()/2)*scale) && 
				y > (pos.y-((double) petAdult.getHeight()/2)*scale) &&
				y < (pos.y + ((double) petAdult.getHeight()/2)*scale)) 
				clicked = true;
		}

		return clicked;
	}
	
	public void setPos(int x, int y, Dimension wnd) {
		pos.x=x;
		pos.y=y;
		wallCollision(x,y,wnd);
	}
	
	public PVector getPos() {
		return new PVector((int)pos.x,(int)pos.y);
	}
	
	private void wallCollision(int x, int y, Dimension wnd) {
		if (stateP==BABY) {
			if (pos.x<165) pos.x=165;
			if (pos.x>wnd.width-190) pos.x=wnd.width-190;
			if (pos.y<268) pos.y=268;
			if (pos.y>wnd.height-345) pos.y=wnd.height-345;
		} else if (stateP==KID) {
			if (pos.x<172) pos.x=172;
			if (pos.x>wnd.width-198) pos.x=wnd.width-198;
			if (pos.y<268) pos.y=285;
			if (pos.y>wnd.height-345) pos.y=wnd.height-345;
		} else if (stateP==ADULT) {
			if (pos.x<172) pos.x=172;
			if (pos.x>wnd.width-198) pos.x=wnd.width-198;
			if (pos.y<295) pos.y=295;
			if (pos.y>wnd.height-345) pos.y=wnd.height-345;
		}
		
	}
}
