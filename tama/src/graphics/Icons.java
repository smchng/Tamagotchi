package graphics;
import java.awt.BasicStroke;
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
import java.util.ArrayList;

import tools.Util;
import javax.imageio.ImageIO;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import interactions.Objects;
import tools.MinimHelper;

/*This is the Icons class
 * It includes all the interaction icons that allow you to select interactions
 * It draws the icons that are displayed at the top of the class
 * As well as change the states to provide you with the correct tool depending on the icon selected
 * The different states provide different actions
 * Selecting the cleaning tool gives you access to the cleaning tool to wash your pet and so on...
 * 
 * It also includes other buttons like start and end buttons that changes the entire games state to proceed through the game
 */
public class Icons implements  Objects{

	private BufferedImage stats, statsW, feed,feedW, toilet, toiletW, talk, talkW, store, storeW, doc,docW, startButton, endButton;
	private BufferedImage statsScreen, sponge, heart, heartW, food;
	private int stateI, eraseTimer;
	private final int NONE = -1;
	private final int STATS = 0;
	private final int FEED = 1;
	private final int CLEAN = 2;
	private final int DOC = 3;
	
	private Minim minim;
	private AudioPlayer next, select;

	
	
	public Icons() {
		feed=Util.loadImage("imgs/feed.png");
		feedW=Util.loadImage("imgs/feedW.png");
		stats=Util.loadImage("imgs/stats.png");
		statsW=Util.loadImage("imgs/statsW.png");
		toilet=Util.loadImage("imgs/toilet.png");
		toiletW=Util.loadImage("imgs/toiletW.png");
		doc=Util.loadImage("imgs/doc.png");
		docW=Util.loadImage("imgs/docW.png");
		startButton=Util.loadImage("imgs/start.png");
		endButton=Util.loadImage("imgs/end.png");
		statsScreen=Util.loadImage("imgs/statsScren.png");
		sponge=Util.loadImage("imgs/sponge.png");
		heart=Util.loadImage("imgs/heart.png");
		heartW=Util.loadImage("imgs/heartW.png");
		food=Util.loadImage("imgs/food.png");
		
		minim = new Minim(new MinimHelper());
		next=minim.loadFile("Switch.mp3");
		select=minim.loadFile("Select.mp3");
		
		stateI=NONE;
		
	}
	
