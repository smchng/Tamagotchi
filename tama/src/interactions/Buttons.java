package interactions;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tools.Util;

/*
 * This class implements Decorator and draws the rest of the images layered on top of the base image
 */

public class Buttons implements Decorator{
	
	Decorator dec;
	private int xPos, yPos;
	private BufferedImage img;
	
	public Buttons(int xPos, int yPos, String str, Decorator dec) {
		this.xPos=xPos;
		this.yPos=yPos;
		this.dec=dec;
		img = Util.loadImage(str);
	}

	@Override
	public void draw(Graphics2D g2, Dimension wnd) {
		dec.draw(g2, wnd);
		g2.drawImage(img, xPos, yPos, wnd.width-25, wnd.height-50, null);
	}

}
