package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Methods to load things
 */

public class Util {
	public static BufferedImage loadImage(String imgFile) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imgFile));
		} catch (IOException e) {
		}
		return img;
	}
	public static double radians(double angle){
		return angle/180*Math.PI;		
	}
	public static float random(float high) {
		return (float) Math.random() * high;
	}
}
