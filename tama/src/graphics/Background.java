package graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import interactions.Objects;
import tools.Util;


/* This is the class that draws the background depending on the state the game is in
 * It loads the different versions of the images and draws the right one depending on the state
 * 
 * It only draws the images that are not already drawn by the decorator
 */


public class Background implements Objects {
	
	private BufferedImage bg, bg1,bg2, intro, end;
	
	public Background(){
		end = Util.loadImage("imgs/ending.png");
		bg = Util.loadImage("imgs/bg.png");
		bg1 = Util.loadImage("imgs/bg2.png");
		bg2 = Util.loadImage("imgs/bg3.png");
	}
	
	public void draw(Graphics2D g2, Dimension wnd, int gameState) {	
		
		AffineTransform pic = g2.getTransform();
		g2.translate(0,0);
		if(gameState==1&&Pet.stateP==0) g2.drawImage(bg, 0, 0, wnd.width-25, wnd.height-50, null);
		else if(gameState==1&&Pet.stateP==1) g2.drawImage(bg1, 0, 0, wnd.width-25, wnd.height-50, null);
		else if(gameState==1&&Pet.stateP==2) g2.drawImage(bg2, 0, 0, wnd.width-25, wnd.height-50, null);
		if(gameState==2) g2.drawImage(end, 0, 0, wnd.width-25, wnd.height-50, null);
		g2.setTransform(pic);	
		
	}

	@Override
	public void actionText(Graphics2D g, Dimension wnd, int gameState) {
		// TODO Auto-generated method stub
		
	}
	
}