	//This method provides the player with the tools to clean, feed or help the pet
	//Depending on the method, it switches the graphics
	public void action(double x,double y, Graphics2D g2, Dimension wnd,  float d) {
		eraseTimer++;
		//The stats screen
		if (stateI==STATS) {
			AffineTransform stats = g2.getTransform();
			g2.translate(wnd.width/2-12,wnd.height/2-25);
			g2.scale(0.4,0.4);
			g2.drawImage(statsScreen, -statsScreen.getWidth()/2, -statsScreen.getHeight()/2, null);
			
			String name=null;
			if (Pet.genderState==2) {
				if(Pet.stateP==0)name="Baby Mametchi";
				else if(Pet.stateP==1)name="Young Mametchi";
				else if(Pet.stateP==2)name="Adult Mametchi";
			}
			else if (Pet.genderState==1) {
				if(Pet.stateP==0)name="Baby Memetchi";
				else if(Pet.stateP==1)name="Young Memetchi";
				else if(Pet.stateP==2)name="Adult Memetchi";
			}
			else name="";
			
			AffineTransform nameString = g2.getTransform();
				g2.translate(-70, 40);
	
				Font f = new Font("Gill Sans", Font.BOLD, 40);
				g2.setFont(f);
				FontMetrics metrics = g2.getFontMetrics(f);
				
				float textWidth = metrics.stringWidth(name);
				float textHeight = metrics.getHeight();
				float margin = 12, spacing = 2;
	
				g2.setColor(new Color (108,126,159));
				g2.drawString(name, -textWidth/2,
						-75.75f - margin - (textHeight + spacing) * 2f);
			g2.setTransform(nameString);
			
			AffineTransform health = g2.getTransform();
			g2.translate(-125, -75);
			g2.scale(0.15, 0.15);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.health>=1) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.health>=2) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.health>=3) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.health>=4) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.health>=5) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.setTransform(health);
			
			AffineTransform happy = g2.getTransform();
			g2.translate(-140, 25);
			g2.scale(0.15, 0.15);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.happy>=1) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.happy>=2) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.happy>=3) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.happy>=4) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.translate(500,0);
			g2.drawImage(heartW, -heartW.getWidth()/2, -heartW.getHeight()/2, null);
			if (Pet.happy>=5) g2.drawImage(heart, -heart.getWidth()/2, -heart.getHeight()/2, null);
			g2.setTransform(happy);
			
			AffineTransform age = g2.getTransform();
			g2.translate(-230,340);
			String ag=null;
			if(Pet.stateP==0)ag="0";
			else if(Pet.stateP==1)ag="1";
			else if(Pet.stateP==2)ag="2";
			else ag="";
			
			float textW = metrics.stringWidth(ag);
			float textH = metrics.getHeight();

			g2.setColor(new Color (108,126,159));
			g2.drawString(ag, -textW/2,
					-75.75f - margin - (textH + spacing) * 2f);
			g2.setTransform(age);
			
			g2.setTransform(stats);
		}
		if (stateI==CLEAN) {
			AffineTransform clean = g2.getTransform();
			g2.translate(x,y);
			g2.scale(0.25, 0.25);
			g2.drawImage(sponge, -sponge.getWidth()/2, -sponge.getHeight()/2, null);
			g2.setTransform(clean);
			if (Pet.collided(x,y)&&Pet.dirtAmount>=1&&eraseTimer>90) {
				Pet.dirtAmount--;
				eraseTimer=0;
				Pet.happy++;
			}
		}
		if (stateI==FEED) {
			AffineTransform feed = g2.getTransform();
			g2.translate(x,y);
			g2.scale(0.25, 0.25);
			g2.drawImage(food, -food.getWidth()/2, -food.getHeight()/2, null);
			g2.setTransform(feed);
			if (Pet.collided(x,y)&&!Pet.sick) {
				Pet.health++;
				stateI=NONE;
			}
		}
		
		if (stateI==DOC) {
			drawDoc(x,y,g2,d);
			if (Pet.sick&&Pet.collided(x,y)) {
				if(Pet.eatTimer>500&&Pet.health>5) {
					if (Pet.stateP==0)Pet.scale=0.3;
					if (Pet.stateP==1)Pet.scale=0.25;
					if (Pet.stateP==2)Pet.scale=0.2;
					Pet.eatTimer=0;
					Pet.health=5;
				}
				if(Pet.sickTimer>1000)Pet.sickTimer=0;
				
				Pet.sick=false;
			}
		}
	
	}
	
	private void drawDoc(double x, double y, Graphics2D g2, float d) {
		AffineTransform doc = g2.getTransform();
		g2.translate(x,y);
		g2.setColor(new Color (240, 67, 110));
		g2.setStroke(new BasicStroke(3));
		g2.draw(new Ellipse2D.Float(-d/2, -d/2, d, d ));
		g2.setColor(new Color (158, 218, 230));
		g2.fill(new Ellipse2D.Float(-d/2, -d/2, d, d ));
		g2.setTransform(doc);
		if (d  > 1.5) {
				 d *= 0.75; //shrink d by 25% each recursion
				 drawDoc(x,y,g2,d);
			}
	}
	
	public void actionText(Graphics2D g, Dimension wnd, int gameState) {
		String icon=null;
		if (stateI==NONE) icon="";
		if (stateI==STATS) icon="Viewing Statistics...";
		if (stateI==FEED) icon="Feeding your pet!";
		if (stateI==CLEAN) icon="Washing up your pet...";
		if (stateI==DOC) icon="Giving your pet a check up";
		
		AffineTransform at = g.getTransform();
		Font f = new Font("Gill Sans", Font.BOLD, 17);
		g.setFont(f);
		FontMetrics metrics = g.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth(icon);
		float textHeight = metrics.getHeight();
		float margin = 12, spacing = 2;
		
		g.translate(wnd.width/2, wnd.height-115);
		g.setColor(new Color (108,126,159));
		g.drawString(icon, -textWidth/2,
				-75.75f - margin - (textHeight + spacing) * 2f);
		
		g.setTransform(at);
	}
	
	@Override
	public void draw(Graphics2D g2, Dimension wnd, int gameState) {
		if (gameState ==0) {
			AffineTransform pic = g2.getTransform();
			g2.translate((wnd.width/2)-10,(wnd.height/2)+120);
			g2.scale(0.35,0.35);
			g2.drawImage(startButton, -startButton.getWidth()/2, -startButton.getHeight()/2, null);
			g2.setTransform(pic);	
		}
		if (gameState==2) {
			AffineTransform pic = g2.getTransform();
			g2.translate((wnd.width/2-15),(wnd.height/2)+40);
			g2.scale(0.35,0.35);
			g2.drawImage(endButton, -endButton.getWidth()/2, -endButton.getHeight()/2, null);
			g2.setTransform(pic);	
		}
		if (gameState==1) {
			AffineTransform stat = g2.getTransform();
			g2.translate(wnd.width/2-160, wnd.height/2-190);
			g2.scale(0.5,0.5);
			if (stateI == NONE||stateI==FEED||stateI==CLEAN||stateI==DOC) g2.drawImage(stats, -statsW.getWidth()/2, -statsW.getHeight()/2, null);
			else if (stateI == STATS) {
				g2.drawImage(statsW, -statsW.getWidth()/2, -statsW.getHeight()/2, null);
			}
			g2.setTransform(stat);
			
			AffineTransform food = g2.getTransform();
			g2.translate(wnd.width/2-70, wnd.height/2-190);
			g2.scale(0.55,0.55);
			if (stateI == NONE||stateI==STATS||stateI==CLEAN||stateI==DOC)g2.drawImage(feed, -feedW.getWidth()/2, -feedW.getHeight()/2, null);
			else if (stateI == FEED) g2.drawImage(feedW, -feedW.getWidth()/2, -feedW.getHeight()/2, null);
			g2.setTransform(food);	
			
			AffineTransform clean = g2.getTransform();
			g2.translate(wnd.width/2+30, wnd.height/2-190);
			g2.scale(0.25,0.25);
			if (stateI == NONE||stateI==STATS||stateI==FEED||stateI==DOC)g2.drawImage(toilet, -toiletW.getWidth()/2, -toiletW.getHeight()/2, null);
			else if (stateI == CLEAN) g2.drawImage(toiletW, -toiletW.getWidth()/2, -toiletW.getHeight()/2, null);
			g2.setTransform(clean);	
			
			AffineTransform doctor = g2.getTransform();
			g2.translate(wnd.width/2+125, wnd.height/2-190);
			g2.scale(0.25,0.25);
			if (stateI == NONE||stateI==STATS||stateI==FEED||stateI==CLEAN)g2.drawImage(doc, -docW.getWidth()/2, -docW.getHeight()/2, null);
			else if (stateI == DOC) g2.drawImage(docW, -docW.getWidth()/2, -docW.getHeight()/2, null);
			g2.setTransform(doctor);	
		}
	}
	
	public void clicked(double x, double y, Dimension wnd){
		
		if (x > (wnd.width/2-160-((double) stats.getWidth()/2)*0.5) &&
				x < (wnd.width/2-160+ ((double) stats.getWidth()/2)*0.5) && 
				y > (wnd.height/2-190-((double) stats.getHeight()/2)*0.5) &&
				y < (wnd.height/2-190 + ((double) stats.getHeight()/2)*0.5)) {
			if(stateI==NONE||stateI==FEED||stateI==CLEAN||stateI==DOC) {
				select.play(0);
				stateI=STATS;
				
			}else if(stateI==STATS) {
				next.play(0);
				stateI=NONE;
			}
		} 
		if (x > (wnd.width/2-70-((double) feed.getWidth())/2*0.55) &&
				x < (wnd.width/2-70+ ((double) feed.getWidth())/2*0.55) && 
				y > (wnd.height/2-190-((double) feed.getHeight())/2*0.55) &&
				y < (wnd.height/2-190 + ((double) feed.getHeight())/2*0.55)) {
			if(stateI==NONE||stateI==STATS||stateI==CLEAN||stateI==DOC) {
				select.play(0);
				stateI = FEED;
			} else if(stateI==FEED) {
				next.play(0);
				stateI=NONE;
			}
		} 
		if (x > (wnd.width/2+30-((double) toilet.getWidth())/2*0.25) &&
				x < (wnd.width/2+30+((double) toilet.getWidth())/2*0.25) && 
				y > (wnd.height/2-190-((double) toilet.getHeight())/2*0.25) &&
				y < (wnd.height/2-190 + ((double) toilet.getHeight())/2*0.25)) {
			if(stateI==NONE||stateI==STATS||stateI==FEED||stateI==DOC) {
				select.play(0);
				stateI = CLEAN;
			} else if(stateI==CLEAN) {
				next.play(0);
				stateI=NONE;
			}
		} 
		if (x > (wnd.width/2+125-((double) doc.getWidth())/2*0.25) &&
				x < (wnd.width/2+125+ ((double) doc.getWidth())/2*0.25) && 
				y > (wnd.height/2-190-((double) doc.getHeight())/2*0.25) &&
				y < (wnd.height/2-190 + ((double) doc.getHeight())/2*0.25)) {
			if(stateI==NONE||stateI==STATS||stateI==FEED||stateI==CLEAN) {
				select.play(0);
				stateI = DOC;
			} else if(stateI==DOC) {
				next.play(0);
				stateI=NONE;
			}
		}
	}
	
	public boolean startClicked(double x, double y, Dimension wnd){
		boolean clicked = false;
		
		if (x > ((wnd.width/2)-10-((double) startButton.getWidth()/2)*0.35) &&
				x < ((wnd.width/2)-10+ ((double)  startButton.getWidth()/2)*0.35) && 
				y > ((wnd.height/2)+120-((double)  startButton.getHeight()/2)*0.35) &&
				y < ((wnd.height/2)+120 + ((double) startButton.getHeight()/2)*0.35)&&
				clicked==false) {
			select.play(0);
			clicked = true;
		}
		return clicked;
	}
	
	public boolean endClicked(double x, double y, Dimension wnd){
		boolean clicked = false;
		
		if (x > ((wnd.width/2-15)-((double) endButton.getWidth()/2)*0.35) &&
				x < ((wnd.width/2-15)+ ((double)  endButton.getWidth()/2)*0.35) && 
				y > ((wnd.height/2)+40-((double)  endButton.getHeight()/2)*0.35) &&
				y < ((wnd.height/2)+40 + ((double) endButton.getHeight()/2)*0.35)&&
				clicked==false) {
			select.play(0);
			clicked = true;
		}
		
		return clicked;
	}
	
	
}
