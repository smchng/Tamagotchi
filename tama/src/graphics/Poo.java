package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import interactions.Objects;
import tools.Util;


/* This method draws the waste that your pet will excrete and you need to clean up by clicking
 * It will also print text to tell the palyer to clean up
 * 
 * HOWEVER
 * the code is bugged so the clicking is not working 
 * 
 */

public class Poo implements Objects{
	
	private BufferedImage poo;
	private int timer, pooAmount;
	private ArrayList poop;
	
	public Poo() {
		poo=Util.loadImage("imgs/poo.png");
		timer=0;
		pooAmount=0;
		poop=new ArrayList();
	}

	@Override
	public void draw(Graphics2D g2, Dimension wnd, int gameState) {
		timer++;
		
		if (gameState==1) {
			if(timer>300) {
				pooAmount++;
				timer=0;
				if (pooAmount>10) pooAmount=10;
			}
			AffineTransform p = g2.getTransform();
			g2.translate(500,475);
			g2.scale(0.8,0.8);
			if (pooAmount>=1) {
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=2) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=3) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=4) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=5) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			g2.setTransform(p);	
			AffineTransform p2 = g2.getTransform();
			g2.translate(485,475);
			g2.scale(0.8,0.8);
			if (pooAmount>=6) {
				g2.translate(-poo.getWidth(),0);
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=7) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=8) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			if (pooAmount>=9) {
				g2.translate(0,-poo.getHeight());
				g2.drawImage(poo, -poo.getWidth()/2, -poo.getHeight()/2, null);
			}
			
			g2.setTransform(p2);	
		}
		
	}

	@Override
	public void actionText(Graphics2D g, Dimension wnd, int gameState) {
		if (gameState==1) {
			String icon=null;
			if (pooAmount>=1) icon="Clean up the waste!";
			else icon="";
			
			AffineTransform at = g.getTransform();
			g.translate(218, 410);
	
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
	
	public void clicked(double x, double y){
//		for (int i=0; i<poop.size();i++) {
//			g2.translate(500,475);
//			g2.scale(0.8,0.8);
			if (pooAmount>=1) {
				if (x > (500-((double) poo.getWidth())/2*0.8) &&
				x < (500+ ((double) poo.getWidth())/2*0.8) && 
				y > (475-((double) poo.getHeight())/2*0.8) &&
				y < (475 + ((double) poo.getHeight())/2*0.8)) {
					System.out.println("boo");
					pooAmount--;
				}
//			}
		}
	}
	
}
