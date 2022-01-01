package interactions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tools.Util;


/*
 * This class implements Decorator and draws the base image
 */
public class Egg implements Decorator{
	private int xPos, yPos;
	private BufferedImage egg;
	
	public Egg(int xPos, int yPos, String str) {
		this.xPos=xPos;
		this.yPos=yPos;
		egg = Util.loadImage(str);
	}

	@Override
	public void draw(Graphics2D g2, Dimension wnd) {
		g2.drawImage(egg, xPos, yPos, wnd.width-25, wnd.height-50, null);
	}
}
